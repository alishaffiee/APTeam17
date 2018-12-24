package Model.Animal;

import Model.Cell;
import Model.Item;
import Model.ItemType;
import Model.Map;

public class WildAnimal extends Animal {
    private int health, speed;
    private ItemType itemType;

    public WildAnimal(Map map, int health, ItemType itemType, int speed) {
        super(map);
        this.health = health;
        this.itemType = itemType;
        this.speed = speed;
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
            cell.getEntities().add(new Item(itemType, cell));
            cell.getEntities().remove(this);
        }
    }

    @Override
    public Cell nextMove() {
        if (map.getNearestAnimal(cell) != null) {
            return move(cell, map.getNearestItem(cell), getSpeed());
        }
        return randomMove(cell);
    }

    public ItemType getItemType() {
        return itemType;
    }
}
