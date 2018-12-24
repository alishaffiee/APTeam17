package Model.Animal;

import Model.Cell;
import Model.Map;
import Values.Values;

public class Dog extends Animal {

    public Dog(Map map) {
        super(map);
    }

    public int getSpeed() {
        return Values.DOG_SPEED;
    }

    public void nextTurn() {

    }

    @Override
    public Cell nextMove() {
        if (map.getNearestWildAnimal(cell) != null) {
            return move(cell, map.getNearestWildAnimal(cell), getSpeed());
        }
        return randomMove(cell);
    }
}
