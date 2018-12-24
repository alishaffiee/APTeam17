package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;

public class Sheep extends Pet {
    public Sheep(Map map) {
        super(map, ItemType.getItemType("Wool"),
                Values.SHEEP_PRODUCT_TIME, Values.SHEEP_HEALTH, Values.SHEEP_SPEED, Values.SHEEP_SPEED * 2);
    }
}
