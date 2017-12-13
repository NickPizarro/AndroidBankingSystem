package project2.bankingsystem;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

import project2.bankingsystem.database.MyBankDAO;
import project2.bankingsystem.model.Account;
import project2.bankingsystem.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewUserFragment extends Fragment {
    private static final String KEY_USERNAME = "username";

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mMiddleName;
    private Button mBirthDate;
    private Date mDate;
    private EditText mUsername;
    private EditText mPassword;
    private Spinner mType;

    private User mUser;
    private Account mInitialAccount;


    public CreateNewUserFragment() {
        // Required empty public constructor
    }

    public static CreateNewUserFragment newInstance (String username){
        Bundle args = new Bundle();
        args.putString(KEY_USERNAME,username);
        CreateNewUserFragment frag= new CreateNewUserFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new_user, container, false);

        mFirstName = (EditText) view.findViewById(R.id.et_first_name);
        mLastName = (EditText) view.findViewById(R.id.et_last_name);
        mMiddleName = (EditText) view.findViewById(R.id.et_middle_name);
        mUsername = (EditText) view.findViewById(R.id.et_username);
        mPassword = (EditText) view.findViewById(R.id.et_password);
        mBirthDate = (Button) view.findViewById(R.id.button_birthdate);
        mType = (Spinner) view.findViewById(R.id.spinner_account_type);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.accountTypes_array
               , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mType.setAdapter(adapter);

        mBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialogFragment dialog = DateDialogFragment.newInstance(mDate);
                dialog.setTargetFragment(CreateNewUserFragment.this, 0);
                dialog.show(getFragmentManager(), "DateDialog");
            }
        });


//        Bundle args = getArguments();
//        String username = args.getString(KEY_USERNAME);
//
//        if(username != null){
//            mUser = MyBankDAO.get(getContext()).getUser(username);
//        }else{
//            mUser = new User();
//            MyBankDAO.get(getContext()).addUser(mUser);
//        }

        Button mOkButton = (Button) view.findViewById(R.id.button_new_user_ok);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = mFirstName.getText().toString();
                String middleName = mMiddleName.getText().toString();
                String lastName = mLastName.getText().toString();
                Date birthDate = mDate;
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String accountType = mType.getSelectedItem().toString();

                if (firstName.trim().length() == 0 ||  lastName.trim().length() == 0
                        || birthDate==null || username.trim().length() == 0 || password.trim().length() == 0 || accountType==null){
                    Toast.makeText(getActivity(), "Info missing, please complete.",Toast.LENGTH_SHORT ).show();
                }

                else if(MyBankDAO.get(getContext()).getUser(username)!=null){
                    Toast.makeText(getActivity(), "Username already exists, choose another one.",Toast.LENGTH_SHORT ).show();
                }
                else{
                    mUser = new User(firstName, lastName, middleName, birthDate, username, password, "");
                    boolean isSavingsAccount = true;
                    if (accountType.equals("Checking Account")){
                        isSavingsAccount=false;
                    }
                    mInitialAccount = new Account(0,isSavingsAccount);
                    mInitialAccount.setDefault(true);
                    mInitialAccount.setOwnerUsername(username);
                    mInitialAccount.setEffective(true);

                    mUser.setDefaultAccount(mInitialAccount.getAccountID());


                    MyBankDAO.get(getContext()).addAccount(mInitialAccount);

                    MyBankDAO.get(getContext()).addUser(mUser);

                    Intent intent = new Intent(getActivity(),UserHomepageActivity.class);
                    intent.putExtra(UserHomepageActivity.KEY_USERNAME,username);
                    startActivityForResult(intent,0);
                }
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            mDate = (Date)data.getSerializableExtra(DateDialogFragment.EXTRA_DATE);
            updateReleaseDateButtonLabel();
        }
    }

    private void updateReleaseDateButtonLabel() {
        if(mDate == null) {
            mDate = new Date();
        }
        String formatted = DateFormat.getMediumDateFormat(getContext()).format(mDate);
        mBirthDate.setText(formatted);
    }

}
