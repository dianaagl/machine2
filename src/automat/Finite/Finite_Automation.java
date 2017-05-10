package automat.Finite;

import automat.Automation;
import automat.State;
import javafx.scene.control.TextField;

/**
 * Created by Диана on 28.04.2017.
 */
public class Finite_Automation extends Automation {
    private int index;
    private int curr_state;
    private String curr_symbol;
    private int alphabet_size;
    private int states_count;
    private int[][] jump_table;
    private State[] States;
    private String[] alphabet;
    private int q0;
    private int[] qn;
    private String[] input_lent;


    public Finite_Automation() {

    }

    public Finite_Automation(int alphabet_size, int states_count, int[][] jump_table, State[] states, String[] alphabet) {
        this.setAlphabet_size(alphabet_size);
        this.setStates_count(states_count);
        this.setJump_table(jump_table);
        setStates(states);
        this.setAlphabet(alphabet);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCurr_state() {
        return curr_state;
    }

    public void setCurr_state(int curr_state) {
        this.curr_state = curr_state;
    }

    public String getCurr_symbol() {
        return curr_symbol;
    }

    public void setCurr_symbol(String curr_symbol) {
        this.curr_symbol = curr_symbol;
    }

    public int getQ0() {
        return q0;
    }

    public void setQ0(int q0) {
        this.q0 = q0;
    }

    public int[] getQn() {
        return qn;
    }

    public void setQn(int[] qn) {
        this.qn = qn;
    }

    public String[] getInput_lent() {
        return input_lent;
    }

    public void setInput_lent(String[] input_lent) {
        this.input_lent = input_lent;
    }

    private State findStateById(String name) {
        for (int i = 0; i < getStates_count(); i++) {
            if (getStates()[i].getId().equals(name)) {
                return getStates()[i];
            }
        }
        return null;
    }

    public void begin_aut(TextField q0, TextField qn, TextField input_field) {
        this.index = 0;
        this.setQ0(Integer.parseInt(q0.getText()));
        String[] s = qn.getText().split(" ");
        this.setQn(new int[s.length]);
        for (int i = 0; i < s.length; i++) {
            this.getQn()[i] = Integer.parseInt(s[i]);
        }
        this.setInput_lent(input_field.getText().split(" "));
        this.setCurr_state(this.getQ0());
        this.setCurr_symbol(this.getInput_lent()[0]);
    }

    public void doStep() {
        System.out.println("curr_state= " + curr_state + "curr symbol=" + curr_symbol + "  " + alphabet_size);

        for (int i = 0; i < getAlphabet_size(); i++) {
            if (jump_table[i][curr_state] != -1) {
                System.out.println("jump_table[i][curr_state] = " + jump_table[i][curr_state] + "getAlphabet()[i]l=" + getAlphabet()[i]);
                if (getCurr_symbol().equals(getAlphabet()[i])) {


                    setCurr_state(getJump_table()[i][getCurr_state()]);
                    index++;
                    if (getIndex() < getInput_lent().length) {
                        setCurr_symbol(getInput_lent()[getIndex()]);
                    } else {
                        System.out.println("Error:lent is over");
                    }
                    break;
                }
            }
        }


    }

    public int getAlphabet_size() {
        return alphabet_size;
    }

    public void setAlphabet_size(int alphabet_size) {
        this.alphabet_size = alphabet_size;
    }

    public int getStates_count() {
        return states_count;
    }

    public void setStates_count(int states_count) {
        this.states_count = states_count;
    }

    public int[][] getJump_table() {
        return jump_table;
    }

    public void setJump_table(int[][] jump_table) {
        this.jump_table = jump_table;
    }

    public State[] getStates() {
        return States;
    }

    public void setStates(State[] states) {
        States = states;
    }

    public String[] getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String[] alphabet) {
        this.alphabet = alphabet;
    }
}
