package project2.bankingsystem.model;

import java.util.Date;

import project2.bankingsystem.database.MyBankDAO;

/**
 * Created by zhouwen on 6/11/16.
 */
public class Transaction {
    private String[] types = {"Withdrawal", "Deposit", "Transfer", "Pay others", "Receive"};

    private int type;
    private Date transactionDate;
    private double amount;

    private String sourceAccountId;
    private String destinationAccountId;
    private String sourceAccountUsername;
    private String destinationAccountUsername;

    private Account sourceAccount;
    private Account destinationAccount;

    public Transaction(int type,double amount){
        this.type = type;
        this.amount = amount;
        this.transactionDate = new Date();
    }

    public Transaction (int type, double amount, String sourceAccountUsername, String sourceAccountId){ //for withdrawal and deposit
        this.type = type;
        this.amount = amount;
        this.transactionDate = new Date();
        this.sourceAccountId = sourceAccountId;
        this.sourceAccountUsername = sourceAccountUsername;
    }

    public Transaction (int type, double amount, String sourceAccountUsername, String sourceAccountId, String destinationAccountId){
        //for transfer to self
        this.type = type;
        this.amount = amount;
        this.transactionDate = new Date();
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.sourceAccountUsername = sourceAccountUsername;
    }

    public Transaction (int type, double amount, String sourceAccountUsername,
                        String sourceAccountId, String destinationAccountUsername, String na){
        //for transfer to other user
        this.type = type;
        this.amount = amount;
        this.transactionDate = new Date();
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountUsername = destinationAccountUsername;
        this.sourceAccountUsername =sourceAccountUsername;

    }

    public void Withdrawal(){
        sourceAccount.subtractBalance(amount);
    }

    public void Deposit(){
        sourceAccount.addBalance(amount);
    }

    public void Transfer(){
        sourceAccount.subtractBalance(amount);
        destinationAccount.addBalance(amount);
    }

    public void setSourceAccountId (String id){
        sourceAccountId =id;
    }

    public void setDestinationAccountId (String id){
        destinationAccountId = id;
    }

    public void setSourceAccountUsername (String username){
        sourceAccountUsername = username;
    }

    public void setDestinationAccountUsername (String username){
        destinationAccountUsername = username;
    }

    public void setSourceAccount (Account sourceAccount){
        this.sourceAccount =sourceAccount;
    }

    public void setDestinationAccount (Account destinationAccount){
        this.destinationAccount = destinationAccount;
    }

    public int getType(){
        return type;
    }

    public String getTypeString(){
        return types[type];
    }

    public Date getTransactionDate(){
        return transactionDate;
    }

    public double getAmount(){
        return amount;
    }

    public Account getSourceAccount(){
        return sourceAccount;
    }

    public Account getDestinationAccount(){
        return destinationAccount;
    }

    public String getSourceAccountId(){return sourceAccountId;}

    public String getDestinationAccountId() {return destinationAccountId;}

    public String getSourceAccountUsername() {return sourceAccountUsername;}

    public String getDestinationAccountUsername(){return destinationAccountUsername;}

    public void setTransactionDate(Date date){
        transactionDate =date;
    }
}
