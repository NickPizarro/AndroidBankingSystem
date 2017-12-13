package project2.bankingsystem.model;

import project2.bankingsystem.database.MyBankDAO;

/**
 * Created by zhouwen on 6/11/16.
 */
public class GenerateID {
    private static long LAST_NUMBER=1000000000; //10 digit

    public static long getUniqueNumber(){
        LAST_NUMBER=LAST_NUMBER+1;
        return LAST_NUMBER;
    }
}
