package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;

public class Bear extends WildAnimal{
    public Bear(Map map) {
        super(map, Values.LION_HEALTH, ItemType.getItemType("Bear"));
    }
}
