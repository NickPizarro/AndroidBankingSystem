package project2.bankingsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreateNewAccountActivity extends AppCompatActivity {
    public static final String EXTRA_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        Intent intent = getIntent();
        String username = intent.getStringExtra(EXTRA_USERNAME);

        CreateNewAccountFragment frag = CreateNewAccountFragment.newInstance(username);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fl_create_new_account,frag)
                .commit();

    }

    public static Intent newIntent(Context context, String username) {
        Intent intent = new Intent(context, CreateNewAccountActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        return intent;
    }
}
