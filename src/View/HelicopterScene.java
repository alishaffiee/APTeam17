package View;

import Control.CommandController;
import Control.Game;
import Model.ItemType;
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

    public static Group root;
    private Stage primaryStage;
    private Scene scene;

    private HelicopterScene() {
        root = new Group();
        scene = new Scene(root, 1100, 825);
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start() {
        ImageView background = getImage("./Graphic/helicopterback.jpg");
        root.getChildren().add(background);

        ImageView back = getImage("./Graphic/backbutton.png");
        back.setX(1000);
        back.setY(20);
        root.getChildren().add(back);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Back Helicopter menu");
                primaryStage.setScene(GameScene.gameScene.getScene());
            }
        });

        primaryStage.setTitle("Helicopter");
        primaryStage.setScene(scene);
        primaryStage.show();

        Warehouse warehouse = CommandController.commandController.getGame().getCurrentLevel().getMap().getWarehouse();
        ArrayList<ItemType> items = warehouse.getItemTypes();
        List<String> products = ItemsCosts.names;

        int dx = 185;
        int dy = 110;
        int startX = 100;
        int startY = 100, cur = 0;
        ImageView sendButton = GameScene.getImage("./Graphic/send.png");
        sendButton.setX(950);
        sendButton.setY(630);
        root.getChildren().add(sendButton);

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
                int w = (int) sell.getImage().getWidth();
                int h = (int) sell.getImage().getHeight();
                int x = startX + dx * i;
                int y = startY + dy * j;
                sell.setX(x);
                sell.setY(y);
                root.getChildren().add(sell);
                Text text = new Text(x + 70, y + 32,
                        String.valueOf(cnt) + "\n" + ItemType.getItemType(product).getBuyCost());

                text.setStyle("-fx-font: 18 arial;");

                ImageView sellButton = GameScene.getImage("./Graphic/buy.png");
                sellButton.setX(x + 140);

                sellButton.setY(y + 17);

                root.getChildren().add(text);
                root.getChildren().add(sellButton);

            }
        }
    }
}
