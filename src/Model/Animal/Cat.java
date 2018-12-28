package Model.Animal;

import Model.Cell;
import Model.Map;
import Values.Values;

public class Cat extends Animal {
    private static int level = 1;

    public Cat(Map map) {
        super(map, "Cat");
    }

    public void nextTurn() {

    }

    public int getSpeed() {
        return Values.CAT_SPEED;
    }

    @Override
    public Cell nextMove() {
        if (map.getNearestItem(cell) != null) {
            return move(cell, map.getNearestItem(cell), getSpeed());
        }
        return randomMove(cell);
    }

    public static int getLevel() {
        return level;
    }

    public static void upgrade() {
        if (level == 1)
            throw new RuntimeException("Cannot upgrade cat.");
        level++;
    }
}
