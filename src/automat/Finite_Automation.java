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
}
