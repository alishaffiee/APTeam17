package Model;

import Interfaces.Printable;
import Interfaces.Upgradable;

import java.io.Serializable;
import java.util.ArrayList;

public class Warehouse implements Upgradable, Printable, Serializable {
    private ArrayList<ItemType> itemTypes, allItems;
    private int level;

    public Warehouse() {
        itemTypes = new ArrayList<>();
        allItems = new ArrayList<>();
        level = 0;
    }

    public int getCapacity() {
        if (level == 0) return 50;
        if (level == 1) return 150;
        if (level == 2) return 300;
        if (level == 3) return 600;
        return -1;
    }

    public ArrayList<ItemType> getItemTypes() {
        return itemTypes;
    }

    public int getFreeCapacity() {
        int ans = getCapacity();
        for (ItemType itemType : itemTypes) {
            ans -= itemType.getVolume();
        }
        return ans;
    }

    public void add(ItemType itemType) {
        if (itemType.getVolume() > getFreeCapacity())
            throw new RuntimeException("Not enough storage");
        itemTypes.add(itemType);
        allItems.add(itemType);
    }

    public void remove(ItemType itemType) {
        for (ItemType itemType1 : itemTypes) {
            if (itemType.equals(itemType1)) {
                itemTypes.remove(itemType1);
                return;
            }
        }
    }

    public boolean canUpgrade() {
        return level < 3;
    }

    public void upgrade() {
        if (level == 3)
            throw new RuntimeException("Already at max level.");
        level++;
    }

    public int getUpgradeCost() {
        return (level + 1) * 100;
    }

    public void print() {
        System.out.println("level = " + level);
        System.out.println("Capacity = " + getCapacity());
        System.out.println("Free capacity = " + getFreeCapacity());
        if (itemTypes.size() == 0) {
            System.out.println("Warehouse is empty.");
            return;
        }
        System.out.println("Items : ");
        for (ItemType itemType : itemTypes) {
            System.out.println(" + " + itemType.getName());
        }
    }

    public int count(ItemType itemType) {
        int ans = 0;
        for (ItemType itemType1 : itemTypes) {
            if (itemType.equals(itemType1))
                ans++;
        }
        return ans;
    }

    @Override
    public int getLevel() {
        System.out.println(6);
        return level;
    }
}
