package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;

public class Cow extends Pet{
    public Cow(Map map)
    {
        super(map, ItemType.getItemType("Milk"), Values.COW_PRODUCT_TIME);
    }
}
