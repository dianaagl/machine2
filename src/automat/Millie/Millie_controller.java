package automat.Millie;

import automat.Jump_Table;
import automat.State;
import graph.graph;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Optional;


public class Millie_controller {
    private static Stage stage;
    @FXML
    private AnchorPane parentPane;
    @FXML
    private AnchorPane lambda_pane;
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
    @FXML
    private Label output_lent;
    private Parent root;
    private Millie_automation aut;
    private graph.Graph frame;

    public Millie_controller() {

    }

    public Millie_controller(Stage stage, Parent root) {
        this.stage = stage;
        this.root = root;
    }

    public void initialize() {

        Jump_Table millie_table = new Jump_Table(show_table_but, draw_graph_but, jump_table, lambda_pane, n, m);
        millie_table.Create_Table();
        millie_table.LambdaMilliePaneConst();


        draw_graph_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String[] alph = millie_table.get_Alphabet();
                String[][] lambda = millie_table.getOutput();
                int N = Integer.parseInt(n.getText());
                int M = Integer.parseInt(m.getText());
                for (int i = 0; i < N; i++) {
                    System.out.println(alph[i]);
                }
                System.out.print("alphabet\n");
                int[][] states = millie_table.getStates();
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
                    States[i] = new State(String.valueOf(i),
                            String.valueOf(i + 1),
                            (int) (xc + (int) r * Math.cos((Math.PI) * i * 2.0f / M)),
                            (int) (yc + r * Math.sin((Math.PI) * i * 2.0f / M)));

                }
                aut = new Millie_automation(states, lambda, alph, States, N, M);
                frame = new graph.Graph(aut);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 720);
                frame.setVisible(true);
                //finite_table.removeTable();
                // Finite_Automation avt = new Finite_Automation(Integer.parseInt(n.getText()),Integer.parseInt(m.getText()),);

            }
        });

        saveToXML.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (aut != null) {
                    TextInputDialog dialog = new TextInputDialog("newFile.xml");
                    dialog.setTitle("Text Input Dialog");
                    dialog.setHeaderText("Look, a Text Input Dialog");
                    dialog.setContentText("Please enter filename:");

                    Optional<String> result = dialog.showAndWait();

                    aut = (Millie_automation) frame.SaveGraph(aut);
                    aut.SaveToXML(result.get());


                }


                // Finite_Automation avt = new Finite_Automation(Integer.parseInt(n.getText()),Integer.parseInt(m.getText()),);

            }
        });
        begin_aut.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (aut != null) {
                    aut = (Millie_automation) frame.SaveGraph((Millie_automation) aut);
                    aut.begin_aut(q0, qn, input_lent);
                    frame.Show_vert(String.valueOf(aut.getIndex()), "");
                }

            }
        });
        doStep_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (aut != null) {

                    frame.Show_vert(String.valueOf(aut.getCurr_state()), aut.getCurr_symbol() + "," + aut.getOut());
                    System.out.println(aut.getCurr_symbol() + "," + aut.getOut());
                    if (aut.getIndex() < aut.getInput_lent().length) {
                        aut.doStep();
                        output_lent.setText("");
                        for (int i = 0; i < aut.output_lent.size(); i++) {
                            output_lent.setText(output_lent.getText() + " " + aut.output_lent.get(i));
                        }
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


// The Java 8 way to get the response value (with lambda expression).

                if (aut != null) {
                    aut = (Millie_automation) aut.LoadAutomation(result.get());
                } else {
                    aut = new Millie_automation();
                    aut = (Millie_automation) aut.LoadAutomation(result.get());
                }
                frame = new graph.Graph(aut);
                frame.setSize(800, 720);
                frame.setVisible(true);
            }
        });

    }


}
