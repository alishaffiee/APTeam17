package View;

import Control.CommandController;
import Control.Game;
import Model.ItemType;
import Model.Vehicle.Helicopter;
import Model.Warehouse;
import Values.ItemsCosts;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static View.GameScene.gameScene;
import static View.GameScene.getImage;

public class HelicopterScene {
    public static HelicopterScene helicopterScene = new HelicopterScene();
    private Text cost = new Text("0");
    private int value = 0;


    public static Group root;
    private Stage primaryStage;
    private Scene scene;

    private HelicopterScene() {
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
        ImageView background = getImage("./Graphic/helicopterback.jpg");
        root.getChildren().add(background);

        addCoin(470, 40);

        ImageView back = getImage("./Graphic/backbutton.png");
        back.setX(1000);
        back.setY(20);
        root.getChildren().add(back);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Back Helicopter menu");
                root.getChildren().clear();
                primaryStage.setScene(GameScene.gameScene.getScene());
            }
        });

        primaryStage.setTitle("Helicopter");
        primaryStage.setScene(scene);
        primaryStage.show();

        Warehouse warehouse = CommandController.commandController.getGame().getCurrentLevel().getMap().getWarehouse();
        ArrayList<ItemType> itemTypes = new ArrayList<>();
        List<String> products = ItemsCosts.names;
        Helicopter helicopter = CommandController.commandController.getGame().getCurrentLevel().getMap().getHelicopter();

        int dx = 185;
        int dy = 110;
        int startX = 100;
        int startY = 100, cur = 0;

        for (String product : products) {
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
                        0 + "\n" + ItemType.getItemType(product).getBuyCost());

                text.setStyle("-fx-font: 18 arial;");

                ImageView sellButton = GameScene.getImage("./Graphic/buy.png");
                sellButton.setX(x + 140);

                sellButton.setY(y + 17);

                sellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    int num = 0;
                    @Override
                    public void handle(MouseEvent event) {
                        num++;
                        itemTypes.add(ItemType.getItemType(product));
                        text.setText(num  + "\n" + ItemType.getItemType(product).getBuyCost());
                        addValue(ItemType.getItemType(product).getBuyCost());
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

        sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    helicopter.go(itemTypes, value);
                } catch (Exception e) {
                    System.out.println("cannot send helicopter.");
                }
                itemTypes.clear();
                root.getChildren().clear();
                addValue(-value);
                primaryStage.setScene(GameScene.gameScene.getScene());
            }
        });
    }
}
