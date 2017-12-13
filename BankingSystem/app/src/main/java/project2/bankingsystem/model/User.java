package project2.bankingsystem.model;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouwen on 6/11/16.
 */
public class User {
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private String userName;
    private String password;
//    private List<Account> checkingAccounts;
//    private Account savingsAccount;
    private String defaultAccountId;
    private String profilePic;
//    private Map<String,Account> accounts;

    public User(){

    }

    public User(String firstName, String lastName, String middleName, Date birthDate, String userName, String password, String profilePic){
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.userName = userName;
        this.password = password;
        this.profilePic = profilePic;

    }

//    public void initialAccount (Account firstAccount){
//        firstAccount.setDefault(true);
//        firstAccount.setOwner(this);
//        defaultAccount = firstAccount;
//        accounts.put(firstAccount.getAccountID(),firstAccount);
//        if(firstAccount.isSavingsAccount()){
//            savingsAccount = firstAccount;
//        }else{
//            this.checkingAccounts.add(firstAccount);
//        }
//    }

    public void setDefaultAccount (String defaultAccountId){
       this.defaultAccountId = defaultAccountId;
    }

//    public void addCheckingAccount(Account account){
//        account.setOwner(this);
//        checkingAccounts.add(account);
//        accounts.put(account.getAccountID(),account);
//    }
//
//    public void addSavingsAccount (Account account){
//        account.setOwner(this);
//        savingsAccount =account;
//        accounts.put(account.getAccountID(),account);
//    }
//
//    public List<Account> getCheckingAccounts(){
//        return checkingAccounts;
//    }
//
//    public Account getSavingsAccount(){
//        return savingsAccount;
//    }
//
//    public Account getAccountById(String id){
//        return accounts.get(id);
//    }

    public String getDefaultAccountId(){
        return defaultAccountId;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getMiddleName(){
        return middleName;
    }

    public Date getBirthDate(){
        return birthDate;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public String getProfilePic(){
        return profilePic;
    }


}
