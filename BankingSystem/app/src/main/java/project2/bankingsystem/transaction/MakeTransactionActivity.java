package project2.bankingsystem.transaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import project2.bankingsystem.MainActivity;
import project2.bankingsystem.R;
import project2.bankingsystem.UserHomepageActivity;

public class MakeTransactionActivity extends AppCompatActivity {
    public static final String USERNAME = "username";
    public static final String ACCOUNT_ID = "account_id";
    private String mUsername;
    private String mAccountId;
    private TextView mTVusername;
    private TextView mTVid;
    private Button mDeposit;
    private Button mWithdrawal;
    private Button mTransfer;
    private Button mPay;

    private Fragment depositFrag;
    private Fragment withdrawalFrag;
    private Fragment transferFrag;
    private Fragment payFrag;
    private FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_transaction);


        Intent intent = getIntent();
        mUsername = intent.getStringExtra(USERNAME);
        mAccountId = intent.getStringExtra(ACCOUNT_ID);
        mTVusername = (TextView) findViewById(R.id.transaction_source_username);
        mTVid = (TextView) findViewById(R.id.transaction_source_id);
        mTVusername.setText(mUsername);
        mTVid.setText(mAccountId);
        mDeposit = (Button)findViewById(R.id.button_deposit);
        mWithdrawal = (Button) findViewById(R.id.button_withdrawal);
        mTransfer = (Button) findViewById(R.id.button_transfer_self);
        mPay = (Button)findViewById(R.id.button_transfer_other);

        manager = getFragmentManager();
        depositFrag = DepositFragment.newInstance(mUsername,mAccountId);
        withdrawalFrag = WithdrawalFragment.newInstance(mUsername,mAccountId);
        transferFrag = TransferFragment.newInstance(mUsername,mAccountId);
        payFrag = PayFragment.newInstance(mUsername,mAccountId);

        mDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .replace(R.id.fl_transaction_container, depositFrag).commit();
            }
        });

        mWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .replace(R.id.fl_transaction_container, withdrawalFrag).commit();
            }
        });

        mTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .replace(R.id.fl_transaction_container, transferFrag).commit();
            }
        });

        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .replace(R.id.fl_transaction_container, payFrag).commit();
            }
        });

        Button mBack = (Button)findViewById(R.id.transaction_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeTransactionActivity.this,UserHomepageActivity.class);
                intent.putExtra(MainActivity.KEY_USERNAME,mUsername);
                startActivityForResult(intent,0);
            }
        });

    }

    public static Intent newIntent(Context context, String username, String accountId) {
        Intent intent = new Intent(context, MakeTransactionActivity.class);
        intent.putExtra(USERNAME, username);
        intent.putExtra(ACCOUNT_ID,accountId);
        return intent;
    }
}
