package project2.bankingsystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import project2.bankingsystem.database.MyBankDAO;
import project2.bankingsystem.model.Account;
import project2.bankingsystem.transaction.MakeTransactionActivity;
import project2.bankingsystem.transaction.TransactionListActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountListFragment extends Fragment {
    private static final String TAG = "AccountListFragment";
    private static final String KEY_USERNAME = "username";
    public static final String KEY_ACCOUNT_ID ="account_id";

    private MyBankDAO myBankDAO;
    private RecyclerView mRecyclerView;
    private AccountAdapter mAdapter;
    private String username;
    private Button mMakeTransaction;


    public AccountListFragment() {
        // Required empty public constructor
    }

    public static AccountListFragment newInstance(String username){
        Bundle args = new Bundle();
        args.putString(KEY_USERNAME,username);
        AccountListFragment frag = new AccountListFragment();
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myBankDAO = myBankDAO.get(getContext());
        Bundle args = getArguments();
        username = args.getString(KEY_USERNAME);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_list, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_accounts);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        List<Account> accounts = myBankDAO.getAccounts(username);
        Log.d(TAG, accounts.toString());
        if(mAdapter == null) {
            mAdapter = new AccountAdapter(accounts);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.setAccounts(accounts);
        }
    }


    private class AccountAdapter extends RecyclerView.Adapter<AccountViewHolder>{
        private static final String TAG = "AccountAdapter";
        private List<Account> mAccounts;

        public AccountAdapter(List<Account> accounts){
            mAccounts=accounts;
        }

        @Override
        public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(TAG, "onCreateViewHolder() called");
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.view_accounts, parent, false);
            return new AccountViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(AccountViewHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder(holder," + position +") called");
            Account account = mAccounts.get(position);
            holder.bind(account);
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "getItemCount() returning " + mAccounts.size());
            return mAccounts.size();
        }

        public void setAccounts(List<Account> accounts){
            mAccounts = accounts;
            notifyDataSetChanged();
        }
    }



    private class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView mType;
        private final TextView mId;
        private final TextView mBalance;
        private Account mAccount;

        public AccountViewHolder(View itemView) {
            super(itemView);
            mType = (TextView) itemView.findViewById(R.id.listview_account_type);
            mId = (TextView) itemView.findViewById(R.id.listview_account_id);
            mBalance = (TextView) itemView.findViewById(R.id.listview_account_balance);
            itemView.setOnClickListener(this);
            mMakeTransaction = (Button) itemView.findViewById(R.id.listview_make_transaction);

            mMakeTransaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = MakeTransactionActivity.newIntent(getContext(),mAccount.getOwnerUsername(),mAccount.getAccountID());
                    startActivityForResult(intent, 0);
                }
            });

        }

        public void bind (Account account){
            mAccount = account;
            String type = "";
            if (account.isSavingsAccount()){
                type="Savings Account";
            }else{
                type="Checking Account";
            }
            mType.setText(type);
            mId.setText(account.getAccountID());
            mBalance.setText(Double.toString(account.getBalance()));


        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), TransactionListActivity.class);
            intent.putExtra(KEY_ACCOUNT_ID,mAccount.getAccountID());
            startActivityForResult(intent,0);
        }
    }

}
