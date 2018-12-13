package Model.Animal;

import Model.ItemType;
import Model.Map;

public class Pet extends Animal{
    private ItemType prouduciton;

    public Pet(Map map, ItemType prouduciton) {
        super(map);
        this.prouduciton = prouduciton;
    }

    public void nextTurn() {

    }
}
