package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;
import View.MoveAnimal;

public class Chicken extends Pet {
    public Chicken(Map map) {
        super(map, "Chicken", ItemType.getItemType("Egg"),
                Values.CHICKEN_PRODUCT_TIME, Values.CHICKEN_HEALTH, 1, 1);
        moveAnimal = new MoveAnimal("Chicken", 0, 0, 0, 1, 25, 5, 5, 5, 5, null);
    }
}
