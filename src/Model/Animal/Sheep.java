package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;
import View.MoveAnimal;

public class Sheep extends Pet {
    public Sheep(Map map) {
        super(map, "Sheep", ItemType.getItemType("Wool"),
                Values.SHEEP_PRODUCT_TIME, Values.SHEEP_HEALTH, Values.SHEEP_SPEED, Values.SHEEP_SPEED * 2);
        moveAnimal = new MoveAnimal("Sheep", 0, 0, 0, 1, 25, 5, 5, 4);
    }
}
