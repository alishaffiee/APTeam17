package Model.Animal;

import Model.Cell;
import Model.Map;
import Values.Values;
import View.MoveAnimal;

public class Dog extends Animal {

    public Dog(Map map) {
        super(map, "Dog");
        moveAnimal = new MoveAnimal("Dog", 0, 0, 0, 1, 24, 6, 6);
    }

    public int getSpeed() {
        return Values.DOG_SPEED;
    }

    public void nextTurn() {

    }

    @Override
    public Cell nextMove() {
        if (map.getNearestWildAnimal(cell) != null) {
            return move(cell, map.getNearestWildAnimal(cell));
        }
        return randomMove(cell);
    }
}
