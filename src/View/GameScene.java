package View;

import Control.CommandController;
import Model.ItemType;
import Model.Well;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private final int wellX = 570;
    private final int wellY = 80;
    public final static int leftBoundery = 280;
    public final static int rightBoundery = 805;
    public final static int upBoundery = 250;
    public final static int downBoundery = 610;

    protected static ImageView getImage(String path) {
        try {
            return new ImageView(new Image(new FileInputStream(path)));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private GameScene() {
        root = new Group();
        scene = new Scene(root, 1100, 825);
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

    protected ImageView addIcon(int x, int y, int level, String name) {
        if (name.equals("well")) {
            return addWellIcon(x, y, level);
        }
        ImageView image = getImage("./Graphic/Service/" + name + "/" + Integer.toString(level) + ".png");
        image.setX(x);
        image.setY(y);
        root.getChildren().add(image);
        return image;

    }

    protected ImageView addWellIcon(int x, int y, int level) {
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
                            CommandController.commandController.getGame().getCurrentLevel().getMap().getWell().fill();
                            stop();
                            flag = false;
                        }
                        prv = now;
                        wellSpriteAnimation.interpolate(1);
                    }
                }.start();
            }
        });
        return image;
    }

    private void addWellUpgradeButton(int x, int y) {
        ImageView image = getImage("./Graphic/plus.png");
        image.setX(x);
        image.setY(y);
        root.getChildren().add(image);
        image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    CommandController.commandController.upgrade("well");
                    root.getChildren().remove(wellSpriteAnimation.getImageView());
                    int level = CommandController.commandController.getGame().getCurrentLevel().getMap().getWell().getLevel() + 1;
                    addIcon(wellX, wellY, level, "well");
                    //   addWellIcon(wellX, wellY, level);

                } catch (Exception e) {
                    System.out.println("cannot upgrade");
                }

            }
        });
    }

    private void addGrass(int x, int y) {
        if (x < leftBoundery || x > rightBoundery)
            return;
        if (y < upBoundery || y > downBoundery)
            return;
        try {
            CommandController.commandController.plant(x - leftBoundery, y - upBoundery);
        } catch (Exception e) {
            return;
        }
        int id = new Random().nextInt(4) + 1;
        ImageView image = getImage("./Graphic/Grass/grass" + id + ".png");
        image.setX(x);
        image.setY(y);
        int width = (int) image.getImage().getWidth();
        int height = (int) image.getImage().getHeight();
        SpriteAnimation spriteAnimation = new SpriteAnimation(image, new Duration(1000), 16, 4, 0, 0, width / 4, height / 4);
        spriteAnimation.interpolate(1);
        root.getChildren().add(image);
        new AnimationTimer() {
            long prv = -1, count = 0;

            @Override
            public void handle(long now) {
                if (now - prv < 5e7) {
                    //   System.out.println(1);
                    return;
                }
                count++;
                if (count == 13)
                    stop();
                prv = now;
                spriteAnimation.interpolate(1);

            }
        }.start();
    }

    private void addWaterValue(int x, int y) {
        Rectangle rectangle = new Rectangle(x, y, 7, 110);
        rectangle.setFill(Color.BLUE);
        root.getChildren().add(rectangle);
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                Well well = CommandController.commandController.getGame().getCurrentLevel().getMap().getWell();
                int capacity = well.getCapacity();
                int waterValue = well.getWaterValue();
                rectangle.setY(y + 110 * (capacity - waterValue) / capacity);
                rectangle.setHeight(110 * waterValue / capacity);

            }
        }.start();
    }

    public void addItemType(ItemType itemType, int x, int y) {
        ImageView imageView = getImage("./Graphic/Products/" + itemType.getName() + ".png");
        imageView.setX(x);
        imageView.setY(y);
        root.getChildren().add(imageView);

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().remove(imageView);
            }
        });
    }

    private void addCoin(int x, int y) {
        ImageView imageView = getImage("./Graphic/UI/Icons/Coin.png");
        imageView.setX(x);
        imageView.setY(y);
        root.getChildren().add(imageView);
    }

    public void addWorkshop(String name, int place, int level) {
        ImageView imageView = getImage("./Graphic/Workshops/" + name + "/0" + (level + 1) + ".png");

        int[] x = {130, 130, 130, 820, 820, 820};
        int[] y = {200, 350, 500, 200, 350, 500};

        imageView.setX(x[place]);
        imageView.setY(y[place]);

        SpriteAnimation spriteAnimation = new SpriteAnimation(imageView, 16, 4);
        spriteAnimation.interpolate(1);

        root.getChildren().add(imageView);
    }

    public void start() {
        ImageView backImage = getImage("./Graphic/back.png");
        backImage.setX(0);
        backImage.setY(0);
        root.getChildren().add(backImage);
        int W = 80;
        addAnimalIcon("Chicken", 30, 30);
        addAnimalIcon("Cow", 30 + W * 1, 30);
        addAnimalIcon("Sheep", 30 + W * 2, 30);
        addAnimalIcon("Cat", 30 + W * 3, 30);
        addAnimalIcon("Dog", 30 + W * 4, 30);

        addCoin(735, 13);

        addIcon(wellX, wellY, 1, "well");

        new UpgradeButton("Depot", 450, 650, null, this,
                addIcon(450, 650, 1, "Depot"));
        new UpgradeButton("Helicopter", 750, 630, null, this,
                addIcon(750, 630, 1, "Helicopter"));
        new UpgradeButton("Truck", 200, 650, null, this,
                addIcon(200, 650, 1, "Truck"));

        addWellUpgradeButton(wellX, wellY + 10);
        addWaterValue(wellX + 130, wellY + 10);

        Label moneyLebal = new Label("Start");
        moneyLebal.relocate(790, 28);
        root.getChildren().add(moneyLebal);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                moneyLebal.setText("" + CommandController.commandController.getGame().getCurrentLevel().getMap().getMoney());
            }
        }.start();

        addWorkshop("Cake", 0, 0);
        addWorkshop("Cake", 1, 1);
        addWorkshop("Cake", 2, 2);
        addWorkshop("Cake", 3, 3);
        addWorkshop("Weaving", 4, 0);
        addWorkshop("Weaving", 5, 1);


        /*
        Rectangle rectangle = new Rectangle(leftBoundery, upBoundery,
                rightBoundery - leftBoundery, downBoundery - upBoundery);
        root.getChildren().add(rectangle);
        */

        backImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                addGrass(x - 24, y - 24);
            }
        });

        new AnimationTimer() {
            long prv = -1;

            @Override
            public void handle(long now) {
                if (now - prv < 2e7) {
                    //   System.out.println(1);
                    return;
                }
                prv = now;
                CommandController.commandController.nextTurn(1);
            }
        }.start();

        primaryStage.setTitle("Farm Frenzy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
