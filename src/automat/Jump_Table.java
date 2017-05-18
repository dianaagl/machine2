package automat;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Диана on 28.04.2017.
 */

public class Jump_Table {
    private TextField N;
    private TextField M;
    private Button table_but;
    private Button build_but;
    private AnchorPane pane;
    private AnchorPane lambda_pane;

    private TableView jump_table;
    private TableView output_table;
    public Jump_Table(Button table_but, Button build_but, AnchorPane pane, AnchorPane lambda_pane, TextField n, TextField m) {
        this.table_but = table_but;
        this.build_but = build_but;
        this.pane = pane;
        this.lambda_pane = lambda_pane;
        this.N = n;
        this.M = m;
    }

    public Button getTable_but() {
        return table_but;
    }

    public Button getBuild_but() {
        return build_but;
    }

    public void removeTable() {
        pane.getChildren().remove(jump_table);
    }
    public void Create_Table() {
        table_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int n = Integer.parseInt(N.getText());
                int m = Integer.parseInt(M.getText());

                jump_table = new TableView();
                jump_table.setPrefHeight(120);

                TableColumn<StringProperty[], String> columns[] = new TableColumn[m + 1];

                //TableColumn<StringProperty[],String> sigma  =new TableColumn<StringProperty[], String>("sigma");

                //sigma.setEditable(true);

                //jump_table.getColumns().add(0,sigma);

                for (int i = 0; i < m + 1; i++) {
                    final int t = i ;
                    columns[i ] //
                            = new TableColumn<StringProperty[], String>(String.valueOf(i ));
                    columns[i].setCellValueFactory(cellData -> cellData.getValue()[t]);
                    columns[i ].setEditable(true);


                    jump_table.getColumns().add(columns[i]);


                }


                columns[0].setText("таблица переходов");

                jump_table.setEditable(true);
                jump_table.setItems(getList(n, m));
                pane.getChildren().add(jump_table);


                for (int i = 0; i < m + 1; i++) {
                    final int ti = i;
                    columns[ti ].setCellFactory(TextFieldTableCell.forTableColumn());
                    columns[ti  ].setOnEditCommit(

                            new EventHandler<TableColumn.CellEditEvent<StringProperty[], String>>() {
                                @Override
                                public void handle(TableColumn.CellEditEvent<StringProperty[], String> t) {
                                    System.out.print(t.getTableView().getFixedCellSize());
                                    ((StringProperty[]) t.getTableView().getItems().get(
                                            t.getTablePosition().getRow())
                                    )[ti] = new SimpleStringProperty(t.getNewValue());
                                }
                            }
                    );
                }
                /*sigma.setCellFactory(TextFieldTableCell.forTableColumn());
                sigma.setOnEditCommit(

                        new EventHandler<TableColumn.CellEditEvent<StringProperty[], String>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<StringProperty[], String> t) {
                                System.out.print(t.getTableView().getFixedCellSize());
                                 t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())[0]
                                = new SimpleStringProperty(t.getNewValue());
                            }
                        }
                );
                */

            }

        });

    }

    public String[] getLambda() {

        int m = Integer.parseInt(M.getText());
        String[] lambda = new String[m];
        StringProperty[][] data = new StringProperty[1][m];
        ObservableList<StringProperty[]> list = FXCollections.observableArrayList();
        list = output_table.getItems();

        int temp;
        for (int i = 1; i < m + 1; i++) {
            ;
            lambda[i - 1] = list.get(0)[i].getValue();


        }
        return lambda;
    }

    public String[][] getOutput() {

        int n = Integer.parseInt(N.getText());
        int m = Integer.parseInt(M.getText());
        String[][] lambda = new String[n][m];

        StringProperty[][] data = new StringProperty[n][m + 1];
        ObservableList<StringProperty[]> list = FXCollections.observableArrayList();
        list = output_table.getItems();

        String temp;
        for (int j = 0; j < list.size(); j++) {
            data[j] = list.get(j);
            for (int i = 1; i < m + 1; i++) {
                try {
                    if (data[j][i] != null) {
                        temp = data[j][i].getValue();
                        lambda[j][i - 1] = temp;
                    }
                } catch (NumberFormatException e) {

                }


            }
        }
        return lambda;
    }
    public int [][] getStates(){
        int n = Integer.parseInt(N.getText());
        int m = Integer.parseInt(M.getText());
        int [][]states = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                states[i][j] = -1;
            }
        }
        StringProperty[][] data = new StringProperty[n][m+1];
        ObservableList<StringProperty[]> list = FXCollections.observableArrayList();
        list = jump_table.getItems();

        int temp;
        for(int i = 0;i < n;i++){
            data[i] = list.get(i);
            for(int j = 1;j < m+1;j++) {

                try{
                    if(data[i][j] != null) {
                        temp = Integer.parseInt(data[i][j].getValue());
                        states[i][j - 1] = temp - 1;
                    }
                }
                catch (NumberFormatException e) {

                }
            }

        }
        return states;
    }
    public String [] get_Alphabet(){
        int n = Integer.parseInt(N.getText());
        int m = Integer.parseInt(M.getText());
        StringProperty[][] data = new StringProperty[n][m];
        ObservableList<StringProperty[]> list = FXCollections.observableArrayList();
        list = jump_table.getItems();
        String [] alph = new String[n];

        for(int j = 0;j < n;j++){
            data[j] = list.get(j);
                alph[j] = data[j][0].getValue();

        }



        return alph;
    }
    public void LambdaPaneConst() {
        table_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int n = Integer.parseInt(N.getText());
                int m = Integer.parseInt(M.getText());
                output_table = new TableView();


                output_table.setEditable(true);
                TableColumn<StringProperty[], String> lambda[] = new TableColumn[m + 1];

                for (int i = 0; i < m + 1; i++) {
                    final int t = i;

                    lambda[i] //
                            = new TableColumn<StringProperty[], String>(String.valueOf(i));

                    lambda[i].setCellValueFactory(cellData -> cellData.getValue()[t]);
                    lambda[i].setEditable(true);
                    output_table.getColumns().add(lambda[i]);


                }
                lambda[0].setText("lambda");
                for (int i = 0; i < m + 1; i++) {
                    final int ti = i;
                    lambda[ti].setCellFactory(TextFieldTableCell.forTableColumn());
                    lambda[ti].setOnEditCommit(

                            new EventHandler<TableColumn.CellEditEvent<StringProperty[], String>>() {
                                @Override
                                public void handle(TableColumn.CellEditEvent<StringProperty[], String> t) {
                                    System.out.print(t.getTableView().getFixedCellSize());
                                    ((StringProperty[]) t.getTableView().getItems().get(
                                            t.getTablePosition().getRow())
                                    )[ti] = new SimpleStringProperty(t.getNewValue());
                                }
                            }
                    );
                }

                // output_table.setItems(getList(output_table.getItems(),1,m));
                output_table.setItems(getList(m));

                lambda_pane.getChildren().add(output_table);
            }
        });
    }

    public void LambdaMilliePaneConst() {
        table_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int n = Integer.parseInt(N.getText());
                int m = Integer.parseInt(M.getText());
                output_table = new TableView();
                output_table.setPrefHeight(120);

                output_table.setEditable(true);
                TableColumn<StringProperty[], String> lambda[] = new TableColumn[m + 1];

                for (int i = 0; i < m + 1; i++) {
                    final int t = i;

                    lambda[i] //
                            = new TableColumn<StringProperty[], String>(String.valueOf(i));

                    lambda[i].setCellValueFactory(cellData -> cellData.getValue()[t]);
                    lambda[i].setEditable(true);
                    output_table.getColumns().add(lambda[i]);


                }
                lambda[0].setText("lambda");
                for (int i = 0; i < m + 1; i++) {
                    final int ti = i;
                    lambda[ti].setCellFactory(TextFieldTableCell.forTableColumn());
                    lambda[ti].setOnEditCommit(

                            new EventHandler<TableColumn.CellEditEvent<StringProperty[], String>>() {
                                @Override
                                public void handle(TableColumn.CellEditEvent<StringProperty[], String> t) {
                                    System.out.print(t.getTableView().getFixedCellSize());
                                    ((StringProperty[]) t.getTableView().getItems().get(
                                            t.getTablePosition().getRow())
                                    )[ti] = new SimpleStringProperty(t.getNewValue());
                                }
                            }
                    );
                }

                // output_table.setItems(getList(output_table.getItems(),1,m));
                output_table.setItems(getList(n, m));

                lambda_pane.getChildren().add(output_table);
            }
        });
    }

    private ObservableList<StringProperty[]> getList(int n, int m) {
        ObservableList<StringProperty[]> l = FXCollections.observableArrayList();
        StringProperty[][] data = new StringProperty[n][m+1];
        //list = FXCollections.observableArrayList();
        //ObservableList<StringProperty[] > l = FXCollections.observableArrayList();
        for (int i = 0; i < n; i++) {
            data[i][0] = new SimpleStringProperty("w" + i);
            l.add(data[i]);
        }


        return l;
    }

    private ObservableList<StringProperty[]> getList(int m) {
        ObservableList<StringProperty[]> l = FXCollections.observableArrayList();
        StringProperty[][] data = new StringProperty[1][m + 1];
        //list = FXCollections.observableArrayList();
        //ObservableList<StringProperty[] > l = FXCollections.observableArrayList();
        for (int i = 1; i < m + 1; i++) {
            data[0][i] = new SimpleStringProperty("w" + i);

        }
        data[0][0] = new SimpleStringProperty("lambda");
        l.add(data[0]);


        return l;
    }

    private void L() {//ObservableList<StringProperty[]> getList() {
/*
        StringProperty[][] data = new StringProperty[n][m];
        ObservableList<StringProperty[]> list = FXCollections.observableArrayList();
        for (int j = 0; j < n; j++) {

            for (int i = 1; i < m; i++) {
                data[j][i] = new SimpleStringProperty(" ");
            }
            data[j][0] = new SimpleStringProperty("w" + j);

            list.add(data[j]);

        }


        return list;
    }
    */
    }
}
