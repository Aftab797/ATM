package org.example;

import javax.sound.midi.Soundbank;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
        Bank hdfc = new Bank("HDFC Bank");

        User curUser;
        
        while (true){
            
            do{
                curUser = Main.mainMenuPrompt(hdfc, sc);
            }while (curUser == null);

            Main.printUserMenu(curUser, sc, hdfc);
        }

    }

    private static User mainMenuPrompt(Bank bank, Scanner sc) {

        String userId;
        String pass;
        User authUser = null;
        int choice=0;
        System.out.printf("Welcome to %s \n", bank.getName());

        do{
            System.out.println("1) Create User");
            System.out.println("2) Login");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            if (choice<1 || choice>2){
                System.out.println("Invalid choice");
            }

        }while (choice<0 || choice>4);

        switch (choice){
            case 1:
                sc.nextLine();
                System.out.print("Enter your First name: ");
                String firstname = sc.nextLine();

                System.out.print("Enter your Last name: ");
                String lastname = sc.nextLine();

                System.out.print("Enter pin of your choice: ");
                String pin = sc.nextLine();

                User aUser = bank.addUser(firstname, lastname, pin);

                break;

            case 2:

                do{
                    sc.nextLine();
                    System.out.print("Please Enter you UserId: ");
                    userId = sc.nextLine();

                    System.out.print("Please Enter your password: ");
                    pass = sc.nextLine();

                    authUser = bank.userLogin(userId, pass);

                    if (authUser == null) {
                        System.out.println("Please Enter valid UserID/Pass: ");
                    }

                }while (authUser == null);

                break;
        }


        return authUser;
    }
    private static void printUserMenu(User curUser, Scanner sc, Bank bank) {

        curUser.getAccountSummary();

        int choice;

        System.out.printf("Welcome %s, What would you like to do?\n", curUser.getFirstName());

        do{
            System.out.println(" 1) Add Account");
            System.out.println(" 2) Show transaction history ");
            System.out.println(" 3) Withdrawal ");
            System.out.println(" 4) Deposit ");
            System.out.println(" 5) Transfer ");
            System.out.println(" 6) Quit ");
            System.out.println();
            System.out.print("Enter Choice: ");
            choice = sc.nextInt();

            if(choice < 1 || choice > 6){
                System.out.println("Invalid Choice please try again");
            }

        }while (choice < 1 || choice > 5);

        switch (choice){
            case 1:
                Main.addAccount(curUser, sc, bank);
                break;
            case 2:
                Main.showTransactionHistory(curUser, sc);
                break;
            case 3:
                Main.withdraw(curUser, sc);
                break;
            case 4:
                Main.deposit(curUser, sc);
                break;
            case 5:
                Main.transferFunds(curUser, sc);
                break;
        }

        if (choice == 6){
            System.exit(0);
        }

        if(choice!=5){
            printUserMenu(curUser, sc, bank);
        }


    }

    private static void addAccount(User curUser, Scanner sc, Bank bank) {

        System.out.println("Enter type of account \n1) Savings Account\n2) Checking Account");
        int choice;
        String accountType = "Savings";
        do{
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            if (choice<1 || choice>2){
                System.out.println("Invalid Choice");
            }

        }while (choice<1 || choice>2);

        if (choice == 1){
            accountType = "Savings";
        }
        if (choice ==2){
            accountType = "Checking";
        }

        Account acc = new Account(accountType, curUser, bank);

        System.out.println("*********** Account created for user "+ curUser.getFirstName()+ " ***********");

    }

    private static void showTransactionHistory(User user, Scanner sc) {

        int theAcct;

        do{
            System.out.printf("Enter the number (1-%d) of the account you want to see: ", user.numAccount());
            theAcct = sc.nextInt()-1;

            if( theAcct < 0 || theAcct > user.numAccount()){
                System.out.println("Invalid Account Choice. Please try again....");
            }

        }while ( theAcct < 0 || theAcct > user.numAccount());

        user.printAccountTransactionHistory(theAcct);

    }

    private static void transferFunds(User user, Scanner sc) {

        int fromAccount;
        int toAccount;
        double amount;
        double accBalance;

        do {

            System.out.printf("Enter the number (1-%d) of the account you want to transfer from: ", user.numAccount());
            fromAccount = sc.nextInt()-1;

            if( fromAccount < 0 || fromAccount > user.numAccount()){
                System.out.println("Invalid Account Choice. Please try again....");
            }

        }while (fromAccount < 0 || fromAccount > user.numAccount());

        do {

            System.out.printf("Enter the number (1-%d) of account you want to transfer to ", user.numAccount());
            toAccount = sc.nextInt()-1;

            if( toAccount < 0 || toAccount > user.numAccount()){
                System.out.println("Invalid Account Choice. Please try again....");
            }

        }while (toAccount < 0 || toAccount > user.numAccount());

        accBalance = user.getAccountBalance(fromAccount);

        do {

            System.out.printf("Enter the amount you want to transfer (max %.02f)₹: ", accBalance);
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount should be grater than zero");
            }else if( amount > accBalance){
                System.out.printf("Amount must not be greater than  %.02f₹", accBalance);
            }

        }while (amount < 0 || amount > accBalance);

        user.addAccountTransaction(fromAccount, -1*amount, String.format("Transfer to account %s ", user.getAccountNo(toAccount)));

        user.addAccountTransaction(toAccount, amount, String.format("Received from account %s ", user.getAccountNo(fromAccount)));
    }

    private static void withdraw(User user, Scanner sc) {

        int fromAccount;
        double amount;
        double accBalance ;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account you want to withdraw from ", user.numAccount());
            fromAccount = sc.nextInt()-1;

            if( fromAccount < 0 || fromAccount > user.numAccount()){
                System.out.println("Invalid Account Choice. Please try again....");
            }

        }while (fromAccount < 0 || fromAccount > user.numAccount());

        accBalance = user.getAccountBalance(fromAccount);


        do {

            System.out.print("Enter the amount you want to withdraw max "+ accBalance + "₹: ");
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount should be grater than zero");
            }else if( amount > accBalance){
                System.out.printf("Amount must not be greater than " + accBalance + "₹: ");
            }

        }while (amount < 0 || amount > accBalance);

        sc.nextLine();

        System.out.println("Enter Memo: ");
        memo = sc.nextLine();

        user.addAccountTransaction(fromAccount, -1*amount, memo);

    }

    private static void deposit(User user, Scanner sc) {

        int toAccount;
        double amount;
        String memo;

        do {

            System.out.printf("Enter the number (1-%d) of the account where you want to deposit ", user.numAccount());
            toAccount = sc.nextInt()-1;

            if( toAccount < 0 || toAccount > user.numAccount()){
                System.out.println("Invalid Account Choice. Please try again....");
            }

        }while (toAccount < 0 || toAccount > user.numAccount());


        System.out.println("Enter the amount you want to deposit: ");
        amount = sc.nextDouble();

        do {
            if (amount < 0) {
                System.out.println("Amount should be grater than zero");
            }
        }while (amount < 0);

        sc.nextLine();

        System.out.println("Enter Memo: ");
        memo = sc.nextLine();

        user.addAccountTransaction(toAccount, amount, memo);

    }

}