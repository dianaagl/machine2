package automat;

/**
 * Created by Диана on 28.04.2017.
 */
public class Finite_Automation {
    int alphabet_size;
    int states_count;
    int [][] states;
    String []alphabet;

    public Finite_Automation(int alphabet_size, int states_count, int[][] states, String[] alphabet) {
        this.alphabet_size = alphabet_size;
        this.states_count = states_count;
        this.states = states;
        this.alphabet = alphabet;
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

    public int[][] getStates() {
        return states;
    }

    public void setStates(int[][] states) {
        this.states = states;
    }

    public String[] getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String[] alphabet) {
        this.alphabet = alphabet;
    }
}
