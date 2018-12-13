package Model;

import src.Interfaces.Storageble;

public class Item extends Entity implements Storageble {
    private ItemType itemType;
    private int aliveTurn;
    private Cell cell;

    public Item(ItemType itemType, Cell cell) {
        this.itemType = itemType;
        this.cell = cell;
        aliveTurn = itemType.getLifeTime();
    }

    public void nextTurn() {
        aliveTurn--;
        if(aliveTurn == 0) {
            cell.getEntities().remove(this);
        }
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
