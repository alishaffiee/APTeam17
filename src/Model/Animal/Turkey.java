package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;

public class Turkey extends Pet {
    public Turkey(Map map) {
        super(map, ItemType.getItemType("Plume"), Values.TURKEY_PRODUCT_TIME);
    }
}
