package sample;

import automat.Automation;
import automat.Mour_automation;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.StreamCorruptedException;

/**
 * Created by Диана on 31.03.2017.
 */
public class Mour_controller {
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField input;
    @FXML
    private TextField output;
    @FXML
    private Button build_but;
    @FXML
    private AnchorPane lambda_pane;
    @FXML
    private Button table_but;
    @FXML
    private TextField n;
    @FXML
    private TextField m;

    private static Stage stage;
    private Parent root;
    public Mour_controller(){

    }
    public Mour_controller(Stage stage,Parent root){
        this.stage = stage;
        this.root = root;
    }
    public void initialize(){

        Jump_Table finite_table = new Jump_Table(table_but,build_but,pane,lambda_pane,n,m);
        finite_table.Create_Table();

    }


}
