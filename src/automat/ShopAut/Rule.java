package automat.ShopAut;

import automat.State;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Диана on 12.05.2017.
 */
public class Rule {
    private static final String arrow = "--->";
    int number;

    State q0;
    State qn;
    String a;
    String z;
    String[] zn;

    public Rule(State q0, State qn, String a, String z, String[] zn, int number) {
        this.q0 = q0;
        this.qn = qn;
        this.a = a;
        this.z = z;
        this.zn = zn;
        this.number = number;
    }

    public static String getArrow() {
        return arrow;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public StringProperty getQ0() {
        return new SimpleStringProperty(q0.getName());
    }

    public void setQ0(State q0) {

        this.q0 = q0;
    }

    public StringProperty getQn() {
        return new SimpleStringProperty(qn.getName());
    }

    public void setQn(State qn) {
        this.qn = qn;
    }

    public StringProperty getA() {
        return new SimpleStringProperty(a);
    }

    public void setA(String a) {
        this.a = a;
    }

    public StringProperty getZ() {
        return new SimpleStringProperty(z);
    }

    public void setZ(String z) {
        this.z = z;
    }

    public StringProperty getZn() {
        String zzz = "";
        if (zn == null) return new SimpleStringProperty("");
        for (int i = 0; i < zn.length; i++) {
            zzz += " " + zn[i];
        }
        return new SimpleStringProperty(zzz);
    }

    public void setZn(String zn) {
        this.zn = zn.split(" ");
    }
}
