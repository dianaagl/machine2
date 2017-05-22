package automat.Finite;

import automat.Automation;
import automat.State;
import javafx.scene.control.TextField;

/**
 * Created by Диана on 28.04.2017.
 */
public class Finite_Automation extends Automation {


    public Finite_Automation() {

    }

    public Finite_Automation(int alphabet_size, int states_count, int[][] jump_table, State[] states, String[] alphabet) {
        this.setAlphabet_size(alphabet_size);
        this.setStates_count(states_count);
        this.setJump_table(jump_table);
        setStates(states);
        this.setAlphabet(alphabet);
    }




    public void begin_aut(TextField q0, TextField qn, TextField input_field) {
        index = 0;
        this.setQ0(q0.getText());
        String[] s = qn.getText().split(" ");
        this.setQn(new String[s.length]);
        for (int i = 0; i < s.length; i++) {
            this.getQn()[i] = s[i];
        }
        this.setInput_lent(input_field.getText().split(" "));
        this.setCurr_state(super.getStateByName(this.getQ0()));
        this.setCurr_symbol(this.getInput_lent()[0]);
    }

    public void doStep() {
        System.out.println("curr_state= " + curr_state + "curr symbol=" + curr_symbol + "  " + alphabet_size);

        for (int i = 0; i < getAlphabet_size(); i++) {
            if (jump_table[i][Integer.parseInt(curr_state.getId())] != -1) {
                System.out.println("jump_table[i][curr_state] = " + jump_table[i][Integer.parseInt(curr_state.getId())] + "getAlphabet()[i]l=" + getAlphabet()[i]);
                if (getCurr_symbol().equals(getAlphabet()[i])) {

                    int id = getJump_table()[i][Integer.parseInt(getCurr_state().getId())];
                    setCurr_state(getStateById(String.valueOf(id)));
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
