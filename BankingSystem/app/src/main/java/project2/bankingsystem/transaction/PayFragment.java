package project2.bankingsystem.transaction;

import android.content.Context;
import android.net.Uri;
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


public class PayFragment extends Fragment {
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


    public PayFragment() {
        // Required empty public constructor
    }

    public static PayFragment newInstance (String username,String id){
        Bundle args = new Bundle();
        args.putString(USERNAME,username);
        args.putString(ACCOUNT_ID,id);
        PayFragment frag= new PayFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pay, container, false);

        Bundle args = getArguments();
        username = args.getString(USERNAME);
        accountId = args.getString(ACCOUNT_ID);
        mUser = MyBankDAO.get(getContext()).getUser(username);
        mAccount = MyBankDAO.get(getContext()).getAccount(accountId);

        mSource = (TextView) view.findViewById(R.id.pay_source_id) ;
        mSource.setText(accountId);
        mMessage = (TextView)view.findViewById(R.id.pay_message);

        mAmount = (EditText) view.findViewById(R.id.pay_amount);
        mDestination = (EditText)view.findViewById(R.id.pay_destination_id);

        Button mOK = (Button)view.findViewById(R.id.pay_ok);
        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = mAmount.getText().toString();
                String destinationUsername = mDestination.getText().toString();
                if (amount.trim().length()==0 || destinationUsername.trim().length()==0){
                    mMessage.setText("Info incomplete");
                }else{
                    double amountDouble = Double.valueOf(amount);
                    User destinationUser = MyBankDAO.get(getContext()).getUser(destinationUsername);

                    if(destinationUser ==null){
                        mMessage.setText("Cannot find this user");
                    }
                    else{
                        Account destinationAccount = MyBankDAO.get(getContext()).getAccount(destinationUser.getDefaultAccountId());
                        if(destinationAccount ==null){

                        }
                        else{
                            if(mAccount.getBalance() < amountDouble){
                                mMessage.setText("Not enough money");
                            }else{
                                mMessage.setText("");
                                mAccount.subtractBalance(amountDouble);
                                MyBankDAO.get(getContext()).updateAccount(mAccount);
                                destinationAccount.addBalance(amountDouble);
                                MyBankDAO.get(getContext()).updateAccount(destinationAccount);
                                Transaction transaction = new Transaction(3,amountDouble);
                                transaction.setSourceAccountId(accountId);
                                transaction.setSourceAccountUsername(username);
                                transaction.setDestinationAccountId(destinationAccount.getAccountID());
                                transaction.setDestinationAccountUsername(destinationUser.getUserName());
                                MyBankDAO.get(getContext()).addTransaction(transaction);

                                Transaction transaction1 = new Transaction(4,amountDouble);
                                transaction1.setDestinationAccountId(accountId);
                                transaction1.setDestinationAccountUsername(username);
                                transaction1.setSourceAccountId(destinationAccount.getAccountID());
                                transaction1.setSourceAccountUsername(destinationUser.getUserName());
                                MyBankDAO.get(getContext()).addTransaction(transaction1);

                            }

                        }
                    }


                }
            }
        });



        return view;
    }

}
