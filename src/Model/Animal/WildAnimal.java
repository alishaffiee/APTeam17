package Model.Animal;

import Model.Cell;
import Model.Item;
import Model.ItemType;
import Model.Map;
import View.GameScene;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Random;

public class WildAnimal extends Animal {
    private int health, speed;
    private ItemType itemType;
    private boolean dead;

    public WildAnimal(Map map, String name, int health, ItemType itemType, int speed) {
        super(map, name);
        this.health = health;
        this.itemType = itemType;
        this.speed = speed;
        dead = false;
    }

    public void setKillAnimation() {
        ImageView[] imageViews = moveAnimal.getImageView();
        for(int i = 0; i < 4; i++) {
            imageViews[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    moveAnimal.kill();
                    cell.getEntities().remove(this);
                    dead = true;
                }
            });
        }
    }

    public boolean isDead() {
        return dead;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    public void nextTurn() {
    }

    public int getVolume() {
        return itemType.getVolume();
    }

    public void decreaseHealth() {
        health--;
        if (health == 0) {
            cell.add(new Item(itemType, cell));
            cell.delete(this);
        }
    }

    @Override
    public Cell nextMove() {
        Cell cell1 = map.getNearestAnimal(cell);
        if (cell1 != null && cell1.getPets().size() > 0 && map.getDistance(cell1, cell) < 600) {
            Pet pet = cell1.getPets().get(0);
            cell1.delete(pet);
            pet.kill();
            return cell;
        }

        if (map.getNearestAnimal(cell) != null) {
            return move(cell, map.getNearestAnimal(cell), speed);
        }
        return randomMove(speed);
    }

    public ItemType getItemType() {
        return itemType;
    }
}
