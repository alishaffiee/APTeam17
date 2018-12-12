package Model;

import java.util.ArrayList;

public class Workshop {
    private int locataion, level;
    private ArrayList<Entity> inputs;
    private Entity product;
    private int upgradeCost;

    public void Workshop(int locataion, ArrayList<Entity> inputs, Entity product, int upgradeCost) {
        this.locataion = locataion;
        this.inputs = inputs;
        this.product = product;
        this.upgradeCost = upgradeCost;
        this.level = 0;
    }

    public int getLocataion() {
        return locataion;
    }

    public ArrayList<Entity> getInputs() {
        return inputs;
    }

    public Entity getProduct() {
        return product;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }
}
