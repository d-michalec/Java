/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TuringMachine;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author damianmichalec
 */
class Tape {
    private List cells = new ArrayList<String>();
    private int headPosition;

    public void updateTape(String direction, String write){
        cells.set(headPosition, write);
        
        if(direction.equals("R"))
            headPosition++;
        else
            headPosition--;
        
        if(headPosition >= cells.size()){
            cells.add(headPosition," ");
        }
        if(headPosition < 0)
            cells.add(0, " ");
    }
    
    public void initialize(String input){
        cells.removeAll(cells);
        for(int i = 0; i < input.length(); i++){
            cells.add(input.substring(i , i + 1));
        }
        headPosition = 0;
    }
    
    public String currentValue(){
        return cells.get(headPosition).toString();
    }

    public int getSize() {
        return cells.size();
    }
}
