package Model.Vehicle;

import Interfaces.Printable;
import Interfaces.Upgradable;
import Model.ItemType;
import Model.Map;
import Values.Values;

import java.util.ArrayList;

public class Truck extends Vehicle implements Upgradable, Printable {
    private int price;

    public Truck(Map map) {
        super(map);
    }

    public void go(ArrayList<ItemType> itemTypes) {
        if(!isFree())
            throw new RuntimeException("Truck is on the way.");
        int sum = 0;
        for(ItemType itemType : itemTypes) {
            sum += itemType.getVolume();
        }
        price = 0;
        for(ItemType itemType : itemTypes) {
            map.getWarehouse().remove(itemType);
            price += itemType.getSellCost();
        }
        map.addMoney(price);
    }

    @Override
    public void nextTurn() {
        if(isFree())
            return;
        super.nextTurn();
        if(timeToComeBack == 0) {
            map.addMoney(price);
        }
    }

    @Override
    public int travelTime() {
        return 20 - 5 * level;
    }

    public void print() {
        System.out.println("Is free = " + isFree());
        System.out.println("level = " + level);
        System.out.println("capacity = " + capacity);
        if(!isFree()) {
            System.out.println("Time to come back = " + timeToComeBack);
        }
    }
}
