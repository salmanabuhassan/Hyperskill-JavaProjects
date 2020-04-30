package banking;

import java.sql.SQLException;
import java.util.Scanner;

public class BankAccount {
public String bankNumber;
private String PIN;
private int Balance;
private static Database db;
//connecting to db
    static {
        try {
            db = new Database("card.s3db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Scanner sc = new Scanner(System.in);
public BankAccount(int id) throws SQLException {
    this.bankNumber = generateAcc();
    this.PIN = generatePIN();
    this.Balance = 0;
    db.insert(id,this.bankNumber,this.PIN,this.Balance);
    Message();
}
private String generateAcc() { //generating accNumber based on Luhn algorithm
    String number = "0123456789";
    StringBuilder bankAcc = new StringBuilder("400000");
    for (int i = 0; i < 9; i++) {
        int index = (int)(number.length() * Math.random());
        bankAcc.append(number.charAt(index));
    }
    int t = luhnNumber(bankAcc.toString());
    char c = (char)(t+48);
    bankAcc.append(c);
    return bankAcc.toString();
}
private String generatePIN() {
    String number = "0123456789";
    StringBuilder PIN = new StringBuilder();
    for (int i = 0; i < 4; i++) {
        int index = (int)(number.length() * Math.random());
        PIN.append(number.charAt(index));
    }
    return PIN.toString();
}
private void Message() {
    System.out.println("Your card have been created");
    System.out.println("Your card number:");
    System.out.println(this.bankNumber);
    System.out.println("Your card PIN:");
    System.out.println(this.PIN);
    System.out.println();
}
protected void Authenticate(String pin) throws SQLException {
    if(pin.equals(this.PIN))
        Login();
    else
        print("Wrong card number or PIN\n");
}
private void Login() throws SQLException {
    print("You have successfully logged in!\n");
    String op;
    do {
        print("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit\n");
        op = sc.nextLine();
        switch (op) {
            case "1":print("Balance: " + this.getBalance());break;
            case "2":deposit();break;
            case "3":transfer();break;
            case "4":closeAccount();break;
            case "5":print("You have successfully logged out!");op = "0";break;
            case "0":db.closeConnection();print("\nBye!");System.exit(0);break;
            default:print("\"Error. Action not recognise\"");
        }
    }while(!op.equals("0"));
}
private void deposit() throws SQLException {
    print("How much you want to deposit:");
    int a = Integer.parseInt(sc.nextLine());
    this.setBalance(this.getBalance() + a);
    db.deposit(this.bankNumber,this.Balance);
    print(a + " has been deposited into your account");
}
private void transfer() throws SQLException {
    boolean valid = false;
    String recipient = null;
    while (!valid) {
        print("Enter the bankNumber that you want to transfer");
        recipient = sc.nextLine();
        if (recipient.equals(this.bankNumber))
            print("You can't transfer money to the same account!");
        else if (!LuhnChecker(recipient))
            print("Probably you made mistake in card number. Please try again!");
        else if (!db.checkAccount(recipient))
            print("Such a card does not exist.");
        else {
            print("Enter the transfer amount:");
            int amount = Integer.parseInt(sc.nextLine());
            if(this.Balance > amount) {
                this.setBalance(this.Balance - amount);
                db.deposit(this.bankNumber, this.Balance - amount); //minus the sender balance
                db.transferTo(recipient, amount);
                valid = true;
            }
            else
                print("Not enough balance!");
        }

    }
}
private boolean LuhnChecker(String bankNumber) {
    int lastDigit = bankNumber.charAt(bankNumber.length()-1) - 48;
String check = bankNumber.substring(0,bankNumber.length()-1);
    int t = luhnNumber(check);
    return t == lastDigit;
}
private void closeAccount() throws SQLException{
db.closeAccount(this.bankNumber);
}
private int luhnNumber(String number) {
    int[] num = new int[number.length()];
    int Luhn = 0;
    for (int i = 0; i < num.length; i++) {
        num[i] = number.charAt(i) - 48;
        if (i % 2 == 0) {
            int temp = num[i] * 2;
            if (temp > 9)
                temp -= 9;
            Luhn += temp;
        }
        else {
            int temp = num[i];
            Luhn += temp;
        }
    }
    if(Luhn%10 == 0)
        return  0;
    else
        return 10 - (Luhn%10);
}
private void print(String msg) {
    System.out.println(msg);
}
    public int getBalance() {
        return this.Balance;
    }

    public void setBalance(int balance) {
        this.Balance = balance;
    }

}