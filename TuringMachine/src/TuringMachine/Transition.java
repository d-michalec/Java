/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TuringMachine;

/**
 *
 * @author damianmichalec
 */
public class Transition {
    private String fromState;
    private String read;
    private String write;
    private String direction;
    private String toState;

    public Transition() {
    }

    public String getFromState() {
        return fromState;
    }

    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        if(write.equals(""))
            write = " ";
        this.write = write;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getToState() {
        return toState;
    }

    public void setToState(String toState) {
        this.toState = toState;
    }

    public String runTransition(Tape tape) {
        fromState = toState;
        tape.updateTape(direction, write);
        return toState;
    }
        
    
}
