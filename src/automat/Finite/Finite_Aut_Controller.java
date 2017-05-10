package automat.Finite;

import Main.some_gr;
import automat.Jump_Table;
import automat.State;
import graph.graph;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Optional;


public class Finite_Aut_Controller {
    private static Stage stage;
    @FXML
    private AnchorPane parentPane;
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
    @FXML
    private TextField q0;
    @FXML
    private TextField qn;
    @FXML
    private TextField input_lent;
    @FXML
    private Button begin_aut;
    @FXML
    private Button doStep_but;
    @FXML
    private Button load_button;
    private Parent root;
    private Finite_Automation aut;
    private graph.Graph frame;

    public Finite_Aut_Controller() {

    }

    public Finite_Aut_Controller(Stage stage, Parent root) {
        this.stage = stage;
        this.root = root;
    }

    public void initialize() {

        Jump_Table finite_table = new Jump_Table(show_table_but, draw_graph_but, jump_table, null, n, m);
        finite_table.Create_Table();

        draw_graph_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String[] alph = finite_table.get_Alphabet();
                int N = Integer.parseInt(n.getText());
                int M = Integer.parseInt(m.getText());
                for (int i = 0; i < N; i++) {
                    System.out.println(alph[i]);
                }
                System.out.print("alphabet\n");
                int[][] states = finite_table.getStates();
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {


                        System.out.println(states[i][j]);
                    }
                }
                State[] States = new State[M];
                int r = 200;
                int xc = 200;
                int yc = 200;
                for (int i = 0; i < M; i++) {
                    States[i] = new State(String.valueOf(i), String.valueOf(i + 1), (int) (xc + (int) r * Math.cos((Math.PI) * i * 2.0f / M)), (int) (yc + r * Math.sin((Math.PI) * i * 2.0f / M)));

                }
                aut = new Finite_Automation(N, M, states, States, alph);
                frame = new graph.Graph(aut);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 720);
                frame.setVisible(true);
                finite_table.removeTable();
                // Finite_Automation avt = new Finite_Automation(Integer.parseInt(n.getText()),Integer.parseInt(m.getText()),);

            }
        });

        saveToXML.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (aut != null) {
                    TextInputDialog dialog = new TextInputDialog("walter");
                    dialog.setTitle("Text Input Dialog");
                    dialog.setHeaderText("Look, a Text Input Dialog");
                    dialog.setContentText("Please enter your name:");

// Traditional way to get the response value.
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        System.out.println("Your name: " + result.get());
                    }

// The Java 8 way to get the response value (with lambda expression).
                    result.ifPresent(name -> System.out.println("Your name: " + name));
                    aut = (Finite_Automation) frame.SaveGraph(aut);
                    aut.SaveToXML(result.get());
                    some_gr s = new some_gr();
                    s.setSize(400, 320);
                    s.setVisible(true);

                }


                // Finite_Automation avt = new Finite_Automation(Integer.parseInt(n.getText()),Integer.parseInt(m.getText()),);

            }
        });
        begin_aut.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (aut != null) {
                    aut = (Finite_Automation) frame.SaveGraph(aut);
                    aut.begin_aut(q0, qn, input_lent);
                    frame.Show_vert(String.valueOf(aut.getIndex()), "");
                }

            }
        });
        doStep_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (aut != null) {

                    frame.Show_vert(String.valueOf(aut.getCurr_state()), aut.getCurr_symbol());
                    if (aut.getIndex() < aut.getInput_lent().length) {
                        aut.doStep();
                    }
                }
            }
        });
        load_button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextInputDialog dialog = new TextInputDialog("newFile.xml");
                dialog.setTitle("Enter filename");
                dialog.setHeaderText("Look, a Text Input Dialog");
                dialog.setContentText("Please enter filename:");

// Traditional way to get the response value.
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    System.out.println("Your name: " + result.get());
                }

// The Java 8 way to get the response value (with lambda expression).
                result.ifPresent(name -> System.out.println("Your name: " + name));
                if (aut != null) {
                    aut.LoadAutomation(result.get());
                } else {
                    aut = new Finite_Automation();
                    aut.LoadAutomation(result.get());
                }
                frame = new graph.Graph(aut);
                frame.setSize(800, 720);
                frame.setVisible(true);
            }
        });
    }

}
