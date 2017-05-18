package automat.ShopAut;

import automat.State;
import graph.graph;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class Shop_controller {
    private static Stage stage;
    @FXML
    private Button add_rule_butt;
    @FXML
    private TableView<Rule> rule_table;
    @FXML
    private TableColumn<Rule, String> q0;
    @FXML
    private TableColumn<Rule, String> arrow;
    @FXML
    private TableColumn<Rule, Integer> number;
    @FXML
    private TableColumn<Rule, String> a;
    @FXML
    private TableColumn<Rule, String> z0;
    @FXML
    private TableColumn<Rule, String> qn;
    @FXML
    private TableColumn<Rule, String> zn;
    @FXML
    private Button draw_graph_but;
    @FXML
    private Button begin_aut;
    @FXML
    private Button doStep_but;
    @FXML
    private TextField Sq0;
    @FXML
    private TextField Sqn;
    @FXML
    private TextField input_lent;
    @FXML
    private Label shop_lent;
    private Parent root;

    private Shop_automation aut;
    private graph.Graph frame;
    private ObservableList<Rule> rules = FXCollections.observableArrayList();
    public Shop_controller() {

    }

    public Shop_controller(Stage stage, Parent root) {
        this.stage = stage;
        this.root = root;
    }

    public void initialize() {

        q0.setCellValueFactory(cellData -> cellData.getValue().getQ0());
        a.setCellValueFactory(cellData -> cellData.getValue().getA());
        z0.setCellValueFactory(cellData -> cellData.getValue().getZ());
        qn.setCellValueFactory(cellData -> cellData.getValue().getQn());
        zn.setCellValueFactory(cellData -> cellData.getValue().getZn());
        number.setCellValueFactory((new PropertyValueFactory<Rule, Integer>("number")));
        arrow.setCellValueFactory(cellData -> new SimpleStringProperty("---->"));
        q0.setCellFactory(TextFieldTableCell.forTableColumn());
        qn.setCellFactory(TextFieldTableCell.forTableColumn());
        a.setCellFactory(TextFieldTableCell.forTableColumn());
        z0.setCellFactory(TextFieldTableCell.forTableColumn());
        zn.setCellFactory(TextFieldTableCell.forTableColumn());

        add_rule_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rules.add(new Rule(new State("", "", 0, 0), new State("", "", 0, 0), "", "", null,
                        rule_table.getItems().size() + 1));
                rule_table.setItems(rules);
            }
        });
        doStep_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                shop_lent.setText("");

                if (aut.getIndex() < aut.getInput_lent().length) {
                    frame.Show_vert(aut.getStatesArray().get(aut.getCurr_state()).getId(), aut.getLabel());
                    aut.doStep();
                    for (int i = 0; i < aut.getShop().size(); i++) {
                        shop_lent.setText(shop_lent.getText() + aut.getShop().get(aut.getShop().size() - i - 1));
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("end");
                    String s = "Lent is over";

                    alert.setContentText(s);

                    alert.showAndWait();
                }
            }
        });
        draw_graph_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                aut = new Shop_automation();
                boolean tr = true;
                Rule curr;
                ArrayList<State> states = new ArrayList<State>();
                ArrayList<String> alfabet = new ArrayList<String>();

                HashMap<Key, Takt> rules_map = new HashMap<Key, Takt>();


                for (int i = 0; i < rules.size(); i++) {
                    curr = rules.get(i);
                    if (!curr.getQ0().getValue().equals("") && !curr.getQn().getValue().equals("")
                            && !curr.getA().getValue().equals("")) {

                        if (curr.getZn().getValue().equals("")) {
                            curr.setZn("$");
                        }
                        if (curr.getZ().getValue().equals("")) {
                            curr.setZ("$");
                        }
                        rules_map.put(new Key(curr.getQ0().getValue(), curr.getA().getValue(), curr.getZ().getValue()),
                                new Takt(curr.getQn().getValue(), curr.getZn().getValue()));
                        boolean q0_cont = false;
                        boolean qn_cont = false;
                        for (int j = 0; j < states.size(); j++) {
                            if (states.get(j).getId().equals(curr.getQ0().getValue())) {
                                q0_cont = true;
                            }
                            if (states.get(j).getId().equals(curr.getQn().getValue())) {
                                qn_cont = true;
                            }
                        }
                        if (!q0_cont) {
                            states.add(new State(curr.getQ0().getValue(), curr.getQ0().getValue(), 0, 0));
                        }
                        if (!qn_cont) {
                            states.add(new State(curr.getQn().getValue(), curr.getQn().getValue(), 0, 0));
                        }
                        if (!alfabet.contains(curr.getA().getValue())) {
                            alfabet.add(curr.getA().getValue());
                        }

                    } else {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("kk");
                        String s = "Bad Rule under number = " + i;

                        alert.setContentText(s);

                        alert.showAndWait();
                        tr = false;
                    }
                }
                if (tr) {
                    aut = new Shop_automation(states, alfabet, states.size(), alfabet.size(), rules.size(), rules_map);
                    frame = new graph.Graph(aut);
                    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(800, 720);
                    frame.setVisible(true);
                    // finite_table.removeTable();
                }
            }
        });
        begin_aut.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (aut != null) {
                    System.out.println("\nq0=" + Sq0.getText());
                    frame.Show_vert(Sq0.getText(), "");
                    aut = (Shop_automation) frame.SaveGraph((Shop_automation) aut);
                    aut.begin_aut(Sq0, Sqn, input_lent);
                }
            }
        });

        q0.setOnEditCommit(

                new EventHandler<TableColumn.CellEditEvent<Rule, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Rule, String> t) {
                        System.out.print(t.getTableView().getFixedCellSize());
                        ((Rule) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setQ0(new State(t.getNewValue(), t.getNewValue(), 0, 0));
                    }
                }
        );
        a.setOnEditCommit(

                new EventHandler<TableColumn.CellEditEvent<Rule, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Rule, String> t) {
                        System.out.print(t.getTableView().getFixedCellSize());
                        ((Rule) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setA(t.getNewValue());
                    }
                }
        );
        z0.setOnEditCommit(

                new EventHandler<TableColumn.CellEditEvent<Rule, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Rule, String> t) {
                        System.out.print(t.getTableView().getFixedCellSize());
                        ((Rule) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setZ(t.getNewValue());
                    }
                }
        );

        qn.setOnEditCommit(

                new EventHandler<TableColumn.CellEditEvent<Rule, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Rule, String> t) {
                        System.out.print(t.getTableView().getFixedCellSize());
                        ((Rule) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setQn(new State(t.getNewValue(), t.getNewValue(), 0, 0));
                    }
                }
        );

        zn.setOnEditCommit(

                new EventHandler<TableColumn.CellEditEvent<Rule, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Rule, String> t) {
                        System.out.print(t.getTableView().getFixedCellSize());
                        ((Rule) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setZn(t.getNewValue());
                    }
                }
        );
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
            aut = new Shop_automation();
            aut.LoadAutomation(file.getAbsolutePath());
        }
    }

    /**
     * Сохраняет файл в файл адресатов, который в настоящее время открыт.
     * Если файл не открыт, то отображается диалог "save as".
     */


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
            aut.SaveToXML(file.getAbsolutePath());
        }
    }

}
