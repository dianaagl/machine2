package automat.ShopAut;

/**
 * Created by Диана on 17.05.2017.
 */
public class Key {
    public String id_state;
    public String curr_symb_lent;
    public String curr_symb_stek;

    public Key(String id_state, String curr_symb_lent, String curr_symb_stek) {
        this.id_state = id_state;
        this.curr_symb_lent = curr_symb_lent;
        this.curr_symb_stek = curr_symb_stek;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Key) {
            Key k = (Key) obj;
            if (k == null) {
                return false;
            }
            System.out.println("\nequals=" + id_state + "," + curr_symb_stek + "," + curr_symb_lent);
            System.out.print("equals=" + k.id_state + "," + k.curr_symb_stek + "," + k.curr_symb_lent);
            return id_state.equals(k.id_state) && curr_symb_lent.equals(k.curr_symb_lent) && curr_symb_stek.equals(k.curr_symb_stek);


        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id_state == null) ? 0 : curr_symb_lent.hashCode());
        result = prime * result + id_state.hashCode();
        result = prime * result +
                ((curr_symb_lent == null) ? 0 : curr_symb_lent.hashCode());
        return result;
    }
}
