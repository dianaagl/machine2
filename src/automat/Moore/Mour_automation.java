package automat.Moore;

import automat.Automation;
import automat.State;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Created by Диана on 01.04.2017.
 */
public class Mour_automation extends Automation {
    public ArrayList<String> output_lent = new ArrayList<>();
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
    private String[] lambda;

    public Mour_automation() {
    }

    public Mour_automation(int[][] jump_table, String[] lambda, String[] alfabet, State[] States, int alfabet_size, int states_size) {
        this.jump_table = jump_table;
        this.lambda = lambda;
        this.alphabet = alfabet;
        this.alphabet_size = alfabet_size;
        this.states_count = states_size;
        this.States = States;
    }

    public String[] getLambda() {
        return lambda;
    }

    public void setLambda(String[] lambda) {
        this.lambda = lambda;
    }

    public void begin_aut(TextField q0, TextField qn, TextField input_field) {
        this.index = 0;
        this.setQ0(Integer.parseInt(q0.getText()));
        String[] s = qn.getText().split(" ");
        this.qn = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            this.qn[i] = Integer.parseInt(s[i]);
        }
        this.output_lent.clear();
        this.input_lent = (input_field.getText().split(" "));
        this.curr_state = (this.q0);
        this.curr_symbol = this.input_lent[0];
    }

    public void doStep() {
        System.out.println("curr_state= " + curr_state + "curr symbol=" + curr_symbol + "  " + alphabet_size);
        System.out.println("lambda");
        for (int i = 0; i < lambda.length; i++) {
            System.out.println(lambda[i]);
        }
        for (int i = 0; i < getAlphabet_size(); i++) {
            if (jump_table[i][curr_state] != -1) {
                System.out.println("jump_table[i][curr_state] = " + jump_table[i][curr_state] + "getAlphabet()[i]l=" + getAlphabet()[i]);
                if (getCurr_symbol().equals(getAlphabet()[i])) {
                    System.out.println("curr+state = " + jump_table[i][curr_state]);
                    output_lent.add(lambda[curr_state]);
                    setCurr_state(jump_table[i][curr_state]);

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

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int getCurr_state() {
        return curr_state;
    }

    @Override
    public void setCurr_state(int curr_state) {
        this.curr_state = curr_state;
    }

    @Override
    public String getCurr_symbol() {
        return curr_symbol;
    }

    @Override
    public void setCurr_symbol(String curr_symbol) {
        this.curr_symbol = curr_symbol;
    }

    @Override
    public int getAlphabet_size() {
        return alphabet_size;
    }

    @Override
    public void setAlphabet_size(int alphabet_size) {
        this.alphabet_size = alphabet_size;
    }

    @Override
    public int getStates_count() {
        return states_count;
    }

    @Override
    public void setStates_count(int states_count) {
        this.states_count = states_count;
    }

    @Override
    public int[][] getJump_table() {
        return jump_table;
    }

    @Override
    public void setJump_table(int[][] jump_table) {
        this.jump_table = jump_table;
    }

    @Override
    public State[] getStates() {
        return States;
    }

    @Override
    public void setStates(State[] states) {
        this.States = states;
    }

    @Override
    public String[] getAlphabet() {
        return alphabet;
    }

    @Override
    public void setAlphabet(String[] alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public int getQ0() {
        return q0;
    }

    @Override
    public int[] getQn() {
        return qn;
    }

    @Override
    public String[] getInput_lent() {
        return input_lent;
    }
}
