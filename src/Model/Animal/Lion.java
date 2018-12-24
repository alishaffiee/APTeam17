package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;

public class Lion extends WildAnimal{
    public Lion(Map map) {
        super(map, Values.LION_HEALTH, ItemType.getItemType("CagedLion"), Values.LION_SPEED);
    }


}
