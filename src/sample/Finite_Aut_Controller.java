package sample;

import automat.Finite_Automation;
import automat.Mour_automation;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Finite_Aut_Controller {
    @FXML
    private AnchorPane jump_table;
    @FXML
    private Button show_table_but;
    @FXML
    private Button draw_graph_but;
    @FXML
    private TextField n;
    @FXML
    private TextField m;
    private static Stage stage;
    private Parent root;
    public Finite_Aut_Controller(){

    }
    public Finite_Aut_Controller(Stage stage,Parent root){
        this.stage = stage;
        this.root = root;
    }
    public void initialize(){

        Jump_Table finite_table = new Jump_Table(show_table_but,draw_graph_but,jump_table,null,n,m);
        finite_table.Create_Table();

        draw_graph_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String [] alph = finite_table.get_Alphabet();
                int N = Integer.parseInt(n.getText());
                int M = Integer.parseInt(m.getText());
                for(int i = 0;i < N;i++){
                    System.out.println(alph[i]);
                }
               int [][] states  = finite_table.getStates();
               for(int i = 0;i < N;i++){
                    for(int j = 0;j < M;j++) {


                        System.out.println(states[i][j]);
                    }
                }
               // Finite_Automation avt = new Finite_Automation(Integer.parseInt(n.getText()),Integer.parseInt(m.getText()),);

            }
        });
    }

}
