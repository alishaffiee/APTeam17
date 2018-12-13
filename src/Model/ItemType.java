package Model;

import java.util.ArrayList;

public class ItemType {
    private static ArrayList<ItemType> itemTypes;
    private String name;
    private int volume, buyCost, sellCost;

    public void ItemType(String name, int volume, int buyCost, int sellCost) {
        this.volume = volume;
        this.name = name;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
    }

    public int getVolume() {
        return volume;
    }

    public String getName() {
        return name;
    }

    public boolean equals(ItemType itemType) {
        return this.name.equals(itemType.name);
    }

    public static void addItemType(ItemType itemType) {
        for(ItemType itemType1 : itemTypes) {
            if(itemType.equals(itemType1)) {
                return;
            }
        }
        itemTypes.add(itemType);
    }

    public static ItemType getItemType(String name) {
        for(ItemType itemType : itemTypes) {
            if(itemType.getName().equals(name))
                return itemType;
        }
        return null;
    }
}
