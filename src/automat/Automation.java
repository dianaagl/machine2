package automat;

/**
 * Created by Диана on 31.03.2017.
 */
public abstract class Automation {
    private String[]  input;
    private String output[];
    private State states;
    abstract void CreateAutomation();
    public String[] getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input.split(" ");
    }
}
