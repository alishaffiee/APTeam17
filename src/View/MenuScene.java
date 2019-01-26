package View;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MenuScene {
    public static MenuScene menuScene = new MenuScene();

    public static Group root;
    private Stage primaryStage;
    private Scene scene;

    private MenuScene(){
        root = new Group();
        scene = new Scene(root, 1100, 825);
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start(){
        ImageView background = GameScene.getImage("./Graphic/MenuBackground.png");
        root.getChildren().add(background);

        primaryStage.setTitle("Farm Frenzy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}