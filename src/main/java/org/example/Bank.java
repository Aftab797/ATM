package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

    /**
     * Name of Bank
     */
    private String name;

    /**
     * Total number of users in that bank
     */
    private ArrayList<User> users;

    /**
     * Total number of account bank has
     */
    private ArrayList<Account> accounts;

    public Bank(String name){

        this.name = name;
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    /**
     * Generate Unique customer id
     * @return
     */
    public String getNewUserCustID() {

        String customerID;

        Random rng = new Random();
        int len = 6;
        boolean unique;

        //Loop till we get unique id
        do{
            customerID = "";
            for (int c=0; c<len; c++){
                customerID += ((Integer) rng.nextInt(10)).toString();
            }

            unique = false;

            for( User u : this.users){
                if (customerID.compareTo(u.getCustomerId()) == 0){
                    unique = true;
                    break;
                }
            }

        }while (unique);

        return customerID;

    }

    public String getNewAccountNo() {

        String accountNo;

        Random rng = new Random();
        int len = 10;
        boolean unique;

        //Loop till we get unique id
        do{
            accountNo = "";
            for (int c=0; c<len; c++){
                accountNo += ((Integer) rng.nextInt(10)).toString();
            }

            unique = false;

            for( Account a : this.accounts){
                if (accountNo.compareTo(a.getAccountNo()) == 0){
                    unique = true;
                    break;
                }
            }
        }while (unique);
        return accountNo;
    }

    /**
     * Add account in bank
     * @param account
     */
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public User addUser(String firstName, String lastName,String pin){

        User newUser = new User(firstName, lastName, pin, this);
        users.add(newUser);

        Account newAccount = new Account("Savings", newUser, this);

        return newUser;

    }

    public User userLogin(String userId, String pin){

        for (User u: this.users){

            if( (u.getCustomerId().compareTo(userId) == 0) && u.validatePin(pin) ){
                return u;
            }

        }

        return null;

    }

    public String getName() {
        return name;
    }
}
