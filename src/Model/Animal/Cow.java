package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;

public class Cow extends Pet {
    public Cow(Map map) {
        super(map, "Cow", ItemType.getItemType("Milk"),
                Values.COW_PRODUCT_TIME, Values.COW_HEALTH, Values.COW_SPEED, Values.COW_SPEED * 2);
    }
}
