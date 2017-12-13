package project2.bankingsystem.transaction;


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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import project2.bankingsystem.R;
import project2.bankingsystem.database.MyBankDAO;
import project2.bankingsystem.model.Account;
import project2.bankingsystem.model.Transaction;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionListFragment extends Fragment { //the transaction history
    private static final String KEY_ACCOUNT_ID = "account_id";
    private MyBankDAO myBankDAO;
    private RecyclerView mRecyclerView;
    private TransactionAdapter mAdapter;
    private String id;

    public TransactionListFragment() {
        // Required empty public constructor
    }
    public static TransactionListFragment newInstance(String accountId){
        Bundle args = new Bundle();
        args.putString(KEY_ACCOUNT_ID,accountId);
        TransactionListFragment frag = new TransactionListFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myBankDAO = myBankDAO.get(getContext());
        Bundle args = getArguments();
        id=args.getString(KEY_ACCOUNT_ID);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_transaction_list, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_transactions);
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
        List<Transaction> transactions = myBankDAO.getTransactions(id);
        if(mAdapter == null) {
            mAdapter = new TransactionAdapter(transactions);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.setTransactions(transactions);
        }
    }

    private class TransactionViewHolder extends RecyclerView.ViewHolder{
        private final TextView mType;
        private final TextView mDate;
        private final TextView mAmount;
        private final TextView mSign;
        private Transaction mTransaction;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            mType = (TextView) itemView.findViewById(R.id.transaction_list_type);
            mDate = (TextView) itemView.findViewById(R.id.transaction_list_date);
            mAmount = (TextView) itemView.findViewById(R.id.transaction_list_amount);
            mSign = (TextView)itemView.findViewById(R.id.transaction_list_sign);

        }
        public void bind (Transaction transaction){
            mTransaction =transaction;

            mType.setText(transaction.getTypeString());

            Date date = transaction.getTransactionDate();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            mDate.setText(df.format(date));
            mAmount.setText(Double.toString(transaction.getAmount()));

            if(transaction.getType()==1 || transaction.getType()==4){
                mSign.setText("+");
            }
            else {
                mSign.setText("-");
            }

        }
    }

    private class TransactionAdapter extends RecyclerView.Adapter<TransactionViewHolder>{
        private static final String TAG = "TransactionAdapter";
        private List<Transaction> mTransactions;

        public TransactionAdapter(List<Transaction> transactions){
            mTransactions=transactions;
        }

        @Override
        public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(TAG, "onCreateViewHolder() called");
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.view_transaction, parent, false);
            return new TransactionViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(TransactionViewHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder(holder," + position +") called");
            Transaction transaction = mTransactions.get(position);
            holder.bind(transaction);
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "getItemCount() returning " + mTransactions.size());
            return mTransactions.size();
        }

        public void setTransactions(List<Transaction> transactions){
            mTransactions=transactions;
            notifyDataSetChanged();
        }
    }
}
