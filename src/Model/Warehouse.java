package Model;

import src.Interfaces.Storageble;

import java.util.ArrayList;

public class Warehouse {
    ArrayList<Storageble> storagebles;
    private int capacity;

    public Warehouse(int capacity) {
        storagebles = new ArrayList<>();
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFreeCapacity() {
        int ans = capacity;
        for(Storageble storageble : storagebles) {
            ans -= storageble.getVolume();
        }
        return ans;
    }

    void add(Storageble storageble) {
        storagebles.add(storageble);
    }
}
