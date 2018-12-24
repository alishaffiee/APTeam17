package Model.Vehicle;

import Model.Map;

abstract public class Vehicle {
    protected int capacity, level;
    protected int timeToComeBack;
    protected Map map;

    public Vehicle(Map map) {
        level = 0;
        this.map = map;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isFree() {
        return timeToComeBack == 0;
    }

    public int getTimeToComeBack() {
        return timeToComeBack;
    }

    public void nextTurn() {
        if(timeToComeBack > 0)
            timeToComeBack--;
    }

    public abstract int travelTime();

    public void upgrade() {
        if(level == 3)
            throw new RuntimeException("Already at max level.");
        level++;
    }

    public int getUpgradeCost() {
        return (level + 1) * 120;
    }
}
