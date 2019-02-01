package View;

import Control.CommandController;
import Control.Game;
import Model.ItemType;
import Values.ItemsCosts;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.Random;


public class View extends Application {
    public Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        for (String name : ItemsCosts.names) {
            ItemType.addItemType(
                    new ItemType(name, ItemsCosts.getDepotSize(name), ItemsCosts.getSaleCost(name), ItemsCosts.getBuyCost(name), 10)
            );
        }
        this.primaryStage = primaryStage;
        MenuScene.menuScene.setPrimaryStage(primaryStage);
        MenuScene.menuScene.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
