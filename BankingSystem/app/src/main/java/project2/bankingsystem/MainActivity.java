package project2.bankingsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import project2.bankingsystem.database.MyBankDAO;
import project2.bankingsystem.model.User;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_USERNAME = "username";
    private Context context;
    private String username;
    private String password;
    private User mUser;
    private TextView mLogInMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        final EditText mUsername = (EditText) findViewById(R.id.et_enter_username);
        final EditText mPassword = (EditText) findViewById(R.id.et_enter_password);
        mLogInMessage = (TextView) findViewById(R.id.log_in_message);

        Button mCreateNewUser = (Button) findViewById(R.id.button_new_user);
        mCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CreateNewUserActivity.newIntent(context,null);
                startActivityForResult(intent, 0);
            }
        });

        //log in
        Button mLogIn = (Button) findViewById(R.id.button_log_in);
        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();
                mUser = MyBankDAO.get(context).getUser(username);
                if(mUser!=null){
                    if (mUser.getPassword().equals(password)){
                        mLogInMessage.setText("");
                        Intent intent = new Intent(MainActivity.this,UserHomepageActivity.class);
                        intent.putExtra(KEY_USERNAME,username);
                        startActivityForResult(intent,0);
                    }else {
                        mLogInMessage.setText("Password incorrect, try again.");
                    }

                }else{
                    mLogInMessage.setText("Username does not exist, pleas register.");
                }
            }
        });

    }
}
