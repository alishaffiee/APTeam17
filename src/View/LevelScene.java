package View;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static View.GameScene.getImage;

public class LevelScene {
    public static LevelScene LevelScene = new LevelScene();

    public static Group root;
    private Stage primaryStage;
    private Scene scene;

    private LevelScene() {
        root = new Group();
        scene = new Scene(root, 1100, 825);
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start() {
        ImageView background = GameScene.getImage("./Graphic/Menu/Background.png");
        root.getChildren().add(background);
        int offset = -10;
        /*
        addStartButton(410, 90 - offset);
        addLoadButton(410, 220 - offset);
        addClientButton(410, 350 - offset);
        addHostButton(410, 480 - offset);
        addQuitButton(410, 610 - offset);*/
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                int number = i + j * 3 + 1;
                ImageView image = GameScene.getImage("./Graphic/Level/" + number + ".png");
                int sx = 450;
                int sy = 300;
                int offsetX = 85;
                int offsetY = 85;
                addButton(sx + i * offsetX, sy + j * offsetY, image);

            }
        }
        addBackButton();
        primaryStage.setTitle("Farm frenzy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addButton(int x, int y, ImageView button) {
        button.setX(x);
        button.setY(y);
        root.getChildren().add(button);
    }
    private void addBackButton(){
        ImageView back = getImage("./Graphic/backbutton.png");
        back.setX(1000);
        back.setY(20);
        root.getChildren().add(back);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().clear();
                primaryStage.setScene(MenuScene.menuScene.getScene());
            }
        });

    }
}
