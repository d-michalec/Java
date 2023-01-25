/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TuringMachine;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author damianmichalec
 */
public class TuringMachine {
    Transition transition = new Transition();
    
    private String startState;
    private String acceptState;
    private String rejectState;
    private List transitions = new ArrayList<Transition>();
    private List<String> alphabet = new ArrayList<String>();
    Tape tape = new Tape();
    /**
     * Method that validates the input, runs the PDA, and displays a message.
     * @param input
     * @return 
     */
    public boolean run(String input){
        createTape(input);
        String currentState = "";
        
        // if input is 'exit' we exit the program
        if(!input.equals("exit")){
            // if input does not match the alphabet provided the machine won't run
            if(validateInput(input)){
                currentState = runMachine();
                displayMessage(validateCurrentState(currentState));
            }
            
            return true;
            
        }
        return false;
    }
    /**
     * Method handles transition from one state to another.
     * @param input
     * @return - returns a String where machine has stopped.
     */
    private String runMachine() {
        
        String fromState = "", read = "", write, direction, toState;
        String currentState = startState;
        String [] arr;
        while(!currentState.equals(acceptState) && !currentState.equals(rejectState)){
            for(int j = 0; j < transitions.size(); j++){
                arr = transitions.get(j).toString().split(",");
                
                fromState = arr[0].split(" ")[0].trim();
                read = arr[0].split(" ")[1];
                read = read.substring(1, read.length());
                write = arr[1];
                if(read.equals(""))
                    read = " ";
                direction = arr[2].split(" ")[0].substring(0,1);
                toState = arr[2].split(" ")[1];
                
                if(fromState.equals(currentState) && read.equals(tape.currentValue())){
                    transition.setFromState(fromState);
                    transition.setRead(read);
                    transition.setWrite(write);
                    transition.setDirection(direction);
                    transition.setToState(toState);
                    currentState = transition.runTransition(tape);
                }
            }
        }
        return currentState;
    }
    /**
     * Creates the alphabet of the automaton.
     */
    protected void createAlphabet(String str){
        String [] letters = str.split(",");
        String letter;
        for(int i = 0; i < letters.length; i++){
            letter = letters[i].trim();
            alphabet.add(letter);
    }
        removeDuplicates();
    }
    /**
     * Validates the input passed in. 
     * @param input
     * @return 
     */
    private boolean validateInput(String input) {
        StringBuilder sb = new StringBuilder();
        String str;
       
        if(input != null){
            
         
            for (String letter : alphabet) {
                sb.append(letter);
            }
            str = sb.toString();

            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (str.indexOf(c) == -1) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Removes duplicates from the alphabet.
     */
    private void removeDuplicates() {
        Set<String> set = new LinkedHashSet<>();
        set.addAll(alphabet);
        alphabet.clear();
        alphabet.addAll(set); 
    }
   
     /**
      * Prints the alphabet accepted by the automaton
      */
    public void printAlphabet() {
        String message;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < alphabet.size(); i++)
        {
            sb.append(alphabet.get(i));
        }
        message = "Alphabet accepted by the dfa: " + sb.toString();
        JOptionPane.showMessageDialog(null, message);
        System.out.println();
    }
    
    
    /**
     * Displays the message whether the alphabet was accepted or not.
     * @param correct 
     */
    private void displayMessage(boolean correct){
        if(correct)
            JOptionPane.showMessageDialog(null, "Accepted! Correct final state.");
        else{
            JOptionPane.showMessageDialog(null, "Rejected! Incorrect final state.");
        }
    }
     private boolean validateCurrentState(String currentState) {
        return currentState.equals(acceptState);
     }
    
    private void createTape(String input) {
        tape.initialize(input);
    }
     
    public void setStartState(String startState) {
        this.startState = startState;
    }

    public void setAcceptState(String acceptStates) {
        this.acceptState = acceptStates;
    }

    public void setTransitions(List transitions) {
        this.transitions = transitions;
    }

    void setRejectState(String rejectState) {
        this.rejectState = rejectState;
    }

   

    
}
