package simple_tic_tac_toe;

/*
Author: Damian Michalec
A simple program that recreates a famous tictactoe game.
Functonality:
Program prints out a board, and the user is supposed to provide an input
containing coordinates of one's choice. For instance 1 1, 2 3, 3 1 etc.
 */

import java.util.Arrays;
import java.util.Scanner;
// Main class
public class Main {
    // Main function - driver of the program
    public static void main(String[] args) {
        // count variable allows to switch between X and O marker
        int count = 0;
        boolean isFinished = false;

        // create a 2D array, and print it out
        char[][] board = createGrid();
        printBoard(board);

        // main part of the program, loops until there is a winner
        while(!isFinished) {
            count++;
            updateGrid(board, count);
            printBoard(board);
            isFinished = validation(board);
        }
    }

    // function that prints a current state the board is in
    private static void printBoard(char[][] board) {
        System.out.println("----------");
        for (char[] chars : board) {
            System.out.print("| ");
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println("|");
        }
        System.out.print("----------");
    }
    // updateGird allows players to put down their markers wherever they would like
    private static void updateGrid(char[][] board, int count) {
        Scanner sc = new Scanner(System.in);
        boolean gridUpdated = false;

        while (!gridUpdated) {
            System.out.println();
            String userInput = sc.nextLine();
            int x = 0, y = 0;
            for (int i = 0; i < userInput.length(); i++) {
                x = Character.getNumericValue(userInput.charAt(0));
                y = Character.getNumericValue(userInput.charAt(2));
            }
            if (x > 9 && y > 9) {
                System.out.println("You should enter numbers!");
            } else {
                if (x < 4 && y < 4) {
                    if (board[x - 1][y - 1] == 'X'  || board[x - 1][y - 1] == 'O') {
                        System.out.println("This cell is occupied try another one!");
                    } else {
                        if(count % 2 == 0)
                            board[x - 1][y - 1] = 'O';
                        if(count % 2 == 1)
                            board[x - 1][y - 1] = 'X';

                        gridUpdated = true;
                    }
                } else {
                    System.out.println("Coordinates should be from 1 to 3!");
                }
            }
        }
    }

    // function passes a 2D array into a String which later on is passed to the
    // function isFinished in order to determine the winner
    private static boolean validation(char[][] board) {
        boolean isFinished;
        StringBuilder input = new StringBuilder();
        System.out.println();
        for (char[] chars : board) {
            for (char aChar : chars) {
                input.append(aChar);
            }
        }
        isFinished = checkWinner(input.toString());
        return isFinished;
    }

    // function that creates the grid for a TicTacToe game
    public static char[][] createGrid(){
        char [][] arr = new char[3][3];
        for (char[] chars : arr) {
            Arrays.fill(chars, ' ');
        }
        return arr;
    }
    // function that checks the winner based on a supplied input String,
    // and returns boolean to determine whether the game is finished or not
    public static boolean checkWinner(String input) {
        boolean xWon = false, oWon = false;
        String line = "";
        int xCount = 0, oCount = 0, nullCount = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'X') {
                xCount++;
            }
            if(input.charAt(i) == 'O') {
                oCount++;
            }
            if(input.charAt(i) == ' ') {
                nullCount++;
            }
        }
        if (Math.abs(xCount - oCount) > 1) {
            System.out.println("Impossible");
        }else {

            for (int a = 0; a < 8; a++) {
                switch (a) {
                    case 0 -> line = input.substring(0, 1) + input.charAt(1) + input.charAt(2);
                    case 1 -> line = input.substring(3, 4) + input.charAt(4) + input.charAt(5);
                    case 2 -> line = input.substring(6, 7) + input.charAt(7) + input.charAt(8);
                    case 3 -> line = input.substring(0, 1) + input.charAt(3) + input.charAt(6);
                    case 4 -> line = input.substring(1, 2) + input.charAt(4) + input.charAt(7);
                    case 5 -> line = input.substring(2, 3) + input.charAt(5) + input.charAt(8);
                    case 6 -> line = input.substring(0, 1) + input.charAt(4) + input.charAt(8);
                    case 7 -> line = input.substring(2, 3) + input.charAt(4) + input.charAt(6);
                }

                if (line.equals("XXX"))
                    xWon = true;

                if (line.equals("OOO"))
                    oWon = true;
            }

            if (xWon && oWon){
                System.out.println("Impossible");
                return true;
            }
            else {
                if (xWon) {
                    System.out.println("X wins");
                    return true;
                }
                if (oWon) {
                    System.out.println("O wins");
                    return true;
                }

                if (nullCount > 0) {
                    return false;
                } else {
                    System.out.println("Draw");
                    return true;
                }
            }
        }
        return false;
    }
}
