package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main_Controller {
    @FXML
    private Button mour_but;
    @FXML
    private Button finite_but;
    private static Stage stage;

    public Main_Controller(final Stage stage){
        this.stage = stage;
    }
    public Main_Controller(){

    }
    public void initialize(){
        mour_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {


                try{


                    Parent root = FXMLLoader.load(getClass().getResource("mour_automat.fxml"));
                    Mour_controller m = new Mour_controller(stage,root);
                    stage.setScene(new Scene(root));
                    stage.show();

                }
                catch(IOException e){
                    System.out.print("Автомат Мура");
                }

            }

        });
        finite_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {


                try{


                    Parent root = FXMLLoader.load(getClass().getResource("finite_automat.fxml"));
                   Finite_Aut_Controller m = new Finite_Aut_Controller(stage,root);
                    stage.setScene(new Scene(root));
                    stage.show();

                }
                catch(IOException e){
                    System.out.print("конечный автомат");
                }

            }

        });
    }
}
