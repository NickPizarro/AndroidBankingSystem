package project2.bankingsystem.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhouwen on 6/11/16.
 */
public class MyBankOpenHelper extends SQLiteOpenHelper {
    public MyBankOpenHelper(Context context) {
        super(context, MyBankSchema.DATABASE_NAME, null, MyBankSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MyBankSchema.UserTable.NAME
                + "(_id integer primary key autoincrement, "
                + MyBankSchema.UserTable.Cols.FIRST_NAME + ", "
                + MyBankSchema.UserTable.Cols.LAST_NAME + ", "
                + MyBankSchema.UserTable.Cols.MIDDLE_NAME + ", "
                + MyBankSchema.UserTable.Cols.BIRTH_DATE + ", "
                + MyBankSchema.UserTable.Cols.USERNAME + ", "
                + MyBankSchema.UserTable.Cols.PASSWORD + ", "
                + MyBankSchema.UserTable.Cols.PROFILE_PIC + ", "
                + MyBankSchema.UserTable.Cols.DEFAULT_ACCOUNT_Id + ")"
                //+ MyBankSchema.UserTable.Cols.ACCOUNTS +")"
        );

        db.execSQL("create table " + MyBankSchema.AccountTable.NAME
                + "(_id integer primary key autoincrement, "
                + MyBankSchema.AccountTable.Cols.BALANCE + ", "
                + MyBankSchema.AccountTable.Cols.ACCOUNT_ID + ", "
                + MyBankSchema.AccountTable.Cols.IS_EFFECTIVE + ", "
                + MyBankSchema.AccountTable.Cols.IS_SAVINGSACCOUNT + ", "
                + MyBankSchema.AccountTable.Cols.IS_DEFAULT + ", "
                + MyBankSchema.AccountTable.Cols.OWNER_USERNAME + ")"
                //+ MyBankSchema.AccountTable.Cols.TRANSACTION_LIST +")"
        );

        db.execSQL("create table " + MyBankSchema.TransactionTable.NAME
                + "(_id integer primary key autoincrement, "
                + MyBankSchema.TransactionTable.Cols.TYPE + ", "
                + MyBankSchema.TransactionTable.Cols.TRANSACTION_DATE + ", "
                + MyBankSchema.TransactionTable.Cols.AMOUNT + ", "
                + MyBankSchema.TransactionTable.Cols.SOURCE_ACCOUNT_ID + ", "
                + MyBankSchema.TransactionTable.Cols.DESTINATION_ACCOUNT_ID +", "
                + MyBankSchema.TransactionTable.Cols.SOURCE_ACCOUNT_USERNAME +", "
                + MyBankSchema.TransactionTable.Cols.DESTINATION_ACCOUNT_USERNAME +")"
        );

        db.execSQL("create table " + MyBankSchema.IdTable.NAME
                + "(_id integer primary key autoincrement, "
                + MyBankSchema.IdTable.Cols.LAST_ID +")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
