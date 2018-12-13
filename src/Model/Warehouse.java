package Model;

import src.Interfaces.Storageble;

import java.util.ArrayList;

public class Warehouse {
    ArrayList<ItemType> itemTypes;
    private int capacity;

    public Warehouse(int capacity) {
        itemTypes = new ArrayList<>();
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFreeCapacity() {
        int ans = capacity;
        for(ItemType itemType : itemTypes) {
            ans -= itemType.getVolume();
        }
        return ans;
    }

    void add(ItemType itemType) {
        if(itemType.getVolume() < getFreeCapacity())
            throw new RuntimeException("Not enough storage");
        itemTypes.add(itemType);
    }
}
