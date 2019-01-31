package Model.Animal;

import Model.ItemType;
import Model.Map;
import Values.Values;
import View.MoveAnimal;

public class Lion extends WildAnimal{
    public Lion(Map map) {
        super(map, "Lion", Values.LION_HEALTH, ItemType.getItemType("CagedLion"), 2);
        moveAnimal = new MoveAnimal("Lion", 0, 0, 0, 2, 25, 6, 5, 3, 6, ItemType.getItemType("CagedLion"));
        setKillAnimation();
    }
}
