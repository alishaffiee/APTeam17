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

    private void debug() {
        CommandController.commandController.run("Level0");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        debug();
        MenuScene.menuScene.setPrimaryStage(primaryStage);
        MenuScene.menuScene.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
