package View;

import Control.CommandController;
import Control.Game;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;


public class View extends Application {
    public Stage primaryStage;

    private void debug() {
        CommandController.commandController.run("Level0");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        debug();
      //  GameScene.gameScene.setPrimaryStage(primaryStage);
        MenuScene.menuScene.setPrimaryStage(primaryStage);
        MenuScene.menuScene.start();
     //   GameScene.gameScene.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
