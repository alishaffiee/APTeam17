package Model.Vehicle;

import Interfaces.Upgradable;
import Model.ItemType;
import Model.Map;
import Values.Values;

import java.util.ArrayList;

public class Truck extends Vehicle implements Upgradable {
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
        if(sum > getCapacity())
            throw new RuntimeException("Not enough capacity.");
        price = 0;
        for(ItemType itemType : itemTypes)
            price += itemType.getBuyCost();
        timeToComeBack = travelTime();
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
}
