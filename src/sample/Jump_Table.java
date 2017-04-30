package sample;

import automat.Mour_automation;
import javafx.beans.property.IntegerProperty;
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
import javafx.scene.text.Text;

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
    public Button getTable_but() {
        return table_but;
    }

    public Button getBuild_but() {
        return build_but;
    }

    public Jump_Table(Button table_but, Button build_but, AnchorPane pane, AnchorPane lambda_pane, TextField n, TextField m) {
        this.table_but = table_but;
        this.build_but = build_but;
        this.pane = pane;
        this.lambda_pane = lambda_pane;
        this.N = n;
        this.M = m;
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


                    jump_table.getColumns().add(i,columns[i]);


                }


                columns[0].setText("таблица переходов");

                jump_table.setEditable(true);
                jump_table.setItems(getList(jump_table.getItems(),n, m));
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
    public int [][] getStates(){
        int n = Integer.parseInt(N.getText());
        int m = Integer.parseInt(M.getText());
        int [][]states = new int[n][m];
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
                        states[i][j-1] = temp;
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
        int n = Integer.parseInt(N.getText());
        int m = Integer.parseInt(M.getText());
        TableView output_table = new TableView();
        output_table.setPrefHeight(50);
        TableColumn<StringProperty[], String> lambda[] = new TableColumn[n + 1];
        lambda[0] //
                = new TableColumn<StringProperty[], String>("lambda");
        output_table.getColumns().add(lambda[0]);
        for (int i = 0; i < n; i++) {
            final int t = i;

            lambda[i + 1] //
                    = new TableColumn<StringProperty[], String>(String.valueOf(i));
            lambda[i].setCellValueFactory(cellData -> cellData.getValue()[t]);
            lambda[i + 1].setEditable(true);


            output_table.getColumns().add(lambda[i + 1]);

        }


        output_table.setItems(getList(output_table.getItems(),1, m));

        lambda_pane.getChildren().add(output_table);
    }

    private ObservableList<StringProperty[]> getList(ObservableList<StringProperty[]> list,int n, int m) {

        StringProperty[][] data = new StringProperty[n][m+1];
        list = FXCollections.observableArrayList();
        ObservableList<StringProperty[] > l = FXCollections.observableArrayList();

        for (int j = 0; j < n; j++) {


            data[j][0] = new SimpleStringProperty("w" + j);
            list.add(data[j]);


        }



        return list;
    }
}
