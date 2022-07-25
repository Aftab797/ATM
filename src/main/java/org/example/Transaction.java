package org.example;

import java.util.Date;

public class Transaction {

    /**
     * Amount transferred
     */
    private double amount;

    /**
     * Date and time of that transaction
     */
    private Date timeStamp;

    /**
     * Account from which money is debited
     */
    private Account inAccount;

    /**
     * Note of transaction
     */
    private String memo;


    /**
     * Create new transaction
     * @param amount amount transacted
     * @param inAccount the account where transaction belongs
     */
    public Transaction(double amount, Account inAccount){

        this.inAccount = inAccount;
        this.amount = amount;
        this.timeStamp = new Date();
        this.memo = "";

    }

    /**
     * Create new transaction
     * @param amount amount transacted
     * @param inAccount the account where transaction belongs
     * @param memo the memo for the transaction
     */
    public Transaction(double amount, Account inAccount, String memo){

        //Two arg constructor
        this(amount, inAccount);
        this.memo = memo;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getSummaryLine() {
        if (this.amount>0) {
            return String.format("%s : ₹%.02f : %s", this.timeStamp.toString(), this.amount, this.memo);
        }else{
            return String.format("%s : ₹(%.02f) : %s", this.timeStamp.toString(), this.amount, this.memo);
        }
    }
}
