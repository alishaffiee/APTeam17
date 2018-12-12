package Model;

import Consts.Consts;

public class Well {
    private int waterValue;
    final static int UPGRADE_COST = Consts.WELL_FILL_COST, CAPACITY = Consts.WELL_CAPACITY;

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
