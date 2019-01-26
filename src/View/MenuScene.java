package View;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MenuScene {
    public static MenuScene menuScene = new MenuScene();

    public static Group root;
    private Stage primaryStage;
    private Scene scene;

    private MenuScene() {
        root = new Group();
        scene = new Scene(root, 1100, 825);
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start() {
        ImageView background = GameScene.getImage("./Graphic/Menu/Background.png");
        root.getChildren().add(background);

        addStartButton(410, 170);
        addLoadButton(410, 300);
        addQuitButton(410, 430);

        primaryStage.setTitle("Farm Frenzy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addButton (int x, int y, ImageView button) {
        button.setX(x);
        button.setY(y);
        root.getChildren().add(button);
    }

    private void addText (Text text) {
        text.setFont(Font.font(null, FontWeight.BOLD, 32));
        root.getChildren().add(text);
    }

    private void addStartButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 64, y + 57, "New Game!");

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
            public void handle (MouseEvent event) {
                text.setFill(Color.rgb(0, 0, 0));
                GameScene.gameScene.setPrimaryStage(primaryStage);
                GameScene.gameScene.start();
            }
        });
    }

    private void addLoadButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 107, y + 57, "Load");

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
            public void handle (MouseEvent event) {
                text.setFill(Color.rgb(0, 0, 0));
            }
        });
    }

    private void addQuitButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 112, y + 57, "Quit");

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
            public void handle (MouseEvent event) {
                text.setFill(Color.rgb(0, 0, 0));
                primaryStage.close();
            }
        });
    }
}
