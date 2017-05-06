package sample;

import automat.Finite_Automation;
import automat.State;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;


public class Finite_Aut_Controller {
    @FXML
    private   AnchorPane parentPane;
    @FXML
    private AnchorPane jump_table;
    @FXML
    private Button show_table_but;
    @FXML
    private Button draw_graph_but;
    @FXML
    private Button saveToXML;
    @FXML
    private TextField n;
    @FXML
    private TextField m;
    private static Stage stage;
    private Parent root;
    private Finite_Automation aut;
    private HabrGraph frame;
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
                System.out.print("alphabet\n");
               int [][] states  = finite_table.getStates();
               for(int i = 0;i < N;i++){
                    for(int j = 0;j < M;j++) {


                        System.out.println(states[i][j]);
                    }
                }
                State [] States =new State[M];
                int r = 200;
                int xc = 200;
                int yc = 200;
                for(int i = 0;i < M;i++){
                    States[i] = new State(String.valueOf(i),String.valueOf(i+1),(int)(xc + (int)r*Math.cos((Math.PI )*i*2.0f/M)),(int)(yc + r* Math.sin((Math.PI)*i*2.0f/M)));

                }
                aut = new Finite_Automation(N,M,states,States,alph);
                frame = new HabrGraph(aut);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 720);
                frame.setVisible(true);

               // Finite_Automation avt = new Finite_Automation(Integer.parseInt(n.getText()),Integer.parseInt(m.getText()),);

            }
        });
        saveToXML.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(aut != null){
                    aut = frame.SaveGraph(aut);
                    aut.SaveToXML();
                    some_gr s = new some_gr();
                    s.setSize(400, 320);
                    s.setVisible(true);
                    frame.Show_vert(String.valueOf(1));
                }

                // Finite_Automation avt = new Finite_Automation(Integer.parseInt(n.getText()),Integer.parseInt(m.getText()),);

            }
        });
    }

}
