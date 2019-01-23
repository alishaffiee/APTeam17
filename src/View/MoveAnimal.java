package View;

import Control.Game;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MoveAnimal {
    ImageView[] imageView = new ImageView[4];
    SpriteAnimation[] spriteAnimations = new SpriteAnimation[4];

    private int positionX, positionY, direction, step;

    public static int dx[] = {-1, 0, +1, 0};
    public static int dy[] = {0, -1, 0, +1};
    private Group root;

    public MoveAnimal(String name, int positionX, int positionY, int direction,
                      int step, int count, int vColumns, int hColumns) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = direction;
        this.step = step;
        root = GameScene.root;

        imageView[0] = GameScene.getImage("./Graphic/Animals/" + name + "/left.png");
        imageView[1] = GameScene.getImage("./Graphic/Animals/" + name + "/up.png");
        imageView[2] = GameScene.getImage("./Graphic/Animals/" + name + "/right.png");
        imageView[3] = GameScene.getImage("./Graphic/Animals/" + name + "/down.png");
        System.out.println(imageView[0]==null);
        System.out.println(imageView[1]==null);
        System.out.println(imageView[2]==null);
        System.out.println(imageView[3]==null);
        for (int i = 0; i < 4; i++) {
            imageView[i].setScaleX(0.75);
            imageView[i].setScaleY(0.75);
            int columns = (i % 2 == 1 ? vColumns : hColumns);
            int rows = count / columns;
          //  System.out.println(imageView[i]==null);
            int height = (int) imageView[i].getImage().getHeight() / rows;
            int width = (int) imageView[i].getImage().getWidth() / columns;
            spriteAnimations[i] = new SpriteAnimation(imageView[i], new Duration(1000), count, columns, 0, 0, width, height);
            final int id = i;
            new AnimationTimer() {
                long prv = -1;

                @Override
                public void handle(long now) {
                    if (now - prv < 5e7) {
                        return;
                    }
                    prv = now;
                    move();
                    spriteAnimations[id].interpolate(0.1);
                }
            }.start();
        }
    }

    public void start() {
        root.getChildren().add(imageView[direction]);
    }

    private void update() {
        imageView[direction].setX(positionX);
        imageView[direction].setY(positionY);
    }

    public void setDirection(int direction) {
        root.getChildren().remove(imageView[this.direction]);
        this.direction = direction;
        update();
        root.getChildren().add(imageView[direction]);
    }

    public int getDirection() {
        return direction;
    }

    public void move() {
        positionX += step * dx[direction];
        positionY += step * dy[direction];
        update();
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
