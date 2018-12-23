package Model.Animal;

import Model.*;

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
        if (turnsToHungry < map.getDistance(map.getNearestGrass(cell), cell) + 2 && map.getNearestGrass(super.cell) != null) {
            if (super.cell.hasGrass())
                return super.cell;
            Cell cell = map.getNearestGrass(super.cell);
            int dx = cell.getPositionX() - super.cell.getPositionX();
            int dy = cell.getPositionY() - super.cell.getPositionY();
            if (Math.abs(dx) < Math.abs(dy)) {
                return map.getCell(super.cell.getPositionX(), super.cell.getPositionY() + sign(dy));
            } else {
                return map.getCell(super.cell.getPositionX() + sign(dx), super.cell.getPositionY());
            }
        }
        return randomMove(cell);
    }

    public void nextTurn() {
        turnsToHungry--;
        turnsToProduct--;
        if(turnsToProduct == 0) {
            cell.addEntity(new Item(prouduciton, cell));
            turnsToProduct = productTime;
        }
        if(cell.hasGrass() && turnsToHungry < 4) {
            Grass grass = cell.getGrass();
            grass.eat();
            turnsToHungry += 5;
        }
        if(turnsToHungry == -1) {
            cell.getEntities().remove(this);
        }
    }
}
