package Model;

import Consts.Interfaces.Storageble;

public class Item extends Entity implements Storageble {
    private ItemType itemType;
    private int aliveTurn;

    public Item(ItemType itemType) {
        this.itemType = itemType;
    }

    public void nextTurn() {

    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getAliveTurn() {
        return aliveTurn;
    }

    public int getVolume() {
        return itemType.getVolume();
    }
}
