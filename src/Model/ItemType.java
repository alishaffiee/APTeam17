package Model;

public class ItemType {
    private String name;
    private int volume, cost;

    public void ItemType(String name, int volume, int cost) {
        this.volume = volume;
        this.name = name;
        this.cost = cost;
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
}
