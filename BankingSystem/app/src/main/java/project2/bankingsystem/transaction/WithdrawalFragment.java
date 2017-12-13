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


public class WithdrawalFragment extends Fragment {
    public static final  String USERNAME ="username";
    public static final  String ACCOUNT_ID ="id";

    private String username;
    private String accountId;

    private EditText mAmount;
    private TextView mMessage;

    private User mUser;
    private Account mAccount;



    public WithdrawalFragment() {
        // Required empty public constructor
    }

    public static WithdrawalFragment newInstance (String username, String id){
        Bundle args = new Bundle();
        args.putString(USERNAME,username);
        args.putString(ACCOUNT_ID,id);
        WithdrawalFragment frag= new WithdrawalFragment();
        frag.setArguments(args);
        return frag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_withdrawal, container, false);

        Bundle args = getArguments();
        username = args.getString(USERNAME);
        accountId = args.getString(ACCOUNT_ID);
        mUser = MyBankDAO.get(getContext()).getUser(username);
        mAccount = MyBankDAO.get(getContext()).getAccount(accountId);

        mAmount = (EditText) view.findViewById(R.id.withdrawal_amount);
        mMessage= (TextView)view.findViewById(R.id.withdrawal_message);
        Button mOK = (Button)view.findViewById(R.id.withdrawal_ok);

        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = mAmount.getText().toString();
                if (amount.trim().length()==0){
                    mMessage.setText("Please enter the amount");
                }else{
                    if(mAccount.getBalance() < Double.valueOf(amount)){
                        mMessage.setText("Not enough money");
                    }else{
                        mMessage.setText("");
                        double amountDouble = Double.valueOf(amount);
                        mAccount.subtractBalance(amountDouble);
                        MyBankDAO.get(getContext()).updateAccount(mAccount);
                        Transaction transaction = new Transaction(0,amountDouble);
                        transaction.setSourceAccountId(accountId);
                        transaction.setSourceAccountUsername(username);
                        MyBankDAO.get(getContext()).addTransaction(transaction);
                    }

                }
            }
        });

        return view;
    }

}
