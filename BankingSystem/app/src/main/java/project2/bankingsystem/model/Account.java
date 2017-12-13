package project2.bankingsystem.model;

import java.util.List;

/**
 * Created by zhouwen on 6/11/16.
 */
public class Account {
    private double balance;
    private String accountID;
    private boolean isEffective;
    private boolean isSavingsAccount; //if false, then is checking account
    private boolean isDefault =false;
    private User owner;
    private String ownerUsername;
//    private List<Transaction> transactionList;

    public Account (double initialBalance, boolean isSavingsAccount){
        this.balance = initialBalance;
        this.isSavingsAccount = isSavingsAccount;
        this.isEffective = true;
        this.accountID = new String(String.valueOf(GenerateID.getUniqueNumber()));
    }

    public void setOwnerUsername(String ou){
        ownerUsername =ou;
    }

    public void setAccountID(String id){
        accountID = id;
    }

    public User getOwner(){
        return owner;
    }

    public boolean isSavingsAccount(){
        return isSavingsAccount;
    }

    public double getBalance(){
        return balance;
    }

    public boolean isEffective(){
        return isEffective;
    }

    public boolean isDefault(){
        return isDefault;
    }

    public String getAccountID(){
        return accountID;
    }

    public String getOwnerUsername(){
        return ownerUsername;
    }

//    public List<Transaction> getTransactionList(){
//        return transactionList;
//    }

    public void setOwner (User user){
        owner =user;
    }

    public void setEffective (boolean isEffective){
        this.isEffective = isEffective;
    }

    public void setDefault (boolean isDefault){
        this.isDefault = isDefault;
    }

    public void setBalance (double balance){
        this.balance = balance;
    }

    public void addBalance (double amount){
        balance = balance + amount;
    }

    public void subtractBalance (double amount){
        if (balance-amount<0){

        }else {balance = balance - amount;}
    }

//    public void addTransaction(Transaction t){
//        transactionList.add(t);
//    }

}
