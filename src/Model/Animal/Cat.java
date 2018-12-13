package Model.Animal;

import Model.Cell;
import Model.Map;

public class Cat extends Animal{
    private int level;

    public Cat(Map map) {
        super(map);
        level = 1;
    }

    public void nextTurn() {

    }

    @Override
    public Cell nextMove() {
        return null;
    }
}
