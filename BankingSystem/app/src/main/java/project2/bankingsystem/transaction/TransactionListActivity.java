package project2.bankingsystem.transaction;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import project2.bankingsystem.AccountListFragment;
import project2.bankingsystem.R;
import project2.bankingsystem.UserHomepageActivity;

public class TransactionListActivity extends AppCompatActivity {

    private TransactionListFragment mTransactionList;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        Intent intent = getIntent();
        if(intent!=null){
            id = intent.getStringExtra(AccountListFragment.KEY_ACCOUNT_ID);
        }
        mTransactionList = TransactionListFragment.newInstance(id);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fl_transaction_list, mTransactionList, null)
                .commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            mTransactionList.updateUI();
        }
    }
}
