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

    public void begin_aut(TextField q0, TextField input_field) {
        this.index = 0;
        this.setQ0(q0.getText());


        this.output_lent.clear();
        this.input_lent = (input_field.getText().split(" "));
        this.curr_state = (getStateByName(this.q0));
        this.curr_symbol = this.input_lent[0];
    }

    public void doStep() {
        System.out.println("curr_state= " + curr_state.getName() + "curr symbol=" + curr_symbol + "  " + alphabet_size);
        System.out.println("lambda");
        for (int i = 0; i < lambda.length; i++) {
            System.out.println(lambda[i]);
        }
        for (int i = 0; i < getAlphabet_size(); i++) {
            if (jump_table[i][Integer.parseInt(curr_state.getId())] != -1) {
                //System.out.println("jump_table[i][curr_state] = " + jump_table[i][Integer.parseInt(curr_state.getId())] + "getAlphabet()[i]l=" + getAlphabet()[i]);
                if (getCurr_symbol().equals(getAlphabet()[i])) {

                    output_lent.add(lambda[Integer.parseInt(curr_state.getId())]);
                    setCurr_state(getStateById(String.valueOf(jump_table[i][Integer.parseInt(curr_state.getId())])));
                    System.out.println("\n cur state id = " + String.valueOf(jump_table[i][Integer.parseInt(curr_state.getId())]));
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


}
