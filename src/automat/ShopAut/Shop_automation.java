package automat.ShopAut;

import automat.Automation;
import automat.State;

/**
 * Created by Диана on 09.05.2017.
 */
public class Shop_automation extends Automation {
    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public void setIndex(int index) {

    }

    @Override
    public int getCurr_state() {
        return 0;
    }

    @Override
    public void setCurr_state(int curr_state) {

    }

    @Override
    public String getCurr_symbol() {
        return null;
    }

    @Override
    public void setCurr_symbol(String curr_symbol) {

    }

    @Override
    public int getAlphabet_size() {
        return 0;
    }

    @Override
    public void setAlphabet_size(int alphabet_size) {

    }

    @Override
    public int getStates_count() {
        return 0;
    }

    @Override
    public void setStates_count(int states_count) {

    }

    @Override
    public int[][] getJump_table() {
        return new int[0][];
    }

    @Override
    public void setJump_table(int[][] jump_table) {

    }

    @Override
    public State[] getStates() {
        return new State[0];
    }

    @Override
    public void setStates(State[] states) {

    }

    @Override
    public String[] getAlphabet() {
        return new String[0];
    }

    @Override
    public void setAlphabet(String[] alphabet) {

    }

    @Override
    public int getQ0() {
        return 0;
    }

    @Override
    public int[] getQn() {
        return new int[0];
    }

    @Override
    public String[] getInput_lent() {
        return new String[0];
    }
}
