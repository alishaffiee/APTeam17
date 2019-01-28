package Model.Vehicle;

import Interfaces.Printable;
import Interfaces.Upgradable;
import Model.Item;
import Model.ItemType;
import Model.Map;
import Values.Values;

import java.util.ArrayList;

public class Helicopter extends Vehicle implements Upgradable, Printable {
    ArrayList<ItemType> itemTypes;

    public Helicopter(Map map) {
        super(map);
    }

    public void go(ArrayList<ItemType> itemTypes, int cost) {
        if(map.getMoney() < cost)
            throw new RuntimeException("not enough money");
        int sum = 0;
        for(ItemType itemType : itemTypes) {
            sum += itemType.getVolume();
        }
        if(sum > map.getWarehouse().getFreeCapacity())
            throw new RuntimeException("not enough storage");

        map.addMoney(-cost);
        for(ItemType itemType : itemTypes) {
            map.getWarehouse().add(itemType);
        }
    }

    public void nextTurn() {

    }

    @Override
    public int travelTime() {
        return 12 - 3 * level;
    }

    public void print() {
        System.out.println("Is free = " + isFree());
        System.out.println("level = " + level);
        if(!isFree()) {
            System.out.println("Time to come back = " + timeToComeBack);
        }
    }
}
