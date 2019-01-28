package View;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
        ImageView background = GameScene.getImage("./Graphic/Menu/Background.png");
        root.getChildren().add(background);


        primaryStage.setTitle("Truck");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
