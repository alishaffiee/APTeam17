package Model;

public class Item extends Entity {
    private ItemType itemType;
    private int turnsToDie;
    private Cell cell;

    public Item(ItemType itemType, Cell cell) {
        this.itemType = itemType;
        this.cell = cell;
        turnsToDie = itemType.getLifeTime();
    }

    public void nextTurn() {
        turnsToDie--;
        if(turnsToDie == 0) {
            cell.getEntities().remove(this);
        }
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getTurnsToDie() {
        return turnsToDie;
    }

    public int getVolume() {
        return itemType.getVolume();
    }
}
