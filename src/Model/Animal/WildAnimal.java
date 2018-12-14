package Model.Animal;

import Model.Cell;
import Model.Item;
import Model.ItemType;
import Model.Map;
import src.Interfaces.Storageble;

import java.util.Random;

public class WildAnimal extends Animal implements Storageble {
    private int health;
    private ItemType itemType;

    public WildAnimal(Map map, int health, ItemType itemType) {
        super(map);
        this.health = health;
        this.itemType = itemType;
    }

    public void nextTurn() {

    }

    public int getVolume() {
        return itemType.getVolume();
    }

    public void decreaseHealth() {
        health--;
        if(health == 0) {
            cell.getEntities().add(new Item(itemType, cell));
            cell.getEntities().remove(this);
        }
    }

    @Override
    public Cell nextMove() {
        if(map.getNearestAnimal(super.cell) != null) {
            Cell cell = map.getNearestAnimal(super.cell);
            int dx = cell.getPositionX() - super.cell.getPositionX();
            int dy = cell.getPositionY() - super.cell.getPositionY();
            if(Math.abs(dx) < Math.abs(dy)) {
                return map.getCell(super.cell.getPositionX(), super.cell.getPositionY() + sign(dy));
            }
            else {
                return map.getCell(super.cell.getPositionX() + sign(dx), super.cell.getPositionY());
            }
        }
        Random random = new Random();
        int x = random.nextInt(5);
        int[] dx = {0,-1,1,0,0};
        int[] dy = {0,0,0,1,-1};
        return map.getCell(cell.getPositionX() + dx[x], cell.getPositionY() + dy[x]);
    }

    public ItemType getItemType() {
        return itemType;
    }
}
