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
public class DepositFragment extends Fragment {
    public static final  String USERNAME ="username";
    public static final  String ACCOUNT_ID ="id";

    private String username;
    private String accountId;

    private EditText mAmount;
    private TextView mMessage;

    private User mUser;
    private Account mAccount;


    public DepositFragment() {
        // Required empty public constructor
    }

    public static DepositFragment newInstance (String username, String id){
        Bundle args = new Bundle();
        args.putString(USERNAME,username);
        args.putString(ACCOUNT_ID,id);
        DepositFragment frag= new DepositFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deposit, container, false);

        Bundle args = getArguments();
        username = args.getString(USERNAME);
        accountId = args.getString(ACCOUNT_ID);
        mUser = MyBankDAO.get(getContext()).getUser(username);
        mAccount = MyBankDAO.get(getContext()).getAccount(accountId);

        mAmount = (EditText) view.findViewById(R.id.deposit_amount);
        mMessage =(TextView)view.findViewById(R.id.deposit_message);
        Button mOK = (Button)view.findViewById(R.id.deposit_ok);

        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = mAmount.getText().toString();
                if (amount.trim().length()==0){
                    mMessage.setText("Please enter the amount");
                }else{
                    mMessage.setText("");
                    double amountDouble = Double.valueOf(amount);
                    mAccount.addBalance(amountDouble);
                    MyBankDAO.get(getContext()).updateAccount(mAccount);
                    Transaction transaction = new Transaction(1,amountDouble);
                    transaction.setSourceAccountId(accountId);
                    transaction.setSourceAccountUsername(username);
                    MyBankDAO.get(getContext()).addTransaction(transaction);
                }
            }
        });

        return view;
    }

}
