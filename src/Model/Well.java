package Model;

import Values.Values;

public class Well {
    private int waterValue;
    final static int UPGRADE_COST = Values.WELL_FILL_COST, CAPACITY = Values.WELL_CAPACITY;

    public void Well() {
        waterValue = CAPACITY;
    }

    public void fill() {
        waterValue = CAPACITY;
    }

    public int getWaterValue() {
        return waterValue;
    }
}
