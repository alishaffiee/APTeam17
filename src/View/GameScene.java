package View;

import Control.CommandController;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class GameScene {
    public static GameScene gameScene = new GameScene();
    private Group root;
    private Stage primaryStage;
    private Scene scene;

    protected static ImageView getImage(String path) {
        try {
            return new ImageView(new Image(new FileInputStream(path)));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private GameScene() {
        root = new Group();
        scene = new Scene(root, 800, 600);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void addAnimalIcon(String name, int x, int y) {
        ImageView image = getImage("./Graphic/UI/Icons/Products/" + name.toLowerCase() + ".png");
        image.setX(x);
        image.setY(y);
        root.getChildren().add(image);

        image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(name);
                CommandController.commandController.buyAnimal(name);

            }
        });
    }

    public void start() {
        ImageView backImage = getImage("./Graphic/back.png");
        backImage.setX(0);
        backImage.setY(0);
        root.getChildren().add(backImage);

        addAnimalIcon("Chicken", 20, 20);
        addAnimalIcon("Cow", 70, 20);
        addAnimalIcon("Sheep", 120, 20);
        addAnimalIcon("Cat", 170, 14);

        Label moneyLebal = new Label("Start");
        moneyLebal.relocate(550, 20);
        root.getChildren().add(moneyLebal);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                moneyLebal.setText("Money : " + CommandController.commandController.getGame().getCurrentLevel().getMap().getMoney());
            }
        }.start();


        MoveAnimal moveAnimal = new MoveAnimal(root, "Cat", 100, 100, 1,
                2, 24, 6, 4);

        new AnimationTimer(){
            long prv = -1;
            @Override
            public void handle(long now) {
                if (now - prv < 2e9) {
                 //   System.out.println(1);
                    return;
                }
                prv = now;
                moveAnimal.setDirection((moveAnimal.getDirection() + 1) % 4);

            }
        }.start();

        primaryStage.setTitle("Farm Frenzy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Group getRoot() {
        return root;
    }
}
