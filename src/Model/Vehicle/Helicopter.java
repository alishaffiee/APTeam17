package Model.Vehicle;

import Interfaces.Upgradable;
import Model.Item;
import Model.ItemType;
import Model.Map;
import Values.Values;

import java.util.ArrayList;

public class Helicopter extends Vehicle implements Upgradable {
    ArrayList<ItemType> itemTypes;

    public Helicopter(Map map) {
        super(map);
    }

    public void go(ArrayList<ItemType> itemTypes) {
        if (!isFree())
            throw new RuntimeException("Hellicopter is on the way!");
        timeToComeBack = travelTime();
        this.itemTypes = itemTypes;
    }

    public void nextTurn() {
        if (isFree())
            return;
        super.nextTurn();
        if (timeToComeBack == 0) {
            for (ItemType itemType : itemTypes)
                map.getWarehouse().add(itemType);
            itemTypes = new ArrayList<>();
        }
    }

    @Override
    public int travelTime() {
        return 12 - 3 * level;
    }
}
