package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    /**
     * First Name of the Account holder
     */
    private String firstName;

    /**
     * Last Name of the Account holder
     */
    private String lastName;

    /**
     * Unique ID of the Account holder
     */
    private String customerId;

    /**
     * MD5 hash Password Account holder
     */
    private byte pinHash[];

    /**
     * List of Account a user have
     */
    private ArrayList<Account> accounts;

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    /**
     * Create New User Object
     * @param firstName
     * @param lastName
     * @param pin
     * @param theBank
     */
    public User(String firstName, String lastName, String pin, Bank theBank){

        this.firstName = firstName;
        this.lastName = lastName;


        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No such Algorithm exists");
            e.printStackTrace();
            System.exit(1);
        }

        this.customerId = theBank.getNewUserCustID();

        this.accounts = new ArrayList<Account>();

        System.out.printf("New User %s %s with ID %s created.\n", firstName, lastName, this.customerId);


    }

    /**
     * Add account in user list
     * @param account
     */
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public String getCustomerId() {
        return customerId;
    }

    public boolean validatePin(String pin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("No such Algorithm exists");
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }

    public String getFirstName() {
        return firstName;
    }

    public void getAccountSummary() {

        System.out.printf("\n\n%s's Account Summary\n", this.firstName);

        for(Account account : this.accounts){

            System.out.printf(" Account No %s Summary %s\n", account.getAccountNo(), account.getSummary());

        }

    }

    public int numAccount() {
        return this.accounts.size();
    }

    public void printAccountTransactionHistory(int theAcct) {

        this.accounts.get(theAcct).printTransHistory();

    }

    public double getAccountBalance(int fromAccount) {

        return this.accounts.get(fromAccount).getBalance();

    }

    public String getAccountNo(int accIndex) {
        return this.accounts.get(accIndex).getAccountNo();
    }

    public void addAccountTransaction(int account, double amount, String memo) {
        this.accounts.get(account).addTransaction(amount, memo);
    }
}
