package View;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
        ImageView background = GameScene.getImage("./Graphic/Menu/Background.png");
        root.getChildren().add(background);


        primaryStage.setTitle("Helicopter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
