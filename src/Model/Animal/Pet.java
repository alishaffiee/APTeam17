package Model.Animal;

import Model.Cell;
import Model.Item;
import Model.ItemType;
import Model.Map;

import java.util.Random;

public class Pet extends Animal{
    private ItemType prouduciton;
    private int turnsToHungry, turnsToProduct, productTime;

    public Pet(Map map, ItemType prouduciton, int productTime) {
        super(map);
        this.prouduciton = prouduciton;
        this.productTime = productTime;
    }

    public Cell nextMove() {
        if(turnsToHungry < map.getDistance(map.getNearestGrass(cell), cell) - 2 &&  map.getNearestGrass(super.cell) != null) {
            Cell cell = map.getNearestGrass(super.cell);
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

    public void nextTurn() {
        turnsToHungry--;
        turnsToProduct--;
        if(turnsToProduct == 0) {
            cell.addEntity(new Item(prouduciton, cell));
            turnsToProduct = productTime;
        }
    }
}
