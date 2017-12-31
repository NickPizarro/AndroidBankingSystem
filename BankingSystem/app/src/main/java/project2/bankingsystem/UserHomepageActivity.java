package project2.bankingsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import project2.bankingsystem.database.MyBankDAO;
import project2.bankingsystem.model.Account;
import project2.bankingsystem.model.User;

public class UserHomepageActivity extends AppCompatActivity {
    public static  final  String KEY_USERNAME = "username";
    private String username;
    private User mUser;
    private TextView mTVusername;
    private TextView mTVfirstname1;
    private TextView mTVfirstname2;
    private TextView mTVmiddlename;
    private TextView mTVlastname;
    private Context context;




    private AccountListFragment mAccountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);
        context = this;

        mTVusername = (TextView)findViewById(R.id.tv_hp_username) ;
        mTVfirstname1 =(TextView) findViewById(R.id.tv_hp_firstname);
        mTVfirstname2 = (TextView) findViewById(R.id.tv_hp_firstname2);
        mTVmiddlename = (TextView) findViewById(R.id.tv_hp_middlename);
        mTVlastname = (TextView) findViewById(R.id.tv_hp_lastname);

        Intent intent = getIntent();
        if (intent!= null){
            username = intent.getStringExtra(MainActivity.KEY_USERNAME);
            mTVusername.setText(username);
            mUser = MyBankDAO.get(this).getUser(username);
            mTVfirstname1.setText(mUser.getFirstName());
            mTVfirstname2.setText(mUser.getFirstName());
            mTVmiddlename.setText(mUser.getMiddleName());
            mTVlastname.setText(mUser.getLastName());
        }
        //account list
        mAccountList = AccountListFragment.newInstance(username);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fl_account_list, mAccountList, null)
                .commit();
//       List<Account> accounts = MyBankDAO.get(this).getAccounts(username);
//        List<User> users = MyBankDAO.get(this).getUsers();
//        String s = "";
//        for (int i=0;i<accounts.size();i++){
//            s = s+ accounts.get(i).getAccountID()+", ";
//        }
//
//        TextView tv = (TextView)findViewById(R.id.tv_test);
//        tv.setText(s);

        Button mAddNewAccount = (Button) findViewById(R.id.button_add_account);
        mAddNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CreateNewAccountActivity.newIntent(context,null);
                intent.putExtra(KEY_USERNAME,username);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            mAccountList.updateUI();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log_out, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled;
        switch(item.getItemId()) {
            case R.id.menu_item_log_out:
                Intent intent = new Intent(UserHomepageActivity.this,MainActivity.class);
                startActivityForResult(intent, 0);
                handled = true;
                break;
            default:
                handled = super.onOptionsItemSelected(item);
                break;
        }
        return handled;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }



}
