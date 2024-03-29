package Model;

import Interfaces.Printable;
import Interfaces.Upgradable;
import Values.Values;

import java.io.Serializable;

public class Well implements Upgradable, Printable, Serializable {
    private int waterValue, level;
    private Map map;

    public Well(Map map) {
        this.map = map;
        waterValue = getCapacity();
    }

    public int getCapacity() {
        if (level == 0) return 5;
        if (level == 1) return 7;
        if (level == 2) return 10;
        if (level == 3) return 100;
        return -1;
    }

    public int getFillCost() {
        if (level == 0) return 19;
        if (level == 1) return 17;
        if (level == 2) return 15;
        if (level == 3) return 7;
        return -1;
    }

    public void fill() {
        waterValue = getCapacity();
    }

    public int getWaterValue() {
        return waterValue;
    }

    public void decreaseWater() {
        waterValue--;
    }

    public boolean canUpgrade() {
        return level < 3;
    }

    public void upgrade() {
        if (level == 3)
            throw new RuntimeException("Already at max level.");
        level++;
        waterValue = getCapacity();
    }

    public int getUpgradeCost() {
        return (level + 1) * 100;
    }

    public void print() {
        System.out.println("capacity = " + getCapacity());
        System.out.println("water value = " + waterValue);
        System.out.println("level = " + level);
    }

    public int getLevel() {
        return level;
    }
}
