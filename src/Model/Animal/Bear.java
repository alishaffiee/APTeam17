package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;

public class Bear extends WildAnimal{
    public Bear(Map map) {
        super(map, "Bear", Values.BEAR_HEALTH, ItemType.getItemType("CagedBear"), Values.BEAR_SPEED);
    }
}
