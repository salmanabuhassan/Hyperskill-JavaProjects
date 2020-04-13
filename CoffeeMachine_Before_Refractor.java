package machine;
import java.util.*;

public class CoffeeMachine {
    static Scanner s = new Scanner(System.in);
    static LinkedHashMap<String,Integer> inv = new LinkedHashMap<>();
    public static void main(String[] args) {
        inv.put("water",400);
        inv.put("milk",540);
        inv.put("coffee beans",120);
        inv.put("disposable cups",9);
        inv.put("money",550);

        boolean finish = true;
        String choose;
        do {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            choose = s.nextLine();
            System.out.println();
            switch (choose){
                case "buy" :Buy(inv);break;
                case "fill":Fill(inv);break;
                case "take":Take(inv);break;
                case "remaining":printCoffee(inv);break;
            }

        } while (!choose.equals("exit"));

    }
    static void printCoffee(LinkedHashMap inv){
        System.out.println("The coffee machine has:");
        Iterator<String> it = inv.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if(key == "money") {
                System.out.printf("$%d of %s\n\n", inv.get(key), key);
                break;
            }
            System.out.printf("%d of %s\n", inv.get(key),key);
        }
    }
    static void Buy(LinkedHashMap inv){
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        if(!s.hasNextInt()){
            return;
        }
        String cof = s.nextLine();
        if(cof.equals("1")) {
            if((int)inv.get("water") < 250) {
                System.out.println("Sorry, not enough water!\n");
                return;
            }
            else if((int)inv.get("coffee beans") < 16) {
                System.out.println("Sorry, not enough coffee beans!\n");
                return;
            }
            else if((int)inv.get("disposable cups") < 1) {
                System.out.println("Sorry, not enough disposable cups!\n");
                return;
            }
                inv.replace("water", (int) inv.get("water") - 250);
                inv.replace("coffee beans", (int) inv.get("coffee beans") - 16);
                inv.replace("money", (int) inv.get("money") + 4);
                inv.replace("disposable cups", (int) inv.get("disposable cups") - 1);
                System.out.println("I have enough resources, making you a coffee!\n");
            }
        else if(cof.equals("2")) {
            if((int)inv.get("water") < 350) {
                System.out.println("Sorry, not enough water!\n");
            }
            else if((int)inv.get("milk") < 75) {
                System.out.println("Sorry, not enough coffee beans!\n");
            }
            else if((int)inv.get("coffee beans") < 20) {
                System.out.println("Sorry, not enough coffee beans!\n");
            }
            else if((int)inv.get("disposable cups") < 1) {
                System.out.println("Sorry, not enough disposable cups!\n");
            }
            else {
                inv.replace("water", (int) inv.get("water") - 350);
                inv.replace("milk", (int) inv.get("milk") - 75);
                inv.replace("coffee beans", (int) inv.get("coffee beans") - 20);
                inv.replace("money", (int) inv.get("money") + 7);
                inv.replace("disposable cups", (int) inv.get("disposable cups") - 1);
                System.out.println("I have enough resources, making you a coffee!\n");
            }
        }
        else if(cof.equals("3")){
            if((int)inv.get("water") < 200) {
                System.out.println("Sorry, not enough water\n!");
                return;
            }
            else if((int)inv.get("milk") < 100) {
                System.out.println("Sorry, not enough coffee beans!\n");
                return;
            }
            else if((int)inv.get("coffee beans") < 12) {
                System.out.println("Sorry, not enough coffee beans!\n");
                return;
            }
            else if((int)inv.get("disposable cups") < 1) {
                System.out.println("Sorry, not enough disposable cups!\n");
                return;
            }
            inv.replace("water", (int)inv.get("water") - 200);
            inv.replace("milk", (int)inv.get("milk") - 100);
            inv.replace("coffee beans", (int)inv.get("coffee beans") - 12);
            inv.replace("money", (int)inv.get("money") + 6);
            inv.replace("disposable cups",(int)inv.get("disposable cups") - 1);
            System.out.println("I have enough resources, making you a coffee!\n");
        }
        else if(cof.equals("back"))
            return;

    }
    static void Take(LinkedHashMap inv){
        System.out.println("I gave you $" + inv.get("money") + "\n");
        inv.replace("money",0);
    }
    static void Fill(LinkedHashMap inv){
        Iterator<String> it = inv.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if(key == "money")
                break;
            System.out.printf("Write how many ml of %s do you want to add:\n",key);
            int a = s.nextInt();
            inv.replace(key,a + (int)inv.get(key));
        }

    }
}