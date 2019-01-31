package View;

import Control.CommandController;
import Model.ItemType;
import Model.Vehicle.Helicopter;
import Model.Well;
import Values.Values;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
    public final static int rightBoundery = 790;
    public final static int upBoundery = 250;
    public final static int downBoundery = 590;

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

    private void addAnimalIcon(String name, int x, int y, int cost) {
        ImageView image = getImage("./Graphic/UI/Icons/Products/" + name.toLowerCase() + ".png");
        image.setX(x);
        image.setY(y);
        root.getChildren().add(image);

        SpriteAnimation spriteAnimation = new SpriteAnimation(image, 4, 1);

        spriteAnimation.interpolate(1);

        image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(name);
                CommandController.commandController.buyAnimal(name);

            }
        });

        Text text = new Text(x + 16, y + 51, String.valueOf(cost));
        text.setFont(Font.font(null, FontWeight.BOLD, 10));
        root.getChildren().add(text);
    }

    protected ImageView addIcon(int x, int y, int level, String name) {
        if (name.equals("well")) {
            return addWellIcon(x, y, level);
        }

        ImageView image = getImage("./Graphic/Service/" + name + "/" + Integer.toString(level) + ".png");
        image.setX(x);
        image.setY(y);
        root.getChildren().add(image);
        if(name.equals("Truck")){
            image.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Truck menu");
                    TruckScene.truckScene.setPrimaryStage(primaryStage);
                    TruckScene.truckScene.start();
                }
            });
        }
        if(name.equals("Helicopter")){
            image.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Helicopter menu");
                    HelicopterScene.helicopterScene.setPrimaryStage(primaryStage);
                    HelicopterScene.helicopterScene.start();
                }
            });
        }
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
    private void addChat(){
        ImageView image = getImage("./Graphic/chat.png");
        image.setX(600);
        image.setY(600);
        root.getChildren().add(image);
    }
    private void addGrass(int x, int y) {
        int id = new Random().nextInt(4) + 1;
        ImageView image = getImage("./Graphic/Grass/grass" + id + ".png");
        if (x < leftBoundery || x > rightBoundery)
            return;
        if (y < upBoundery || y > downBoundery)
            return;
        try {
            CommandController.commandController.plant(x - leftBoundery, y - upBoundery, image);
        } catch (Exception e) {
            return;
        }
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
            long prv = -1;
            @Override
            public void handle(long now) {
                if(now - prv < 2e8)
                    return;
                Well well = CommandController.commandController.getGame().getCurrentLevel().getMap().getWell();
                int capacity = well.getCapacity();
                int waterValue = well.getWaterValue();
                rectangle.setY(y + 110 * (capacity - waterValue) / capacity);
                rectangle.setHeight(110 * waterValue / capacity);
                prv = now;
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
                try {
                    CommandController.commandController.getGame().getCurrentLevel().getMap().getWarehouse().add(itemType);
                    root.getChildren().remove(imageView);
                } catch (Exception e) {
                    System.out.println("Cannot add this item to warehouse");
                }

            }
        });
    }

    private void addCoin(int x, int y) {
        ImageView back = getImage("./Graphic/UI/Icons/CoinBackground.png");
        back.setX(x);
        back.setY(y);
        root.getChildren().add(back);

        ImageView coin = getImage("./Graphic/UI/Icons/Coin.png");
        coin.setX(x);
        coin.setY(y);
        root.getChildren().add(coin);
    }

    private void addReturnToMenu (int x, int y) {
        ImageView button = getImage("./Graphic/returnMenu.png");
        button.setX(x);
        button.setY(y);
        root.getChildren().add(button);

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CommandController.commandController.saveGame("gameData");
                MenuScene.menuScene.setPrimaryStage(primaryStage);
                MenuScene.menuScene.start();
            }
        });
    }

    public void addWorkshop(String name, int place, int level) {
        ImageView imageView = getImage("./Graphic/Workshops/" + name + "/0" + (level + 1) + ".png");

        int[] x = {105, 105, 105, 820, 820, 820};
        int[] y = {200, 350, 500, 200, 350, 500};

        imageView.setX(x[place]);
        imageView.setY(y[place]);

        SpriteAnimation spriteAnimation = new SpriteAnimation(imageView, 16, 4);
        spriteAnimation.interpolate(1);


        root.getChildren().add(imageView);

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            boolean flag = false;
            @Override
            public void handle(MouseEvent event) {
                if (flag)
                    return;
                flag = true;
                new AnimationTimer() {
                    long prv = -1, count = 0;

                    @Override
                    public void handle(long now) {
                        if (now - prv < 5e7) {
                            return;
                        }
                        count++;
                        if (count == 60) {
                            stop();
                            flag = false;
                        }
                        prv = now;
                        spriteAnimation.interpolate(1);
                    }
                }.start();
            }
        });

        ImageView upgradeButton = getImage("./Graphic/plus.png");

        if (level != 3) {
            upgradeButton.setX(x[place] + 5);
            upgradeButton.setY(y[place] + 5);
            root.getChildren().add(upgradeButton);
        }

        upgradeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (level == 3) return;

                root.getChildren().remove(imageView);
                root.getChildren().remove(upgradeButton);
                addWorkshop(name, place, level + 1);
            }
        });
    }

    public void start(String level) {
        if(level != null)
            CommandController.commandController.run(level);
        ImageView backImage = getImage("./Graphic/back.png");
        backImage.setX(0);
        backImage.setY(0);
        root.getChildren().add(backImage);
        int W = 60;
        addAnimalIcon("Chicken", 30, 30, Values.CHICKEN_COST);
        addAnimalIcon("Cow", 30 + W * 1, 30, Values.COW_COST);
        addAnimalIcon("Sheep", 30 + W * 2, 30, Values.SHEEP_COST);
        addAnimalIcon("Cat", 30 + W * 3, 30, Values.CAT_COST);
        addAnimalIcon("Dog", 30 + W * 4, 30, Values.DOG_COST);
        addChat();
        addCoin(920, 35);

        addReturnToMenu(1000, 700);

        addIcon(wellX, wellY, 1, "well");

        new UpgradeButton("Depot", 450, 650, null, this,
                addIcon(450, 650, 1, "Depot"));
        new UpgradeButton("Helicopter", 705, 650, null, this,
                addIcon(705, 650, 1, "Helicopter"));

        new UpgradeButton("Truck", 200, 670, null, this,
                addIcon(200, 670, 1, "Truck"));


        addWellUpgradeButton(wellX, wellY + 10);
        addWaterValue(wellX + 130, wellY + 10);

        Text text = new Text(977, 64, "Start");
        text.setFill(Color.YELLOW);
        text.setFont(Font.font(null, FontWeight.BOLD, 18));
        root.getChildren().add(text);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                text.setText("" + CommandController.commandController.getGame().getCurrentLevel().getMap().getMoney());
            }
        }.start();

        addWorkshop("Cake", 0, 0);
        addWorkshop("Spinnery", 1, 0);
        addWorkshop("FlouryCake", 2, 0);
        addWorkshop("DriedEggs", 3, 0);
        addWorkshop("CarnivalDress", 4, 0);
        addWorkshop("Weaving", 5, 0);


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

    public Scene getScene() {
        return scene;
    }
}
