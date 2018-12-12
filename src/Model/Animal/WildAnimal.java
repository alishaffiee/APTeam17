package Model.Animal;

import src.Interfaces.Storageble;

public class WildAnimal extends Animal implements Storageble {
    private int Volume;

    public void nextTurn() {

    }

    public int getVolume() {
        return Volume;
    }
}
