package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
public class Main {
    private static HashMap<Character,Character> mp= new HashMap<>();
    private static String msg = "";
    private static String fileOut;
    public static void main(String[] args){
        boolean write = false;
        boolean read = false;
        Algo algo = new Algo();
        algo.setAlgorithm(new shift());
        String act = "enc";
        int sw = 0;
        int i = 0;
        while (i<args.length){
            switch (args[i]) {
                case "-alg" : checkArgs(i,args);if(args[i+1].equals("unicode"))
                algo.setAlgorithm(new unicode());break;
                case "-mode": checkArgs(i,args);act = args[i + 1];break;
                case "-data": checkArgs(i,args);if(read) break;msg = args[i+1];break;
                case "-key" : checkArgs(i,args);sw = Integer.parseInt(args[i+1]);break;
                case "-out" : checkArgs(i,args);fileOut =args[i+1];write = true;break;
                case "-in"  : checkArgs(i,args);Reader(i,args);read = true;break;
            }
            ++i;
        }

        switch (act){
            case "enc":;mp = algo.encrypt(sw,mp);break;
            case "dec":mp = algo.decrypt(sw,mp);break;
        }
        if(write)
            Writer();
        else {
            for (char en : msg.toCharArray())
                if(mp.containsKey(en))
                System.out.print(mp.get(en));
                else
                    System.out.println(en);
        }
    }
    private static void Writer(){
        File file = new File(fileOut);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (char en : msg.toCharArray()) {
                if(mp.containsKey(en))
                printWriter.print(mp.get(en));
                else
                    printWriter.print(en);
            }
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }
    private static void Reader(int i,String[] args) {
        File fileIn = new File(args[i+1]);
        try (Scanner scanner = new Scanner(fileIn)) {
            while (scanner.hasNext()) {
                msg = scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: ");
        }
    }
    private static void checkArgs(int i,String[] args) {
        if(i+1==args.length || args[i+1].charAt(0)=='-'){
            System.out.println("Error");
            System.exit(0);
        }
    }
}

