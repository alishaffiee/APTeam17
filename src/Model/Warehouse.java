package Model;

import src.Interfaces.Storageble;

import java.util.ArrayList;

public class Warehouse {
    private ArrayList<ItemType> itemTypes;
    private int capacity;

    public Warehouse(int capacity) {
        itemTypes = new ArrayList<>();
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<ItemType> getItemTypes() {
        return itemTypes;
    }

    public int getFreeCapacity() {
        int ans = capacity;
        for(ItemType itemType : itemTypes) {
            ans -= itemType.getVolume();
        }
        return ans;
    }

    public void add(ItemType itemType) {
        if(itemType.getVolume() < getFreeCapacity())
            throw new RuntimeException("Not enough storage");
        itemTypes.add(itemType);
    }

    public void remove(ItemType itemType) {
        for(ItemType itemType1 : itemTypes) {
            if(itemType.equals(itemType1)) {
                itemTypes.remove(itemType1);
                return;
            }
        }
    }
}
