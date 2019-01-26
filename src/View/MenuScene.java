package View;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
        ImageView background = GameScene.getImage("./Graphic/Menu/Background.png");
        root.getChildren().add(background);

        addStartButton(410, 170);

        primaryStage.setTitle("Farm Frenzy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addStartButton(int x, int y){
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        button.setX(x);
        button.setY(y);

        root.getChildren().add(button);

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GameScene.gameScene.setPrimaryStage(primaryStage);
                GameScene.gameScene.start();
            }
        });
    }
}
