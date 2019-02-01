package View;

import Control.CommandController;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Control.CommandController;
import javafx.scene.control.TextField;
import Model.ItemType;
import Model.Level;
import Model.Warehouse;
import Model.Well;
import Network.Client;
import Values.Values;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
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

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

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
    private Client client;

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
        if (name.equals("Truck")) {
            image.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Truck menu");
                    TruckScene.truckScene.setPrimaryStage(primaryStage);
                    TruckScene.truckScene.start();
                }
            });
        }
        if (name.equals("Helicopter")) {
            image.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Helicopter menu");
                    HelicopterScene.helicopterScene.setPrimaryStage(primaryStage);
                    HelicopterScene.helicopterScene.start();
                }
            });
        }

        if (name.equals("Depot") && client != null) {
            image.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Helicopter menu");
                    BazaarScene.bazarScene.setPrimaryStage(primaryStage);
                    BazaarScene.bazarScene.start(client);
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
                if (now - prv < 2e8)
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

        final int positionX = x, positionY = y;

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    CommandController.commandController.getGame().getCurrentLevel().getMap().getWarehouse().add(itemType);
                    new AnimationTimer() {
                        long prv = -1, x = positionX, y = positionY;

                        @Override
                        public void handle(long now) {
                            if (now - prv < 2e8)
                                return;
                            x += (500.0 - x) / 10;
                            y += (650.0 - y) / 10;
                            imageView.relocate(x, y);
                            if (Math.abs(x - 500) < 20 && Math.abs(y - 650) < 20) {
                                root.getChildren().remove(imageView);
                                stop();
                            }
                        }
                    }.start();
                } catch (Exception e) {
                    System.out.println("Cannot add this item to warehouse");
                }

            }
        });

        new AnimationTimer() {
            long prv = -1;

            @Override
            public void handle(long now) {
                if (now - prv < 5e9 && prv != -1)
                    return;
                if (prv == -1) {
                    prv = now;
                    return;
                }
                if (!root.getChildren().contains(imageView)) {
                    stop();
                    return;
                }
                new AnimationTimer() {
                    long prv = -1, cnt = 0;

                    @Override
                    public void handle(long now) {
                        if (now - prv < 8e8)
                            return;
                        cnt++;
                        if (cnt == 25) stop();
                        if (cnt % 2 == 1) {
                            root.getChildren().remove(imageView);
                            return;
                        }

                        root.getChildren().add(imageView);
                    }
                }.start();
                stop();
            }
        }.start();
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

    private void addReturnToMenu(int x, int y) {
        ImageView button = getImage("./Graphic/returnMenu.png");
        button.setX(x);
        button.setY(y);
        root.getChildren().add(button);

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CommandController.commandController.saveGame("gameData");
                if (client == null) {
                    MenuScene.menuScene.setPrimaryStage(primaryStage);
                    MenuScene.menuScene.start();
                }
                else {
                    ClientMenuScene.clientMenuScene.setPrimaryStage(primaryStage);
                    ClientMenuScene.clientMenuScene.start(client);
                }
            }
        });
    }

    public void addWorkshop(String name, int place, int level) {
        ImageView imageView = getImage("./Graphic/Workshops/" + name + "/0" + (level + 1) + ".png");

        String json = CommandController.commandController.read("./Data/Workshops/" + name + ".json");
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        JsonArray inputs = jsonObject.get("inputs").getAsJsonArray();
        ArrayList<ItemType> itemTypes = new ArrayList<>();
        for (JsonElement jsonElement : inputs) {
            itemTypes.add(ItemType.getItemType(jsonElement.getAsString()));
        }

        ItemType output = ItemType.getItemType(jsonObject.get("output").getAsString());

        final int upgrade_cost = jsonObject.get("upgrade_cost").getAsInt() + level * jsonObject.get("cost_increase_step").getAsInt();

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
                Warehouse warehouse = CommandController.commandController.getGame().getCurrentLevel().getMap().getWarehouse();
                for (ItemType itemType : itemTypes) {
                    if (!warehouse.getItemTypes().contains(itemType))
                        return;
                }
                for (ItemType itemType : itemTypes) {
                    warehouse.getItemTypes().remove(itemType);

                    ImageView imageView = getImage("./Graphic/Products/" + itemType.getName() + ".png");
                    imageView.setX(500);
                    imageView.setY(650);
                    root.getChildren().add(imageView);

                    new AnimationTimer() {
                        long prv = -1, xx = 500, yy = 650;

                        @Override
                        public void handle(long now) {
                            if (now - prv < 5e7)
                                return;
                            xx -= (0.0 + xx - x[place] - 30) / 10;
                            yy -= (0.0 + yy - y[place] - 30) / 10;
                            imageView.relocate(xx, yy);
                            if (Math.abs(xx - x[place] - 30) < 20 && Math.abs(yy - y[place] - 30) < 20) {
                                root.getChildren().remove(imageView);
                                stop();
                            }
                        }
                    }.start();
                }
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
                            addItemType(output, x[place] + 100, y[place] + 100);
                            stop();
                            flag = false;
                        }
                        prv = now;
                        spriteAnimation.interpolate(1);
                    }
                }.start();

                Rectangle rectangle = new Rectangle(x[place] + 100, y[place], 7, 100);
                rectangle.setFill(Color.BROWN);
                root.getChildren().add(rectangle);
                new AnimationTimer() {
                    long prv = -1;

                    @Override
                    public void handle(long now) {
                        if (now - prv < 6e6)
                            return;
                        prv = now;
                        rectangle.setHeight(rectangle.getHeight() - 1);
                        rectangle.setY(rectangle.getY() + 1);
                        if (rectangle.getHeight() == 0)
                            stop();
                    }
                }.start();
            }
        });

        ImageView outputImage = getImage("./Graphic/Products/" + output.getName() + ".png");
        outputImage.relocate(600, 20);

        int sx = 400;

        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (ItemType itemType : itemTypes) {
            ImageView imageView1 = getImage("./Graphic/Products/" + itemType.getName() + ".png");
            imageView1.relocate(sx, 20);
            sx += 50;
            imageViews.add(imageView1);
        }

        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().add(outputImage);
                for (ImageView view : imageViews) {
                    root.getChildren().add(view);
                }
            }
        });

        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().remove(outputImage);
                for (ImageView view : imageViews) {
                    root.getChildren().remove(view);
                }
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
                if (upgrade_cost > CommandController.commandController.getGame().getCurrentLevel().getMap().getMoney())
                    return;
                CommandController.commandController.getGame().getCurrentLevel().getMap().addMoney(-upgrade_cost);

                root.getChildren().remove(imageView);
                root.getChildren().remove(upgradeButton);
                addWorkshop(name, place, level + 1);
            }
        });
    }
    public String read(String path) {
        System.out.println(path);
        String ans = "";
        try {
            FileReader inputStream = new FileReader(path);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                ans = ans + scanner.nextLine();
            }
            inputStream.close();
        } catch (IOException e) {
            System.out.println("File not found.");
        }
        return ans;
    }
    public String readJson(String mapName){
        String json = read("./Data/Levels/" + mapName + ".json");
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        int levelNumber = Integer.valueOf(mapName.substring(5));
        JsonArray jsonGoals = jsonObject.get("goals").getAsJsonArray();
        HashMap<ItemType, Integer> goals = new HashMap<>();
        HashMap<String, Integer> goalAnimals = new HashMap<>();
        String answer = "";
        for (JsonElement element : jsonGoals) {
            String name = element.getAsJsonObject().get("item").getAsString();
            ItemType item = ItemType.getItemType(name);
            int count = element.getAsJsonObject().get("count").getAsInt();
            answer = answer + name + "-> " + count + "\n";

            if (item != null)
                goals.put(item, count);
            else
                goalAnimals.put(name, count);
        }
        return answer;
    }
    public void start(String level, Client client) {
        String goal = readJson(level);
        if (level != null)
            CommandController.commandController.run(level);
        this.client = client;
        ImageView backImage = getImage("./Graphic/back.png");
        backImage.setX(0);
        backImage.setY(0);
        root.getChildren().add(backImage);
        ImageView clock = getImage("./Graphic/clock.png");
        clock.setX(820);
        clock.setY(20);
        root.getChildren().add(clock);
        Countdown countdown = new Countdown(root, 750, 20, primaryStage);

        ImageView hint = getImage("./Graphic/lamp.png");
        root.getChildren().add(hint);
        hint.setX(720);
        hint.setY(20);
        Label hints = new Label("");
        hints.relocate(620, 10);
        hints.setStyle("-fx-font: normal bold 20px 'serif'");
        hint.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hints.setText(goal);
                root.getChildren().add(hints);
            }
        });
        hint.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().remove(hints);
            }
        });




        int W = 60;
        addAnimalIcon("Chicken", 30, 30, Values.CHICKEN_COST);
        addAnimalIcon("Cow", 30 + W * 1, 30, Values.COW_COST);
        addAnimalIcon("Sheep", 30 + W * 2, 30, Values.SHEEP_COST);
        addAnimalIcon("Cat", 30 + W * 3, 30, Values.CAT_COST);
        addAnimalIcon("Dog", 30 + W * 4, 30, Values.DOG_COST);
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
