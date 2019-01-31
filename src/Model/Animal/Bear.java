package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;
import View.MoveAnimal;

public class Bear extends WildAnimal{
    public Bear(Map map) {
        super(map, "Bear", Values.BEAR_HEALTH, ItemType.getItemType("CagedBear"), 2);
        moveAnimal = new MoveAnimal("Bear", 0, 0, 0, 2, 24, 4, 4, 4, 6, ItemType.getItemType("CagedGrizzly"));
        setKillAnimation();
    }
}
