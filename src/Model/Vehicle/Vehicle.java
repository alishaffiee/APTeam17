package Model.Vehicle;

import Model.Map;

abstract public class Vehicle {
    protected int capacity;
    protected int timeToComeBack;
    protected Map map;

    public Vehicle(Map map) {
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
}
