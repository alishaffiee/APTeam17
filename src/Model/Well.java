package Model;

import Values.Values;

public class Well {
    private int waterValue;
    final static int FILL_COST = Values.WELL_FILL_COST, CAPACITY = Values.WELL_CAPACITY;
    private Map map;

    public Well(Map map) {
        this.map = map;
    }

    public void Well() {
        waterValue = CAPACITY;
    }

    public void fill() {
        waterValue = CAPACITY;
    }

    public int getWaterValue() {
        return waterValue;
    }

    public void decreaseWater() {
        waterValue--;
    }
}
