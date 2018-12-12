package Model;

public class ItemType {
    private String name;
    private int volume;

    public void ItemType(String name, int volume) {
        this.volume = volume;
        this.name = name;
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
