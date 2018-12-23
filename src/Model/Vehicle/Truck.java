package Model.Vehicle;

import Model.ItemType;
import Model.Map;
import Values.Values;

import java.util.ArrayList;

public class Truck extends Vehicle{
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
        timeToComeBack = Values.TRUCK_BACK_TIME;
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
}
