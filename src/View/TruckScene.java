package View;

import Control.CommandController;
import Model.Item;
import Model.ItemType;
import Model.Warehouse;
import Values.ItemsCosts;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static View.GameScene.getImage;

public class TruckScene {
    public static TruckScene truckScene = new TruckScene();

    public static Group root;
    private Stage primaryStage;
    private Scene scene;

    private TruckScene() {
        root = new Group();
        scene = new Scene(root, 1100, 825);
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start() {
        ImageView background = GameScene.getImage("./Graphic/truckback.jpg");
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

        Warehouse warehouse = CommandController.commandController.getGame().getCurrentLevel().getMap().getWarehouse();
        ArrayList<ItemType> items = warehouse.getItemTypes();
        List<String> products = ItemsCosts.names;

        primaryStage.setTitle("Truck");
        primaryStage.setScene(scene);
        primaryStage.show();

        int dx = 150;
        int dy = 100;
        int startX = 100;
        int startY = 100, cur = 0;
        for (String product : products) {
            int cnt = 0;
            for (ItemType item : items) {
                if(item.getName().equals(product))
                    cnt++;
            }
            System.out.println(product + " " + cnt);
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
                sell.setX(startX + dx * i);
                sell.setY(startY + dy * j);
                root.getChildren().add(sell);
                Text text = new Text(startX + dx * i + 2 * w/2 + 20, startY + dy * j + h/2, String.valueOf(cnt));
                



                root.getChildren().add(text);
            }
        }

    }

}
