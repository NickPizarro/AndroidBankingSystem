package project2.bankingsystem.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;

import project2.bankingsystem.model.Account;
import project2.bankingsystem.model.Transaction;
import project2.bankingsystem.model.User;

/**
 * Created by zhouwen on 6/11/16.
 */
public class MyBankCursorWrapper extends CursorWrapper{
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MyBankCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser (){
        String firstName = getString(getColumnIndex(MyBankSchema.UserTable.Cols.FIRST_NAME));
        String lastName = getString(getColumnIndex(MyBankSchema.UserTable.Cols.LAST_NAME));
        String middleName = getString(getColumnIndex(MyBankSchema.UserTable.Cols.MIDDLE_NAME));
        Date birthDate = new Date(getLong(getColumnIndex(MyBankSchema.UserTable.Cols.BIRTH_DATE)));
        String username = getString(getColumnIndex(MyBankSchema.UserTable.Cols.USERNAME));
        String password = getString(getColumnIndex(MyBankSchema.UserTable.Cols.PASSWORD));
        String profilePic = getString(getColumnIndex(MyBankSchema.UserTable.Cols.PROFILE_PIC));
        String defaultAccountId = getString(getColumnIndex(MyBankSchema.UserTable.Cols.DEFAULT_ACCOUNT_Id));

        User user = new User(firstName, lastName, middleName, birthDate, username, password, profilePic);
        user.setDefaultAccount(defaultAccountId);

        return user;
    }


    public Account getAccount(){
        double balance = getDouble(getColumnIndex(MyBankSchema.AccountTable.Cols.BALANCE));
        String id = getString(getColumnIndex(MyBankSchema.AccountTable.Cols.ACCOUNT_ID));
        boolean isEffective = Boolean.valueOf(getString(getColumnIndex(MyBankSchema.AccountTable.Cols.IS_EFFECTIVE)));
        boolean isSavingsAccount = Boolean.valueOf(getString(getColumnIndex(MyBankSchema.AccountTable.Cols.IS_SAVINGSACCOUNT)));
        boolean isDefault = Boolean.valueOf(getString(getColumnIndex(MyBankSchema.AccountTable.Cols.IS_DEFAULT)));
        String ownerUsername = getString(getColumnIndex(MyBankSchema.AccountTable.Cols.OWNER_USERNAME));

        Account account = new Account(balance,isSavingsAccount);

        account.setAccountID(id);
        account.setEffective(isEffective);
        account.setDefault(isDefault);
        account.setOwnerUsername(ownerUsername);

        return account;
    }
    public Transaction getTransaction(){
        int type = getInt(getColumnIndex(MyBankSchema.TransactionTable.Cols.TYPE));
        Date transactionDate = new Date(getLong(getColumnIndex(MyBankSchema.TransactionTable.Cols.TRANSACTION_DATE)));
        double amount = getDouble(getColumnIndex(MyBankSchema.TransactionTable.Cols.AMOUNT));
        String sourceAccountId = getString(getColumnIndex(MyBankSchema.TransactionTable.Cols.SOURCE_ACCOUNT_ID));
        String destinationAccountId = getString(getColumnIndex(MyBankSchema.TransactionTable.Cols.DESTINATION_ACCOUNT_ID));
        String sourceAccountUsername = getString(getColumnIndex(MyBankSchema.TransactionTable.Cols.SOURCE_ACCOUNT_USERNAME));
        String destinationAccountUsername = getString(getColumnIndex(MyBankSchema.TransactionTable.Cols.DESTINATION_ACCOUNT_USERNAME));
        Transaction transaction = new Transaction(type,amount);

        transaction.setTransactionDate(transactionDate);
        transaction.setSourceAccountId(sourceAccountId);
        transaction.setDestinationAccountId(destinationAccountId);
        transaction.setSourceAccountUsername(sourceAccountUsername);
        transaction.setDestinationAccountUsername(destinationAccountUsername);
        return transaction;
    }

}
