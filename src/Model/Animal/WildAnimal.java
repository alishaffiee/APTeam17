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
        System.out.println(1);
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
        Random random = new Random();
        if (cell.getPets().size() > 0 && random.nextInt(10) < 2) {
            Pet pet = cell.getPets().get(0);
            cell.delete(pet);
            return cell;
        }
        if (map.getNearestAnimal(cell) != null) {
            return move(cell, map.getNearestItem(cell));
        }
        return randomMove();
    }

    public ItemType getItemType() {
        return itemType;
    }
}
