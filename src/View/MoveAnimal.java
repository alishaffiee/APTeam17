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
    private boolean killed;

    public static int dx[] = {-1, 0, +1, 0};
    public static int dy[] = {0, -1, 0, +1};
    private Group root;

    public MoveAnimal(String name, int positionX, int positionY, int direction,
                      int step, int count, int upColumns, int downColumns, int hColumns) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = direction;
        this.step = step;
        killed = false;
        root = GameScene.root;

        imageView[0] = GameScene.getImage("./Graphic/Animals/" + name + "/left.png");
        imageView[1] = GameScene.getImage("./Graphic/Animals/" + name + "/up.png");
        imageView[2] = GameScene.getImage("./Graphic/Animals/" + name + "/right.png");
        imageView[3] = GameScene.getImage("./Graphic/Animals/" + name + "/down.png");
        for (int i = 0; i < 4; i++) {
            imageView[i].setScaleX(0.75);
            imageView[i].setScaleY(0.75);
            int columns = (i % 2 == 1 ? (i==1 ? upColumns : downColumns) : hColumns);
            int rows = count / columns;
            int height = (int) imageView[i].getImage().getHeight() / rows;
            int width = (int) imageView[i].getImage().getWidth() / columns;
            spriteAnimations[i] = new SpriteAnimation(imageView[i], new Duration(1000), count, columns, 0, 0, width, height);
        }
    }

    public void nextMove() {
        if(killed)
            return;
        move();
        for(int i = 0; i < 4; i++) {
            int k = 1;
            if(i==2)
                k = -1;
            spriteAnimations[i].interpolate(k);
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

    public ImageView[] getImageView() {
        return imageView;
    }

    public void kill() {
        killed = true;
    }
}
