package Main;

import automat.Finite.Finite_Aut_Controller;
import automat.Millie.Millie_controller;
import automat.Moore.Mour_controller;
import automat.ShopAut.Shop_controller;
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
    private static Stage stage;
    @FXML
    private Button mour_but;
    @FXML
    private Button finite_but;
    @FXML
    private Button millie_but;
    @FXML
    private Button shop_but;

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


                    Parent root = FXMLLoader.load(getClass().getResource("../automat/Moore/mour_automat.fxml"));
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


                    Parent root = FXMLLoader.load(getClass().getResource("../automat/Finite/finite_automat.fxml"));
                   Finite_Aut_Controller m = new Finite_Aut_Controller(stage,root);
                    stage.setScene(new Scene(root));
                    stage.show();

                }
                catch(IOException e){
                    System.out.print("конечный автомат");
                }

            }

        });
        millie_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                try {


                    Parent root = FXMLLoader.load(getClass().getResource("../automat/Millie/millie_automat.fxml"));
                    Millie_controller m = new Millie_controller(stage, root);
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    System.out.print("конечный автомат");
                }
            }
        });
        shop_but.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                try {


                    Parent root = FXMLLoader.load(getClass().getResource("../automat/ShopAut/shop_automat.fxml"));
                    Shop_controller m = new Shop_controller(stage, root);
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    System.out.print(e.toString());
                }
            }
        });
    }
}
