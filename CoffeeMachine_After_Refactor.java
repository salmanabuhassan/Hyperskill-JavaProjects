package machine;
import java.util.*;

public class CoffeeMachine {
    static Scanner s = new Scanner(System.in);
    static LinkedHashMap<String,Integer> Coffee_Machine = new LinkedHashMap<>();//store resources and its amount

    public static void main(String[] args) {
        addInitialResources();
        while (RunningMachine()); //runs until user input exit which returns false
    }

    static boolean RunningMachine(){
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        String choose;
        choose = s.nextLine();
        System.out.println();
        switch (choose){
            case "buy" :Buy();break;
            case "fill":Fill();break;
            case "take":Take();break;
            case "remaining":printCoffee();break;
            case "exit":return false;
        }
        return true;
    }
    static void addInitialResources() {
        Coffee_Machine.put("water",400);
        Coffee_Machine.put("milk",540);
        Coffee_Machine.put("coffee beans",120);
        Coffee_Machine.put("disposable cups",9);
        Coffee_Machine.put("money",550);
    }
    static void printCoffee(){
        System.out.println("The coffee machine has:");
        Iterator<String> it = Coffee_Machine.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if(key == "money") {
                System.out.printf("$%d of %s\n\n", Coffee_Machine.get(key), key);
                break;
            }
            System.out.printf("%d of %s\n", Coffee_Machine.get(key),key);
        }
    }
    static void Buy(){
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String cof = s.nextLine();
        int[] res = new int[4];//res[0]="water",res[1]="milk",res[2]="beans",res[3]="price"
        switch (cof){
            case "1":res[0]=250;res[1]=0;res[2]=16;res[3]=4;break;
            case "2":res[0]=350;res[1]=75;res[2]=20;res[3]=7;break;
            case "3":res[0]=200;res[1]=100;res[2]=12;res[3]=6;break;
            case "back":return;
        }
            if(Coffee_Machine.get("water") < res[0]) {
                System.out.println("Sorry, not enough water!\n");
                return;
            }
            else if(Coffee_Machine.get("milk") < res[1]) {
                System.out.println("Sorry, not enough milks!\n");
            }
            else if(Coffee_Machine.get("coffee beans") < res[2]) {
                System.out.println("Sorry, not enough coffee beans!\n");
                return;
            }
            else if(Coffee_Machine.get("disposable cups") < 1) {
                System.out.println("Sorry, not enough disposable cups!\n");
                return;
            }
            Coffee_Machine.replace("water", Coffee_Machine.get("water") - res[0]);
            Coffee_Machine.replace("milk", Coffee_Machine.get("milk") - res[1]);
            Coffee_Machine.replace("coffee beans", Coffee_Machine.get("coffee beans") - res[2]);
            Coffee_Machine.replace("money", Coffee_Machine.get("money") + res[3]);
            Coffee_Machine.replace("disposable cups", Coffee_Machine.get("disposable cups") - 1);
            System.out.println("I have enough resources, making you a coffee!\n");
    }
    static void Take(){
        System.out.println("I gave you $" + Coffee_Machine.get("money") + "\n");
        Coffee_Machine.replace("money",0);
    }
    static void Fill(){
        Iterator<String> it = Coffee_Machine.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if(key == "money")
                break;
            System.out.printf("Write how many ml of %s do you want to add:\n",key);
            int a = s.nextInt();
            Coffee_Machine.replace(key,a + (int)Coffee_Machine.get(key));
        }
    }
}