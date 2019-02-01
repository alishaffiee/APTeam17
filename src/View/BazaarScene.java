package View;

import Control.CommandController;
import Model.ItemType;
import Model.Warehouse;
import Network.Client;
import Values.ItemsCosts;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

import static View.GameScene.getImage;

public class BazaarScene {
    public static BazaarScene bazarScene = new BazaarScene();
    private Text cost = new Text("0");
    private int value = 0;


    public static Group root;
    private Stage primaryStage;
    private Scene scene;
    private Client client;
    private Socket bazaarSocket;
    private Formatter formatter;
    private Scanner scanner;

    private BazaarScene() {
        root = new Group();
        scene = new Scene(root, 1100, 825);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private String get(String command) {
        formatter.format(command + "\n");
        formatter.flush();
        return scanner.nextLine();
    }

    public void start(Client client) {
        this.client = client;
        ImageView background = getImage("./Graphic/bazar.jpg");
        root.getChildren().add(background);

        ImageView back = getImage("./Graphic/backbutton.png");
        back.setX(1000);
        back.setY(20);
        root.getChildren().add(back);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Back Bazaar menu");
                root.getChildren().clear();
                primaryStage.setScene(GameScene.gameScene.getScene());
            }
        });

        primaryStage.setTitle("Bazaar");
        primaryStage.setScene(scene);
        primaryStage.show();

        Warehouse warehouse = CommandController.commandController.getGame().getCurrentLevel().getMap().getWarehouse();
        List<String> products = ItemsCosts.names;

        bazaarSocket = client.getBazaarSocket();
        try {
            scanner = new Scanner(bazaarSocket.getInputStream());
            formatter = new Formatter(bazaarSocket.getOutputStream());
        } catch (Exception e) {
            System.err.println("cannot connect to server.");
            return;
        }

        int dx = 210;
        int dy = 110;
        int startX = 30;
        int startY = 100, cur = 0;

        TextInput itemName = new TextInput("item name", 200, 20);
        TextInput cost = new TextInput("new cost", 200, 55);
        ImageView submit = getImage("./Graphic/Menu/check.png");
        submit.setX(160);
        submit.setY(17);
        root.getChildren().add(submit);
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                get("change " + itemName.getString() + " " + cost.getString());
                root.getChildren().clear();
                primaryStage.setScene(GameScene.gameScene.getScene());
            }
        });
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
                ItemType itemType = ItemType.getItemType(product);
                Text text = new Text(x + 70, y + 32, warehouse.count(itemType) + " " + get("count " + product) + "\n" + get("cost " + product));

                text.setStyle("-fx-font: normal bold 20px 'serif'");

                ImageView buyButton = GameScene.getImage("./Graphic/buy.png");
                buyButton.setX(x + 165);

                buyButton.setY(y + 17);

                final String name = product;

                buyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Warehouse warehouse = CommandController.commandController.getGame().getCurrentLevel().getMap().getWarehouse();
                        ItemType itemType = ItemType.getItemType(name);
                        int cnt = Integer.valueOf(get("count " + product));
                        int cost = Integer.valueOf(get("cost " + name));
                        if(cnt == 0 || warehouse.getFreeCapacity() < itemType.getVolume() || cost > CommandController.commandController.getGame().getCurrentLevel().getMap().getMoney())
                            return;
                        warehouse.add(itemType);
                        CommandController.commandController.getGame().getCurrentLevel().getMap().addMoney(-Integer.valueOf(get("cost " + name)));
                        get("remove " + name);
                        root.getChildren().clear();
                        primaryStage.setScene(GameScene.gameScene.getScene());
                    }
                });

                root.getChildren().add(text);
                root.getChildren().add(buyButton);

                ImageView sellButton = GameScene.getImage("./Graphic/sell.png");
                sellButton.setX(x + 125);
                sellButton.setY(y + 17);
                root.getChildren().add(sellButton);

                sellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Warehouse warehouse = CommandController.commandController.getGame().getCurrentLevel().getMap().getWarehouse();
                        ItemType itemType = ItemType.getItemType(name);
                        if(warehouse.count(itemType) == 0)
                            return;
                        System.out.println(itemType.getName());
                        warehouse.remove(itemType);
                        System.out.println(warehouse.count(itemType));
                        CommandController.commandController.getGame().getCurrentLevel().getMap().addMoney(Integer.valueOf(get("cost " + name)));
                        get("add " + name);
                        root.getChildren().clear();
                        primaryStage.setScene(GameScene.gameScene.getScene());
                    }
                });

            }
        }
    }
}
