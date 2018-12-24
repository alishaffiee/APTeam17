package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;

public class Chicken extends Pet {
    public Chicken(Map map) {
        super(map, ItemType.getItemType("Egg"),
                Values.CHICKEN_PRODUCT_TIME, Values.CHICKEN_HEALTH, Values.CHICKEN_SPEED, Values.CHICKEN_SPEED * 2);
    }
}
