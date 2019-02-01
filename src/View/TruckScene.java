package View;

import Control.CommandController;
import Model.Item;
import Model.ItemType;
import Model.Vehicle.Helicopter;
import Model.Vehicle.Truck;
import Model.Warehouse;
import Values.ItemsCosts;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static View.GameScene.getImage;

public class TruckScene {
    public static TruckScene truckScene = new TruckScene();
    private Text cost = new Text("0");
    private int value = 0;

    public static Group root;
    private Stage primaryStage;
    private Scene scene;

    private TruckScene() {
        root = new Group();
        scene = new Scene(root, 1100, 825);
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

        cost.setStyle("-fx-font: 24 arial;");
        cost.setX(x + 60);
        cost.setY(y + 32);
        root.getChildren().add(cost);
    }

    private void addValue(int x) {
        value += x;
        cost.setText("" + value);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start() {
        ImageView background = GameScene.getImage("./Graphic/truckback.jpg");
        root.getChildren().add(background);

        addCoin(470, 40);

        ImageView back = getImage("./Graphic/backbutton.png");
        back.setX(1000);
        back.setY(20);
        root.getChildren().add(back);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Back Truck menu");
                root.getChildren().clear();
                primaryStage.setScene(GameScene.gameScene.getScene());
            }
        });

        Warehouse warehouse = CommandController.commandController.getGame().getCurrentLevel().getMap().getWarehouse();
        ArrayList<ItemType> items = warehouse.getItemTypes(), itemTypes = new ArrayList<>();
        List<String> products = ItemsCosts.names;

        primaryStage.setTitle("Truck");
        primaryStage.setScene(scene);
        primaryStage.show();

        int dx = 185;
        int dy = 110;
        int startX = 100;
        int startY = 100, cur = 0;

        for (String product : products) {
            int cnt = 0;
            for (ItemType item : items) {
                if(item.getName().equals(product))
                    cnt++;
            }

            boolean bad = false;

                try {
                    ImageView tryit = GameScene.getImage("./Graphic/Products/" + product + ".png");
                    if(tryit == null)
                        bad = true;
                }
                catch (Exception e){
                    bad = true;
                }

            if(bad == false){
                int i = cur / 6;
                int j = cur % 6;
                cur++;
                ImageView sell = GameScene.getImage("./Graphic/Products/" + product + ".png");
                int x = startX + dx * i;
                int y = startY + dy * j;
                sell.setX(x);
                sell.setY(y);
                root.getChildren().add(sell);
                Text text = new Text(x + 70, y + 32,
                        String.valueOf(cnt) + "(0)" + "\n" + ItemType.getItemType(product).getSellCost());

                text.setStyle("-fx-font: 18 arial;");

                ImageView sellButton = GameScene.getImage("./Graphic/sell.png");
                sellButton.setX(x + 140);

                sellButton.setY(y + 17);

                final int CNT = cnt;

                sellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    int num = 0;
                    @Override
                    public void handle(MouseEvent event) {
                        if(num == CNT) return;
                        itemTypes.add(ItemType.getItemType(product));
                        num++;
                        text.setText(String.valueOf(CNT) + "(" + num + ")" + "\n" + ItemType.getItemType(product).getSellCost());
                        addValue(ItemType.getItemType(product).getSellCost());
                    }
                });

                root.getChildren().add(text);
                root.getChildren().add(sellButton);

            }
        }

        ImageView sendButton = GameScene.getImage("./Graphic/send.png");
        sendButton.setX(950);
        sendButton.setY(630);
        root.getChildren().add(sendButton);

        Truck truck = CommandController.commandController.getGame().getCurrentLevel().getMap().getTruck();

        sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    truck.go(itemTypes);
                } catch (Exception e) {
                    System.out.println("cannot send truck.");
                    System.out.println(e.getMessage());
                }
                itemTypes.clear();
                addValue(-value);
                root.getChildren().clear();
                primaryStage.setScene(GameScene.gameScene.getScene());
                Group group = GameScene.root;
                int level = truck.getLevel() + 1;
                ImageView imageView = GameScene.getImage("./Graphic/UI/Truck/" + level + ".png");
                imageView.setX(1000);
                imageView.setY(120);
                imageView.setScaleX(2);
                imageView.setScaleY(2);
                SpriteAnimation spriteAnimation = new SpriteAnimation(imageView, 2, 2);
                spriteAnimation.interpolate(1);
                group.getChildren().add(imageView);

                new AnimationTimer() {
                    long prv = -1;

                    @Override
                    public void handle(long now) {
                        if (now - prv < 5e7) {
                            return;
                        }
                        prv = now;
                        spriteAnimation.interpolate(1);
                        imageView.setX(imageView.getX() - 5);
                        if(imageView.getX() < 740) {
                            group.getChildren().remove(imageView);
                            stop();
                        }
                    }
                }.start();
            }
        });
    }

}
