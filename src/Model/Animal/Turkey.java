package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;

public class Turkey extends Pet {
    public Turkey(Map map) {
        super(map, "Turkey", ItemType.getItemType("Plume"),
                Values.TURKEY_PRODUCT_TIME, Values.TURKEY_HEALTH, Values.TURKEY_SPEED, Values.TURKEY_SPEED * 2);
        start();
    }
}
