package automat.ShopAut;

import automat.Automation;
import automat.State;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Диана on 09.05.2017.
 */
public class Shop_automation extends Automation {
    public ArrayList<String> output_lent = new ArrayList<>();
    protected HashMap<Key, Takt> rule_map = new HashMap<Key, Takt>();
    int num_of_rules;

    private Stack<String> shop = new Stack<String>();
    private ArrayList<State> States = new ArrayList<State>();
    private ArrayList<String> alfabet = new ArrayList<String>();


    public Shop_automation(ArrayList<State> states, ArrayList<String> alfabet, int num_of_states, int alfabet_size, int num_of_rules, HashMap<Key, Takt> rule_map) {
        States = states;
        this.alfabet = alfabet;
        this.states_count = num_of_states;
        this.alphabet_size = alfabet_size;
        this.num_of_rules = num_of_rules;
        this.rule_map = rule_map;
    }

    public Shop_automation() {
        num_of_rules = 0;
    }

    public Stack<String> getShop() {
        return shop;
    }

    public String getLabel() {
        if (shop.peek().equals("")) {
            shop.push("$");
        }
        return curr_symbol + "," + shop.peek() + "," + rule_map.get(new Key(curr_state.getId(), curr_symbol, shop.peek())).getStek();
    }

    public String getNextState() {
        return rule_map.get(new Key(curr_state.getId(), curr_symbol, shop.peek())).getId_state();
    }

    public int getIndexOf(String id) {
        for (int i = 0; i < States.size(); i++) {
            if (States.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public void begin_aut(TextField q0, TextField qn, TextField input_field) {
        this.index = 0;
        this.q0 = q0.getText();
        this.qn = qn.getText().split(" ");

        this.output_lent.clear();
        if (!this.shop.isEmpty()) {
            this.shop.clear();

        }
        this.shop.add("$");
        this.input_lent = (input_field.getText().split(" "));
        this.curr_state = getStateByName(this.q0);
        this.curr_symbol = this.input_lent[0];
    }

    public void doStep() {
        Takt newVal = new Takt("1", "d f");
        if (shop.isEmpty()) {
            //newVal =
            newVal = rule_map.get(new Key(curr_state.getId(), curr_symbol, "$"));
            System.out.println(curr_state.getId() + "," + curr_symbol + "$");
        } else {
            newVal = rule_map.get(new Key(curr_state.getId(), curr_symbol, shop.pop()));
            //System.out.print(States.get(curr_state).getId()+"  " +curr_symbol+"$" + shop.pop());

        }

        if (newVal != null) {
            System.out.println("\ncurr_state=" + curr_state + "new c_state=" + getIndexOf(newVal.getId_state()));
            curr_state = getStateById(newVal.getId_state());
            String[] stek_mas = newVal.getStek().split(" ");
            for (int i = 0; i < stek_mas.length; i++) {
                if (!stek_mas[stek_mas.length - i - 1].equals("")) {
                    shop.push(stek_mas[stek_mas.length - i - 1]);
                }
            }
            index++;
            if (getIndex() < getInput_lent().length) {
                curr_symbol = input_lent[index];
            } else {
                System.out.println("Error:lent is over");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("end");
            String s = "no steps";

            alert.setContentText(s);

            alert.showAndWait();
        }

    }

    public ArrayList<String> getAlfabet() {
        return alfabet;
    }

    public void setAlfabet(ArrayList<String> alfabet) {
        this.alfabet = alfabet;
    }

    public void setStates(ArrayList<State> states) {
        States = states;
    }

    public ArrayList<State> getStatesArray() {
        return States;
    }

    public HashMap<Key, Takt> getRule_map() {
        return rule_map;
    }

    public int getNum_of_rules() {
        return num_of_rules;
    }

    public void setNum_of_rules(int num_of_rules) {
        this.num_of_rules = num_of_rules;
    }

    public boolean contains(String name) {
        for (int i = 0; i < States.size(); i++) {
            if (States.get(i).getId().equals(name)) {
                return false;
            }
        }
        return true;
    }


}
