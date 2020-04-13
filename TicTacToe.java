package tictactoe;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        char[][] tic = new char[3][3];
        for (char[] row : tic)
            Arrays.fill(row, '_');
        int[] occ;
        occ = new int[]{0, 0};
        printArray(tic);
        int x = 0;
        int y = 0;
        boolean Finish = false;
        boolean Taken = false;
        int turn = 0;
        do {
            try {
                System.out.print("Enter the coordinates: ");
                x = s.nextInt();
                y = s.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                s.next();
            }
            if((x < 1 || x > 3) || (y < 1 || y >3)) {
                System.out.println("Coordinates should be from 1 to 3!");
            }
            if((x > 0 && x < 4) && (y > 0 && y < 4)) {
                Taken = checkAvailable(tic,x,y);
                if(!Taken) {
                    System.out.println("This cell is occupied! Choose another one!");
                }
                else{
                    tic = Mark(tic,x,y,turn++,occ);
                    printArray(tic);
                    Finish = evaluateGame(tic,occ);
                }
            }

        }while((x < 1 || x > 3) || (y < 1 || y >3) || !Taken || !Finish);

    }


    static boolean checkAvailable(char[][] ttc,int x,int y) {
        if(ttc[x - 1][y - 1] != '_')
            return false;
        else {
            return true;
        }
    }
    static char[][] Mark(char[][] ttc,int x,int y,int Turn,int[] o) {
        if(Turn%2 == 0) {
            ttc[x - 1][y - 1] = 'X';
            ++o[0];
        }
        else {
            ttc[x - 1][y - 1] = 'O';
            ++o[1];
        }
        return ttc;
    }
    static void printArray(char[][] ttc) {
        System.out.println("\t  Tic-Tac-Toe Game");
        System.out.println("---------\t    The Coordinates");
        System.out.println("| " + ttc[0][0] + " " + ttc[0][1] + " " + ttc[0][2] + " |\t\t[1,1][1,2][1,3]");
        System.out.println("| " + ttc[1][0] + " " + ttc[1][1] + " " + ttc[1][2] + " |\t\t[2,1][2,2][2,3]");
        System.out.println("| " + ttc[2][0] + " " + ttc[2][1] + " " + ttc[2][2] + " |\t\t[3,1][3,2][3,3]");
        System.out.println("---------");
    }
    static boolean evaluateGame(char[][] ttc,int[] o) {
        char w = evaluateWin(ttc);
        if(w == '_' && o[0]+o[1] == 9){
            System.out.println("Draw");
            return true;
        }
        if(w == 'X' || w == 'O') {
            System.out.println(w + " wins");
            return true;
        }
        return false;
    }
    static char evaluateWin(char[][] ttc){
        int count = 0;
        char state = '_';
        for (int i = 0; i < 3; i++) {
            if(ttc[i][0] == ttc[i][1] && ttc[i][1] == ttc[i][2]) {
                state = ttc[i][0];
                ++count;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (ttc[0][i] == ttc[1][i] && ttc[1][i] == ttc[2][i]) {
                state = ttc[0][i];
                ++count;
            }
        }
        if( ttc[0][0] == ttc[1][1] && ttc[1][1] == ttc[2][2]) {
            state = ttc[0][0];
            ++count;
        }
        if(ttc[0][2] == ttc[1][1] && ttc[1][1] == ttc[2][0]){
            state = ttc[0][2];
            ++count;
        }
        if(count == 1)
            return state;
        return '_';
    }
}