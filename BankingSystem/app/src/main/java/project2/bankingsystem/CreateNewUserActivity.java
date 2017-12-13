package project2.bankingsystem;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import project2.bankingsystem.model.User;

public class CreateNewUserActivity extends AppCompatActivity {
    public static final String EXTRA_USERNAME = "username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);

        Intent intent = getIntent();
        String username = intent.getStringExtra(EXTRA_USERNAME);

        CreateNewUserFragment frag = CreateNewUserFragment.newInstance(username);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fl_create_new_user,frag)
                .commit();
    }

    public static Intent newIntent(Context context, String username) {
        Intent intent = new Intent(context, CreateNewUserActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        return intent;
    }


}
