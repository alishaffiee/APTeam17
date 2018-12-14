package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;

public class Chiken extends Pet {
    public Chiken(Map map)
    {
        super(map, ItemType.getItemType("Egg"), Values.CHICKEN_PRODUCT_TIME);
    }
}
