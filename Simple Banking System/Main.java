package banking;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static HashMap<String,BankAccount> Account =
            new HashMap<>();
    public static int id = 0;
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws SQLException {
        Database db = new Database("card.s3db");
        db.createNewTable();

        int input;
        do {
            print("1. Create account");
            print("2. Log into account");
            print("0. Exit");
            input = Integer.parseInt(sc.nextLine());
            switch (input) {
                case 1:createAccount();break;
                case 2:Authentication();break;
                case 0:db.closeConnection();print("Bye!");break;
                default:print("Error. Action not recognise");
            }
        }while (input != 0);
    }

    public static void createAccount() throws SQLException {
        BankAccount a = new BankAccount(id++);
        Account.put(a.bankNumber,a);
    }
    public static void Authentication() throws SQLException {
        print("Enter your card number:");
        String cardNumber = sc.nextLine();
        print("Enter your PIN:");
        String PIN = sc.nextLine();
        boolean authorize = false;
        if(Account.containsKey(cardNumber)) {
            Account.get(cardNumber).Authenticate(PIN);
        }
        else
            print("Wrong card number or PIN");
    }
    public static void print(String msg) {
        System.out.println(msg); //lazy to type full
    }
}