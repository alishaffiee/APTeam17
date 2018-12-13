package Model.Animal;

import Model.Cell;
import Model.Map;
import src.Interfaces.Storageble;

public class WildAnimal extends Animal implements Storageble {
    private int Volume;

    public WildAnimal(Map map) {
        super(map);
    }

    public void nextTurn() {

    }

    public int getVolume() {
        return Volume;
    }

    @Override
    public Cell nextMove() {
        return null;
    }
}
