package Model.Animal;

import Model.Cell;
import Model.Item;
import Model.ItemType;
import Model.Map;

import java.util.Random;

public class WildAnimal extends Animal {
    private int health, speed;
    private ItemType itemType;

    public WildAnimal(Map map, String name, int health, ItemType itemType, int speed) {
        super(map, name);
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
            return move(cell, map.getNearestItem(cell), getSpeed());
        }
        return randomMove(cell);
    }

    public ItemType getItemType() {
        return itemType;
    }
}
