package automat;

/**
 * Created by Диана on 31.03.2017.
 */
public class State {
    String id;
    String name;
    int x;
    int y;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State(String id, String name,int x,int y) {

        this.id = id;
        this.name = name;
        this.x= x;
        this.y = y;
    }
}
