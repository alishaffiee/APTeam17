package View;

import Control.CommandController;
import Control.Game;
import Model.ItemType;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class MoveAnimal {
    ImageView[] imageView = new ImageView[4];
    SpriteAnimation[] spriteAnimations = new SpriteAnimation[4];
    ImageView death;
    SpriteAnimation deathSpriteAnimation;

    private int positionX, positionY, direction, step, cnt;
    private boolean killed;
    private ItemType itemType;

    public static int dx[] = {-1, 0, +1, 0};
    public static int dy[] = {0, -1, 0, +1};
    private Group root;

    public MoveAnimal(String name, int positionX, int positionY, int direction,
                      int step, int count, int upColumns, int downColumns, int hColumns, int killColumns, ItemType itemType) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = direction;
        this.cnt = count;
        this.step = step;
        this.itemType = itemType;

        killed = false;
        root = GameScene.root;

        int rows = count / killColumns;

        imageView[0] = GameScene.getImage("./Graphic/Animals/" + name + "/left.png");
        imageView[1] = GameScene.getImage("./Graphic/Animals/" + name + "/up.png");
        imageView[2] = GameScene.getImage("./Graphic/Animals/" + name + "/right.png");
        imageView[3] = GameScene.getImage("./Graphic/Animals/" + name + "/down.png");
        death = GameScene.getImage("./Graphic/Animals/" + name + "/death.png");


        death.setScaleX(0.75);
        death.setScaleY(0.75);

        deathSpriteAnimation = new SpriteAnimation(death, new Duration(100), count, killColumns, 0, 0,
                (int) death.getImage().getWidth() / killColumns, (int) death.getImage().getHeight() / rows);

        for (int i = 0; i < 4; i++) {
            imageView[i].setScaleX(0.75);
            imageView[i].setScaleY(0.75);
            int columns = (i % 2 == 1 ? (i == 1 ? upColumns : downColumns) : hColumns);
            rows = count / columns;
            int height = (int) imageView[i].getImage().getHeight() / rows;
            int width = (int) imageView[i].getImage().getWidth() / columns;
            spriteAnimations[i] = new SpriteAnimation(imageView[i], new Duration(1000), count, columns, 0, 0, width, height);
        }
    }

    public void nextMove() {
        if (killed)
            return;
        move();
        for (int i = 0; i < 4; i++) {
            int k = 1;
            if (i == 2)
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

    public void setStep(int step) {
        this.step = step;
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

    public ImageView getDeath() {
        return death;
    }

    public void kill() {
        root.getChildren().remove(imageView[this.direction]);
        death.setX(positionX);
        death.setY(positionY);
        if(root.getChildren().contains(death)) {
            System.out.println(1);
            return;
        }
        root.getChildren().add(death);
        deathSpriteAnimation.interpolate(1);
        new AnimationTimer() {
            long prv = -1, count = 0;

            @Override
            public void handle(long now) {
                if (now - prv < 5e7) {
                    return;
                }
                count++;
                if (count == cnt - 3) {
                    death.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(itemType != null) {
                                try {
                                    CommandController.commandController.getGame().getCurrentLevel().getMap().getWarehouse().add(itemType);
                                } catch (Exception e) {
                                    System.out.println("Cannot add this object to warehouse.");
                                    return;
                                }
                            }
                            new AnimationTimer() {
                                long prv = -1, x = positionX, y = positionY;
                                @Override
                                public void handle(long now) {
                                    if(now - prv < 2e8)
                                        return;
                                    x += (500 - x) / 10;
                                    y += (650 - y) / 10;
                                    death.relocate(x, y);
                                    if(Math.abs(x - 500) < 20 && Math.abs(y - 650) < 20) {
                                        root.getChildren().remove(death);
                                        stop();
                                    }
                                }
                            }.start();
                        }
                    });

                    new AnimationTimer() {
                        long prv = -1;
                        @Override
                        public void handle(long now) {
                            if(now - prv < 5e9 && prv != -1)
                                return;
                            if(prv == -1) {
                                prv = now;
                                return;
                            }
                            if(!root.getChildren().contains(death)) {
                                stop();
                                return;
                            }
                            new AnimationTimer() {
                                long prv = -1, cnt = 0;
                                @Override
                                public void handle(long now) {
                                    if(now - prv < 8e8)
                                        return;
                                    cnt++;
                                    if(cnt==25) stop();
                                    if(cnt % 2 == 1) {
                                        root.getChildren().remove(death);
                                        return;
                                    }

                                    root.getChildren().add(death);
                                }
                            }.start();
                            stop();
                        }
                    }.start();

                    stop();
                }
                prv = now;
                deathSpriteAnimation.interpolate(1);
            }
        }.start();
        killed = true;
    }
}
