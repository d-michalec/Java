/**
 * Program that reads Turing Machine specification form a text file, and based
 * on provided start state, accept states and transitions determines whether the input
 * provided by the user is within the alphabet of a given automaton.
 */
package TuringMachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author damianmichalec
 */
public class Main {
    /**
     * Main driver of the program
     * @param args 
     */
    public static void main(String[] args) {
        run();
    }
    /**
     * Method processes data from the file, outputs the alphabet accepted by the pda
     * and it reads in the input from the user
     */
     private static void run() {
         TuringMachine turingMachine = new TuringMachine();
         
         processFile(turingMachine);
         turingMachine.printAlphabet();
         while(turingMachine.run(JOptionPane.showInputDialog
        ("Enter input or type 'exit' to terminate program:"))){}
    }
     /**
      * Method reads in data from the text file, and populates appropriate data
      * structures with it. File contains data regarding the PDA.
      * @param turingMachine 
      */
    private static void processFile(TuringMachine turingMachine) {
        // List to store data in the file
        List data = new ArrayList<String>();
        //open a file using JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a text file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
         
            try {
                Scanner myReader = new Scanner(file);
                //assign data from the file to the ArrayList
                while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                data.add(line);
                }
                assignData(data, turingMachine);
                myReader.close();
            } 
         
            catch (FileNotFoundException e)
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
    /**
     * Method that populates data structures with information regarding the PDA.
     * @param data
     * @param turingMachine 
     */
    private static void assignData(List data, TuringMachine turingMachine) {
        
         turingMachine.setStartState(data.get(0).toString());
            
         turingMachine.setAcceptState(data.get(1).toString());
         
         turingMachine.setRejectState(data.get(2).toString());
         
         turingMachine.createAlphabet(data.get(3).toString());
         
         turingMachine.setTransitions(data.subList(4, data.size()));
            
    }
}
