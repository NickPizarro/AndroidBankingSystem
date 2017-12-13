package project2.bankingsystem.transaction;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import project2.bankingsystem.R;
import project2.bankingsystem.database.MyBankDAO;
import project2.bankingsystem.model.Account;
import project2.bankingsystem.model.Transaction;
import project2.bankingsystem.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends Fragment {
    public static final  String USERNAME ="username";
    public static final  String ACCOUNT_ID ="id";

    private String username;
    private String accountId;

    private EditText mAmount;
    private TextView mSource;
    private EditText mDestination;
    private TextView mMessage;

    private User mUser;
    private Account mAccount;

    public TransferFragment() {
        // Required empty public constructor
    }

    public static TransferFragment newInstance (String username, String id){
        Bundle args = new Bundle();
        args.putString(USERNAME,username);
        args.putString(ACCOUNT_ID,id);
        TransferFragment frag= new TransferFragment();
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_transfer, container, false);

        Bundle args = getArguments();
        username = args.getString(USERNAME);
        accountId = args.getString(ACCOUNT_ID);
        mUser = MyBankDAO.get(getContext()).getUser(username);
        mAccount = MyBankDAO.get(getContext()).getAccount(accountId);

        mSource = (TextView) view.findViewById(R.id.transfer_source_id) ;
        mSource.setText(accountId);
        mMessage=(TextView) view.findViewById(R.id.transfer_message);

        mAmount = (EditText) view.findViewById(R.id.transfer_amount);
        mDestination = (EditText)view.findViewById(R.id.transfer_destination_id);

        Button mOK = (Button)view.findViewById(R.id.transfer_ok);
        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = mAmount.getText().toString();
                String destinationId = mDestination.getText().toString();
                if (amount.trim().length()==0 || destinationId.trim().length()==0){
                    mMessage.setText("Info incomplete");
                }else{
                    double amountDouble = Double.valueOf(amount);
                    Account account = MyBankDAO.get(getContext()).getAccount(destinationId);
                    if(account ==null){
                        mMessage.setText("Cannot find this account");
                    }
                    else if (! account.getOwnerUsername().equals(username)){
                        mMessage.setText("This account does not belong to you, please use Pay to transfer money to others");
                    }else{
                        if(mAccount.getBalance() < amountDouble){
                            mMessage.setText("Not enough money");
                        }else{
                            mMessage.setText("");
                            mAccount.subtractBalance(amountDouble);
                            MyBankDAO.get(getContext()).updateAccount(mAccount);
                            account.addBalance(amountDouble);
                            MyBankDAO.get(getContext()).updateAccount(account);
                            Transaction transaction = new Transaction(2,amountDouble);
                            transaction.setSourceAccountId(accountId);
                            transaction.setSourceAccountUsername(username);
                            transaction.setDestinationAccountId(destinationId);
                            transaction.setDestinationAccountUsername(username);
                            MyBankDAO.get(getContext()).addTransaction(transaction);

                            //update the transaction history of the distination account
                            Transaction transaction1 = new Transaction(4,amountDouble);
                            transaction1.setDestinationAccountId(accountId);
                            transaction1.setDestinationAccountUsername(username);
                            transaction1.setSourceAccountId(destinationId);
                            transaction1.setSourceAccountUsername(username);
                            MyBankDAO.get(getContext()).addTransaction(transaction1);
                        }

                    }

                }
            }
        });



        return view;
    }

}
