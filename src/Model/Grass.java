package Model;

import Control.CommandController;
import Values.Values;
import View.GameScene;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Grass extends Entity {
    private int turnsToDie, value;
    private Cell cell;
    private final int offset = 4;
    private ImageView imageView;

    public Grass(Cell cell, ImageView imageView) {
        this.cell = cell;
        this.imageView = imageView;
        turnsToDie = Values.GRASS_LIFE_TIME;
        value = Values.GRASS_VALUE;
        cell.getEntities().add(this);
    }

    private void kill() {
        cell.delete(this);
        GameScene.root.getChildren().remove(imageView);
    }

    public void nextTurn() {
        turnsToDie--;
        if (turnsToDie == 0) {
            kill();
        }
    }

    public void eat() {
        System.out.println(value);
        value--;
        if (value == 0) {
            kill();
        }
    }
}
