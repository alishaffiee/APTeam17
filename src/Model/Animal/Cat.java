package Model.Animal;

import Model.Cell;
import Model.Map;

import java.util.Random;

public class Cat extends Animal{
    private static int level = 1;

    public Cat(Map map) {
        super(map);
    }

    public void nextTurn() {

    }

    @Override
    public Cell nextMove() {
        if (map.getNearestItem(super.cell) != null) {
            Cell cell = map.getNearestItem(super.cell);
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
    public static int getLevel() {
        return level;
    }

    public static void upgrade() {
        if(level == 1)
            throw new RuntimeException("Cannot upgrade cat.");
        level++;
    }
}
