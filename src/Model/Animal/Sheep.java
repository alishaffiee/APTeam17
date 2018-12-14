package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;

public class Sheep extends Pet {
    public Sheep(Map map)
    {
        super(map, ItemType.getItemType("Wool"), Values.SHEEP_PRODUCT_TIME);
    }
}
