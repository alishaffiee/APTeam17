package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;
import View.MoveAnimal;

public class Cow extends Pet {
    public Cow(Map map) {
        super(map, "Cow", ItemType.getItemType("Milk"),
                Values.COW_PRODUCT_TIME, Values.COW_HEALTH, Values.COW_SPEED, Values.COW_SPEED * 2);
        moveAnimal = new MoveAnimal("Cow", 0, 0, 0, 1, 24, 4, 3, 3, 3);
    }
}
