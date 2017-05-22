package automat.Millie;

import automat.Aut_cont;
import automat.Jump_Table;
import automat.State;
import graph.graph;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class Millie_controller extends Aut_cont {

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
    private TextField n;
    @FXML
    private TextField m;
    @FXML
    private TextField q0;

    @FXML
    private TextField input_lent;
    @FXML
    private Button begin_aut;
    @FXML
    private Button doStep_but;

    @FXML
    private Label output_lent;

    public Millie_controller() {

    }

    /**
     * Сохраняет файл в файл адресатов, который в настоящее время открыт.
     * Если файл не открыт, то отображается диалог "save as".
     */


    public Millie_controller(Stage stage, Parent root) {
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
                aut = (Millie_automation) aut.LoadAutomation(file.getAbsolutePath());
            } else {
                aut = new Millie_automation();
                aut = (Millie_automation) aut.LoadAutomation(file.getAbsolutePath());
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
            if (aut == null) {
                aut = new Millie_automation();
            }
            aut = (Millie_automation) frame.SaveGraph(aut);
            aut.SaveToXML(file.getAbsolutePath());

        }
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
                frame = new graph.Graph(aut, "new");
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 720);
                frame.setVisible(true);
                //finite_table.removeTable();
                // Finite_Automation avt = new Finite_Automation(Integer.parseInt(n.getText()),Integer.parseInt(m.getText()),);

            }
        });


        begin_aut.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (aut != null) {
                    aut = (Millie_automation) frame.SaveGraph((Millie_automation) aut);
                    ((Millie_automation) aut).begin_aut(q0, input_lent);
                    frame.Show_vert(String.valueOf(aut.getIndex()), "");
                }

            }
        });
        doStep_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (aut != null) {

                    System.out.println(aut.getCurr_symbol() + "," + ((Millie_automation) aut).getOut());
                    if (aut.getIndex() < aut.getInput_lent().length) {
                        frame.Show_vert(aut.getCurr_state().getId(), aut.getCurr_symbol() + "," + ((Millie_automation) aut).getOut());

                        ((Millie_automation) aut).doStep();
                        output_lent.setText("");
                        for (int i = 0; i < ((Millie_automation) aut).output_lent.size(); i++) {
                            output_lent.setText(output_lent.getText() + " " + ((Millie_automation) aut).output_lent.get(i));
                        }
                    } else {
                        frame.Show_vert(aut.getCurr_state().getId(), "");

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Автомат");
                        String s = "конец входной цепочки";
                        alert.setContentText(s);

                        alert.showAndWait();

                    }
                }
            }
        });


    }


}
