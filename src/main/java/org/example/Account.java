package org.example;

import java.util.ArrayList;

public class Account {

    /**
     * Name of account
     */
    private String name;

    /**
     * Unique Id of Account
     */
    private String accountNo;

    /**
     * Balance of that Account
     */
    private double balance = 0.0;

    /**
     * Owned by which user
     */
    private User holder;

    /**
     * List of Transactions
     */
    private ArrayList<Transaction> transactions;


    public String getAccountNo() {
        return accountNo;
    }

    /**
     * Create Account with name holder and bank
     * @param name
     * @param holder
     * @param theBank
     */
    public Account(String name, User holder, Bank theBank){

        //set account name and holder
        this.name = name;
        this.holder = holder;

        //Generate unique Account no
        this.accountNo = theBank.getNewAccountNo();

        //Initialize Transaction with empty list
        transactions = new ArrayList<>();

        //Add account to holder and bank object
        holder.addAccount(this);
        theBank.addAccount(this);


    }

    public String getName() {
        return name;
    }

    public String getSummary() {

        this.balance = this.getBalance();

        if(balance >= 0){
            return String.format("%s : ₹%.02f : %s", this.accountNo, balance, this.getName());
        }else {
            return String.format("%s : ₹(%.02f) : %s", this.accountNo, balance, this.getName());
        }
    }

    public double getBalance() {
        double bal = 0;

        for (Transaction t : this.transactions){
            bal += t.getAmount();
        }
        return bal;
    }

    public void printTransHistory() {

        System.out.printf("Transaction history of %s: \n",this.accountNo);

        for (int i = this.transactions.size()-1; i >= 0; i--){
            System.out.println(this.transactions.get(i).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amount, String memo) {

        Transaction newTrans = new Transaction(amount, this, memo);

        transactions.add(newTrans);
    }
}
