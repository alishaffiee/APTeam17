package View;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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

        primaryStage.setTitle("Truck");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
