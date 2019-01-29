package View;

import Network.Client;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientMenuScene {
    public static ClientMenuScene clientMenuScene = new ClientMenuScene();

    public static Group root;
    private Stage primaryStage;
    private Scene scene;
    private Client client;

    private ClientMenuScene() {
        root = new Group();
        scene = new Scene(root, 1100, 825);
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start(Client client) {
        this.client = client;
        ImageView background = GameScene.getImage("./Graphic/Menu/Background.png");
        root.getChildren().add(background);
        int offset = 60;

        addUsersButton(410, offset);
        addRankingButton(410, 130 + offset);
        addChatRoomButton(410, 260 + offset);

        primaryStage.setTitle("Farm Frenzy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addButton(int x, int y, ImageView button) {
        button.setX(x);
        button.setY(y);
        root.getChildren().add(button);
    }


    private void addText(Text text) {
        text.setFont(Font.font(null, FontWeight.BOLD, 32));
        root.getChildren().add(text);
    }

    private void addRankingButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 82, y + 57, "Ranking");

        addButton(x, y, button);
        addText(text);

        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(200, 200, 200));
            }
        });

        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(0, 0, 0));
            }
        });
    }

    private void addUsersButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 82, y + 57, "Users");

        addButton(x, y, button);
        addText(text);

        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(200, 200, 200));
                client.addCommand("get users");
            }
        });

        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(0, 0, 0));
            }
        });
    }

    private void addChatRoomButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 72, y + 57, "Chat Room");

        addButton(x, y, button);
        addText(text);

        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(200, 200, 200));
            }
        });

        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(0, 0, 0));
            }
        });
    }
}
