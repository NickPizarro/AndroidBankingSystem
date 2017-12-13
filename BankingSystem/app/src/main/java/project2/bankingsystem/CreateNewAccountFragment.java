package project2.bankingsystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import project2.bankingsystem.database.MyBankDAO;
import project2.bankingsystem.model.Account;
import project2.bankingsystem.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewAccountFragment extends Fragment {
    public static final String KEY_USERNAME = "username";

    private EditText mInitialBalance;
    private Spinner mType;
    private CheckBox mDefaultYes;
    private CheckBox mDefaultNo;

    private Account mNewAccount;
    private User mUser;
    private String username;


    public CreateNewAccountFragment() {
        // Required empty public constructor
    }

    public static CreateNewAccountFragment newInstance (String username){
        Bundle args = new Bundle();
        args.putString(KEY_USERNAME,username);
        CreateNewAccountFragment frag= new CreateNewAccountFragment();
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_create_new_account, container, false);

        Bundle args = getArguments();
        username = args.getString(KEY_USERNAME);

        mUser = MyBankDAO.get(getContext()).getUser(username);

        mInitialBalance = (EditText)view.findViewById(R.id.et_initial_deposit);
        mType = (Spinner) view.findViewById(R.id.spinner_new_account_type);
        mDefaultYes = (CheckBox) view.findViewById(R.id.checkbox_default_yes);
        mDefaultNo = (CheckBox) view.findViewById(R.id.checkbox_default_no);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.accountTypes_array
                , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mType.setAdapter(adapter);


        Button mOkButton = (Button) view.findViewById(R.id.new_account_ok);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String balance = mInitialBalance.getText().toString();
                String accountType = mType.getSelectedItem().toString();

                if (balance.trim().length() ==0 || accountType==null){

                }
                else if ((!mDefaultYes.isChecked()) && (!mDefaultNo.isChecked())){

                }
                else if (mDefaultYes.isChecked() && mDefaultNo.isChecked()){

                }
                else{
                    boolean isSavingsAccount = true;
                    if (accountType.equals("Checking Account")){
                        isSavingsAccount=false;
                    }
                    mNewAccount = new Account(Double.valueOf(balance),isSavingsAccount);
                    mNewAccount.setEffective(true);
                    mNewAccount.setOwnerUsername(username);
                    if (mDefaultYes.isChecked()){
                        mNewAccount.setDefault(true);
                        mUser.setDefaultAccount(mNewAccount.getAccountID());
                    }

                    MyBankDAO.get(getContext()).addAccount(mNewAccount);
                    MyBankDAO.get(getContext()).updateUser(mUser);
                }

                Intent intent = new Intent(getActivity(),UserHomepageActivity.class);
                intent.putExtra(UserHomepageActivity.KEY_USERNAME,username);
                startActivityForResult(intent,0);
            }
        });

        Button mCancelButton = (Button) view.findViewById(R.id.new_account_cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UserHomepageActivity.class);
                intent.putExtra(UserHomepageActivity.KEY_USERNAME,username);
                startActivityForResult(intent,0);
            }
        });

        return view;
    }

}
