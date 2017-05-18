package automat.ShopAut;

/**
 * Created by Диана on 17.05.2017.
 */
public class Takt {
    String id_state;
    String stek;

    public Takt(String id_state, String stek) {
        this.id_state = id_state;
        this.stek = stek;
    }

    public String getId_state() {
        return id_state;
    }

    public String getStek() {
        return stek;
    }


}
