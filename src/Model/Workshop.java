package Model;

import java.util.ArrayList;

public class Workshop {
    private int locataion, level;
    private ArrayList<ItemType> inputs;
    private ItemType product;
    private int upgradeCost, turnsToProduct, productTime;
    private Map map;
    private boolean working;
    private String name;

    public void Workshop(int locataion, ArrayList<ItemType> inputs, ItemType product, int upgradeCost, int productTime, Map map, String name) {
        this.locataion = locataion;
        this.inputs = inputs;
        this.product = product;
        this.upgradeCost = upgradeCost;
        this.map = map;
        this.level = 0;
        this.productTime = productTime;
        this.name = name;
        working = false;
    }

    public boolean isWorking() {
        return working;
    }

    public int getLocataion() {
        return locataion;
    }

    public ArrayList<ItemType> getInputs() {
        return inputs;
    }

    public ItemType getProduct() {
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

    public void start() {
        if(working) {
            throw new RuntimeException("Workshop is busy.");
        }
        int i = 0;
        for(; i < inputs.size(); i++) {
            if(!map.getWarehouse().getItemTypes().contains(inputs.get(i)))
                break;
            map.getWarehouse().remove(inputs.get(i));
        }
        if(i < inputs.size()) {
            for(int j = 0; j < i; j++)
                map.getWarehouse().add(inputs.get(j));
            throw new RuntimeException("Inputs are not in storage.");
        }
        working = true;
        turnsToProduct = productTime;
    }

    public void nextTurn() {
        if(!working)
            return;
        turnsToProduct--;
        if(turnsToProduct == 0) {
            working = false;
            map.getWarehouse().add(product);
        }
    }

    public String getName() {
        return name;
    }
}
