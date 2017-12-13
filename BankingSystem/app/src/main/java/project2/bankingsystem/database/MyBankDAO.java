package project2.bankingsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import project2.bankingsystem.model.Account;
import project2.bankingsystem.model.Transaction;
import project2.bankingsystem.model.User;

/**
 * Created by zhouwen on 6/11/16.
 */
public class MyBankDAO {
    private static MyBankDAO DAO;
    private final Context mContext;
    private SQLiteDatabase mDatabase;

    private final List<User> mUsersList;
    private final Map<String,User> mUsersMap;

    private final List<Account> mAccountsList;
    private final Map<String,Account> mAccountsMap;

    private final List<Transaction> mTransactionList;
    private final Map<String,Transaction> mTransactionMap;


    private MyBankDAO (Context context){
        mContext = context.getApplicationContext();
        mDatabase = new MyBankOpenHelper(context).getWritableDatabase();
        mUsersList = new LinkedList<>();
        mUsersMap = new HashMap<>();
        mAccountsList = new LinkedList<>();
        mAccountsMap = new HashMap<>();
        mTransactionList = new LinkedList<>();
        mTransactionMap = new HashMap<>();
    }

    public static  MyBankDAO get (Context context){
        if(DAO == null) {
            DAO = new MyBankDAO(context);
        }
        return DAO;
    }

    public void addUser (User user){
        mDatabase.insert(
                MyBankSchema.UserTable.NAME,
                null,
                getUserContentValues(user)
        );
    }

    public void addAccount(Account account){
        mDatabase.insert(
                MyBankSchema.AccountTable.NAME,
                null,
                getAccountContentValues(account)
        );
    }

    public void addTransaction (Transaction transaction){
        mDatabase.insert(
                MyBankSchema.TransactionTable.NAME,
                null,
                getTransactionContentValues(transaction)
        );
    }

    public void putLastId (String id){
        ContentValues values = new ContentValues();
        values.put(MyBankSchema.IdTable.Cols.LAST_ID, id);
        mDatabase.insert(
                MyBankSchema.IdTable.NAME,
                null,
                values
        );
    }

    private static ContentValues getUserContentValues (User user){
        ContentValues values = new ContentValues();

        values.put(MyBankSchema.UserTable.Cols.FIRST_NAME, user.getFirstName());
        values.put(MyBankSchema.UserTable.Cols.LAST_NAME, user.getLastName());
        values.put(MyBankSchema.UserTable.Cols.MIDDLE_NAME, user.getMiddleName());
        values.put(MyBankSchema.UserTable.Cols.BIRTH_DATE, user.getBirthDate().getTime());
        values.put(MyBankSchema.UserTable.Cols.USERNAME, user.getUserName());
        values.put(MyBankSchema.UserTable.Cols.PASSWORD, user.getPassword());
        values.put(MyBankSchema.UserTable.Cols.PROFILE_PIC, user.getProfilePic());
        values.put(MyBankSchema.UserTable.Cols.DEFAULT_ACCOUNT_Id, user.getDefaultAccountId());
        ///accounts

        return values;
    }

    private static ContentValues getAccountContentValues (Account account){
        ContentValues values = new ContentValues();
        values.put(MyBankSchema.AccountTable.Cols.BALANCE, account.getBalance());
        values.put(MyBankSchema.AccountTable.Cols.ACCOUNT_ID, account.getAccountID());
        values.put(MyBankSchema.AccountTable.Cols.IS_EFFECTIVE, Boolean.toString(account.isEffective()));
        values.put(MyBankSchema.AccountTable.Cols.IS_SAVINGSACCOUNT, Boolean.toString(account.isSavingsAccount()));
        values.put(MyBankSchema.AccountTable.Cols.IS_DEFAULT, Boolean.toString(account.isDefault()));
        values.put(MyBankSchema.AccountTable.Cols.OWNER_USERNAME, account.getOwnerUsername());
        //transactions

        return values;
    }

    private static ContentValues getTransactionContentValues (Transaction transaction){
        ContentValues values = new ContentValues();
        values.put(MyBankSchema.TransactionTable.Cols.TYPE, transaction.getType());
        values.put(MyBankSchema.TransactionTable.Cols.TRANSACTION_DATE, transaction.getTransactionDate().getTime());
        values.put(MyBankSchema.TransactionTable.Cols.AMOUNT,transaction.getAmount());
        values.put(MyBankSchema.TransactionTable.Cols.SOURCE_ACCOUNT_ID,transaction.getSourceAccountId());
        values.put(MyBankSchema.TransactionTable.Cols.DESTINATION_ACCOUNT_ID,transaction.getDestinationAccountId());
        values.put(MyBankSchema.TransactionTable.Cols.SOURCE_ACCOUNT_USERNAME,transaction.getSourceAccountUsername());
        values.put(MyBankSchema.TransactionTable.Cols.DESTINATION_ACCOUNT_USERNAME,transaction.getDestinationAccountUsername());


        return values;
    }

    public User getUser (String username){ //get user by username
        MyBankCursorWrapper wrapper = queryUser(
                MyBankSchema.UserTable.Cols.USERNAME + "=?",
                new String[]{username}
        );

        User user = null;
        if(wrapper.getCount() != 0) {
            wrapper.moveToFirst();
            user = wrapper.getUser();
        }
        wrapper.close();

        return user;
    }

    public List<User> getUsers(){
        mUsersList.clear();
        mUsersMap.clear();
        MyBankCursorWrapper wrapper = queryUser(null, null);

        try{
            wrapper.moveToFirst();
            while(wrapper.isAfterLast() == false) {
                User user = wrapper.getUser();
                mUsersList.add(user);
                mUsersMap.put(user.getUserName(), user);
                wrapper.moveToNext();
            }
        }finally {
            wrapper.close();
        }
        return  mUsersList;
    }

    public void updateUser (User user){
        String username = user.getUserName();
        ContentValues values = getUserContentValues(user);
        mDatabase.update(
                MyBankSchema.UserTable.NAME,
                values,
                MyBankSchema.UserTable.Cols.USERNAME + "=?",
                new String[]{username}
        );
    }

    private MyBankCursorWrapper queryUser(String where, String[]args){
        Cursor cursor = mDatabase.query(
                MyBankSchema.UserTable.NAME,
                null,
                where,
                args,
                null,
                null,
                null
        );
        return  new MyBankCursorWrapper(cursor);
    }

    public Account getAccount (String id){ //get account by id
        MyBankCursorWrapper wrapper = queryAccount(
                MyBankSchema.AccountTable.Cols.ACCOUNT_ID + "=?",
                new String[]{id}
        );

        Account account = null;
        if(wrapper.getCount() != 0) {
            wrapper.moveToFirst();
            account = wrapper.getAccount();
        }
        wrapper.close();

        return account;
    }

    public List<Account> getAccounts(String username){ //get all the accounts owned by a user
        mAccountsList.clear();
        mAccountsMap.clear();
        MyBankCursorWrapper wrapper = queryAccount(null, null);

        try{
            wrapper.moveToFirst();
            while(wrapper.isAfterLast() == false) {
                Account account = wrapper.getAccount();
                if(account.getOwnerUsername().equals(username)){
                mAccountsList.add(account);
                mAccountsMap.put(account.getAccountID(), account);}
                wrapper.moveToNext();
            }
        }finally {
            wrapper.close();
        }
        return  mAccountsList;
    }

    public void updateAccount (Account account){
        String id = account.getAccountID();
        ContentValues values = getAccountContentValues(account);
        mDatabase.update(
                MyBankSchema.AccountTable.NAME,
                values,
                MyBankSchema.AccountTable.Cols.ACCOUNT_ID + "=?",
                new String[]{id}
        );
    }


    private MyBankCursorWrapper queryAccount (String where, String[] args){
        Cursor cursor = mDatabase.query(
                MyBankSchema.AccountTable.NAME,
                null,
                where,
                args,
                null,
                null,
                null
        );
        return  new MyBankCursorWrapper(cursor);
    }

    private MyBankCursorWrapper queryTransaction (String where, String[] args){
        Cursor cursor = mDatabase.query(
                MyBankSchema.TransactionTable.NAME,
                null,
                where,
                args,
                null,
                null,
                null
        );
        return  new MyBankCursorWrapper(cursor);
    }

    public List<Transaction> getTransactions(String accountId){ //get all transactions owned by an account
        mTransactionList.clear();
        MyBankCursorWrapper wrapper = queryTransaction(null, null);

        try{
            wrapper.moveToFirst();
            while(wrapper.isAfterLast() == false) {
                Transaction transaction = wrapper.getTransaction();
                if(transaction.getSourceAccountId().equals(accountId)){
                    mTransactionList.add(transaction);}
                wrapper.moveToNext();
            }
        }finally {
            wrapper.close();
        }
        return  mTransactionList;
    }

}
