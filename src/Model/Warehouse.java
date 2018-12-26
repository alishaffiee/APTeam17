package Model;

import Interfaces.Printable;
import Interfaces.Upgradable;

import java.util.ArrayList;

public class Warehouse implements Upgradable, Printable {
    private ArrayList<ItemType> itemTypes;
    private int level;

    public Warehouse() {
        itemTypes = new ArrayList<>();
        level = 0;
    }

    public int getCapacity() {
        if(level == 0) return 50;
        if(level == 1) return 150;
        if(level == 2) return 300;
        if(level == 3) return 600;
        return -1;
    }

    public ArrayList<ItemType> getItemTypes() {
        return itemTypes;
    }

    public int getFreeCapacity() {
        int ans = getCapacity();
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

    public void upgrade() {
        if(level == 3)
            throw new RuntimeException("Already at max level.");
        level++;
    }

    public int getUpgradeCost() {
        return (level + 1) * 100;
    }

    public void print() {
        System.out.println("level = " + level);
        System.out.println("Capacity = " + getCapacity());
        System.out.println("Items : ");
        for(ItemType itemType : itemTypes) {
            System.out.println(itemType.getName());
        }
    }
}
