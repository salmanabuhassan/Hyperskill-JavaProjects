package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    private static boolean running = true;
    private static HashMap<String, String> card = new HashMap<>();
    private static HashMap<String, String> defi = new HashMap<>();
    private static TreeMap<String, Integer> stats = new TreeMap<>();
    private static ArrayList<String> log = new ArrayList<>();
    static Scanner s = new Scanner(System.in);
    public static void main(String[] args) {
        while (running)
            inputAction();
    }

    private static void inputAction() {
        do {
            save("Input the action (add, remove, import," +
                    " export, ask, exit, log, hardest card, reset stats):");
            String input = s.nextLine();
            log.add(input);
            switch (input) {
                case "add":addCard();break;
                case "remove":removeCard();break;
                case "import":importCard();break;
                case "export":exportCard();break;
                case "ask":askCard();break;
                case "log":log();break;
                case "hardest card":hardestCard();break;
                case "reset stats":resetStat();break;
                case "exit":save("Bye bye!");running = false;break;
                default:
                    save(input + " is an invalid command");
            }
        } while (running);
    }

    private static void addCard() {
        save("The card:");
        String c = s.nextLine();
        log.add(c);
        if (card.containsKey(c)) {
            save("The card \"" + c + "\" already exists.");
            return;
        }
        save("The definition of the card:");
        String d = s.nextLine();
        log.add(d);
        if (card.containsValue(d)) {
            save("The definition \"" + d + "\" already exists.");
            return;
        }
        save(String.format("The pair (\"%s\":\"%s\") has been added.\n", c, d));
        defi.put(d, c);
        card.put(c, d);
        stats.put(c,0);
    }

    private static void removeCard() {
        save("The card:");
        String rem = s.nextLine();
        log.add(rem);
        if (card.containsKey(rem)) {
            defi.remove(card.get(rem));
            card.remove(rem);
            stats.remove(rem);
            save("The card has been removed.");
        } else
            save(String.format("Can\'t remove \"%s\": there is no" +
                    " such card\n", rem));
    }

    private static void importCard() {
        save("File name:");
        String name = s.nextLine();
        log.add(name);
        File file = new File(name);
        try (Scanner scanner = new Scanner(file)) {
            int n1 = 0;
            while (scanner.hasNext()) {
                String t1 = scanner.nextLine();
                String t2 = scanner.nextLine();
                n1 = Integer.parseInt(scanner.nextLine());
                card.put(t1, t2);
                defi.put(t2,t1);
                stats.put(t1, n1);
            }
            save(card.size() + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            save("File not found.");
        }
    }


    private static void exportCard() {
        save("File name:");
        String name = s.nextLine();
        log.add(name);
        File file = new File(name);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            card.forEach((k, v) -> {
                printWriter.printf("%s\n%s\n%d\n", k, v, stats.get(k));
            });
            save(card.size() + " cards have been saved.");

        } catch (IOException e) {
            save(String.format("An exception occurs %s", e.getMessage()));
        }

    }
    private static void askCard() {
        save("How many times to ask?");
        int n = s.nextInt();
        log.add((Integer.toString(n)));
        s.nextLine();
        Random random = new Random();
        ArrayList<String> hmm = new ArrayList<>();
        card.forEach((k, v) -> {
            hmm.add(k);
        });
        for (int i = 0; i < n; i++) {
            String q = hmm.get(random.nextInt(card.size()));
            save("Print the definition of \"" + q + "\":");
            String ans = s.nextLine();
            if (card.get(q).equals(ans)) {
                save("Correct answer.");
            } else if (card.containsValue(ans)) {
                save("Wrong answer. The correct one is \"" +
                        card.get(q) + "\"," + " you've just written the " +
                        "definition of \"" + defi.get(ans) + "\".");
                stats.put(q,stats.get(q)+ 1);
            } else {
                save("Wrong answer. The correct one" +
                        " is \"" + card.get(q) + "\".");
                stats.put(q,stats.get(q)+ 1);
            }
        }
    }

    private static void log() {
        save("File name:");
        String l = s.nextLine();
        log.add(l);
        save("The log has been saved");
        File file = new File(l);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (String a : log)
                printWriter.println(a);
        } catch (IOException e) {
            save(String.format("An exception occurs %s", e.getMessage()));
        }

    }

    private static void save(String console) {
        System.out.println(console);
        log.add(console);
    }

    private static void hardestCard(){
        ArrayList<String> hc = new ArrayList<>();
        int wrong = 0;
        for (int max : stats.values()){
            wrong = Math.max(max,wrong);
        }
        if(stats.isEmpty() || wrong == 0){
            save("There are no cards with errors.");
            return;
        }
        for (String a: stats.keySet()){
            if(stats.get(a)==wrong)
                hc.add(a);
        }
        String tmp = hc.toString().replace(" ","")
                .replace("[","\"").
                replace("]","\"").
                replace(",","\", \"");
        if(hc.size() > 1) {
            save(String.format("the hardest card are %s. You have" +
                            " %d errors answering them", tmp,
                    wrong));
        }else{
            save(String.format("the hardest card is %s. You have" +
                            " %d errors answering them", tmp,
                    wrong));
        }
    }
    private static void resetStat(){
        stats.replaceAll((k,v)-> 0);
        save("Card statistics has been reset");
    }
}
