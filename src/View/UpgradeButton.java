package View;


import Control.CommandController;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class UpgradeButton {
    private String name;
    private int positionX, positionY;
    private ImageView imageView, prvImage;
    private int level;

    public UpgradeButton(String name, int positionX,int positionY, SpriteAnimation spriteAnimation, GameScene gameScene, final ImageView prvImage){
        this.positionX = positionX;
        this.positionY = positionY;
        this.level = 1;
        this.name = name;
        imageView = GameScene.getImage("./Graphic/plus.png");
        imageView.setX(positionX);
        imageView.setY(positionY);
        GameScene.root.getChildren().add(imageView);

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            ImageView imageView = prvImage;
            @Override
            public void handle(MouseEvent event) {
                try {
                    CommandController.commandController.upgrade(name.toLowerCase());
                    GameScene.root.getChildren().remove(imageView);
                    if(spriteAnimation != null)
                        GameScene.root.getChildren().remove(spriteAnimation.getImageView());
                    int level = CommandController.commandController.getLevel(name) + 1;
                    imageView = gameScene.addIcon(positionX, positionY, level, name);

                } catch (Exception e) {
                    System.out.println("cannot upgrade" + name);
                }

            }
        });
    }
}
