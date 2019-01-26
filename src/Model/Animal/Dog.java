package Model.Animal;

import Model.Cell;
import Model.Map;
import Values.Values;
import View.MoveAnimal;

public class Dog extends Animal {

    public Dog(Map map) {
        super(map, "Dog");
        moveAnimal = new MoveAnimal("Dog", 0, 0, 0, 1, 24, 6, 6, 6, 6);
    }

    public int getSpeed() {
        return Values.DOG_SPEED;
    }

    public void nextTurn() {

    }

    @Override
    public Cell nextMove() {
        Cell cell1 = map.getNearestWildAnimal(cell);
        if (cell1 != null && cell1.getWildAnimals().size() > 0 && map.getDistance(cell1, cell) < 600) {
            WildAnimal wildAnimal = cell1.getWildAnimals().get(0);
            cell1.delete(wildAnimal);
            wildAnimal.kill();
            return cell;
        }

        if (map.getNearestWildAnimal(cell) != null) {
            return move(cell, map.getNearestWildAnimal(cell));
        }
        return randomMove();
    }
}
