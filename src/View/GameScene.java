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
    private SpriteAnimation wellSpriteAnimation;
    public static GameScene gameScene = new GameScene();
    public static Group root;
    private Stage primaryStage;
    private Scene scene;
    private final int wellX = 620;
    private final int wellY = 110;
    protected static ImageView getImage(String path) {
        try {
            return new ImageView(new Image(new FileInputStream(path)));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private GameScene() {
        root = new Group();
        scene = new Scene(root, 1200, 900);
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



    private void addWellIcon(int x, int y,int level) {
        ImageView image = getImage("./Graphic/Service/Well/" + level + ".png");
        image.setX(x);
        image.setY(y);
        int width = (int) image.getImage().getWidth();
        int height = (int) image.getImage().getHeight();
        wellSpriteAnimation = new SpriteAnimation(image, new Duration(1000), 16, 4, 0, 0, width / 4, height / 4);
        wellSpriteAnimation.interpolate(1);
        root.getChildren().add(image);
        image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            boolean flag = false;

            @Override
            public void handle(MouseEvent event) {
                if (flag)
                    return;
                flag = true;
                System.out.println("Well");
                CommandController.commandController.well();
                new AnimationTimer() {
                    long prv = -1, count = 0;

                    @Override
                    public void handle(long now) {
                        if (now - prv < 5e7) {
                            return;
                        }
                        count++;
                        if (count == 30) {
                            stop();
                            flag = false;
                        }
                        prv = now;
                        wellSpriteAnimation.interpolate(0.1);
                    }
                }.start();
            }
        });
    }
    public void addWellUpgradeButton(int x, int y) {
        ImageView image = getImage("./Graphic/UI/Icons/plus.png");
        image.setX(x);
        image.setY(y);
        root.getChildren().add(image);
        image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            boolean flag = false;

            @Override
            public void handle(MouseEvent event) {
                try {
                    CommandController.commandController.upgrade("well");
                    root.getChildren().remove(wellSpriteAnimation.getImageView());
                    int level = CommandController.commandController.getGame().getCurrentLevel().getMap().getWell().getLevel() + 1;
                    addWellIcon(wellX, wellY, level);

                } catch (Exception e) {
                    System.out.println("cannot upgrade");
                }

            }
        });
    }
    public void addGrass(int x,int y){
        ImageView image = getImage("./Graphic/Grass/grass1.png");
        image.setX(x);
        image.setY(y);
        int width = (int) image.getImage().getWidth();
        int height = (int) image.getImage().getHeight();
        SpriteAnimation SpriteAnimation = new SpriteAnimation(image, new Duration(1000), 16, 4, 0, 0, width / 4, height / 4);
        SpriteAnimation.interpolate(1);
        root.getChildren().add(image);
    }

    public void start() {
        ImageView backImage = getImage("./Graphic/back.png");
        backImage.setX(0);
        backImage.setY(0);
        root.getChildren().add(backImage);
        int W = 105;
        addAnimalIcon("Chicken", 30, 30);
        addAnimalIcon("Cow", 30 + W * 1, 30);
        addAnimalIcon("Sheep", 30 + W * 2, 30);
        addAnimalIcon("Cat", 30 + W * 3, 21);
        addWellIcon(wellX, wellY, 1);
        addWellUpgradeButton(wellX + 15, wellY + 10);
        addGrass(450, 600);
        Label moneyLebal = new Label("Start");
        moneyLebal.relocate(550, 20);
        root.getChildren().add(moneyLebal);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                moneyLebal.setText("Money : " + CommandController.commandController.getGame().getCurrentLevel().getMap().getMoney());
            }
        }.start();

        Rectangle rectangle = new Rectangle(318, 285, 555, 435);
        root.getChildren().add(rectangle);

        MoveAnimal moveAnimal = new MoveAnimal("Sheep", 500, 500, 0, 1, 25, 5, 4);
        moveAnimal.start();

        new AnimationTimer() {
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

        backImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            boolean flag = false;

            @Override
            public void handle(MouseEvent event) {
                int x = (int)event.getX();
                int y = (int) event.getY();
                addGrass(x - 24, y - 24);
            }
        });

        primaryStage.setTitle("Farm Frenzy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
