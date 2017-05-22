package automat.Finite;

import automat.Aut_cont;
import automat.Jump_Table;
import automat.State;
import graph.graph;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class Finite_Aut_Controller extends Aut_cont {
    @FXML
    private AnchorPane parentPane;
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

    public Finite_Aut_Controller() {

    }

    /**
     * Сохраняет файл в файл адресатов, который в настоящее время открыт.
     * Если файл не открыт, то отображается диалог "save as".
     */


    public Finite_Aut_Controller(Stage stage, Parent root) {
        super(stage, root);

    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            if (aut != null) {
                aut = (Finite_Automation) aut.LoadAutomation(file.getAbsolutePath());
            } else {
                aut = new Finite_Automation();
                aut = (Finite_Automation) aut.LoadAutomation(file.getAbsolutePath());
            }
            frame = new graph.Graph(aut, "old");
            frame.setSize(800, 720);
            frame.setVisible(true);

        }
    }

    /**
     * Открывает FileChooser, чтобы пользователь имел возможность
     * выбрать файл, куда будут сохранены данные
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }

            aut = (Finite_Automation) frame.SaveGraph(aut);
            aut.SaveToXML(file.getAbsolutePath());
        }
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
                frame = new graph.Graph(aut, "new");
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 720);
                frame.setVisible(true);

                // Finite_Automation avt = new Finite_Automation(Integer.parseInt(n.getText()),Integer.parseInt(m.getText()),);

            }
        });


        begin_aut.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (aut != null) {
                    aut = (Finite_Automation) frame.SaveGraph(aut);
                    ((Finite_Automation) aut).begin_aut(q0, qn, input_lent);
                    frame.Show_vert(String.valueOf(aut.getIndex()), "");
                }

            }
        });
        doStep_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (aut != null) {


                    if (aut.getIndex() < aut.getInput_lent().length) {
                        frame.Show_vert(aut.getCurr_state().getId(), aut.getCurr_symbol());
                        ((Finite_Automation) aut).doStep();
                    } else {
                        frame.Show_vert(aut.getCurr_state().getId(), "");
                        if (((Finite_Automation) aut).getCurr_state().getName().equals(qn.getText())) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Автомат");
                            String s = "Цепочка принадлежит автомату";

                            alert.setContentText(s);

                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Автомат");
                            String s = "Цепочка не принадлежит автомату";
                            alert.setContentText(s);

                            alert.showAndWait();
                        }
                    }
                }
            }
        });

    }

}
