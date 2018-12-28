package Model;

import Interfaces.Printable;
import Interfaces.Upgradable;
import Values.Values;

import java.util.ArrayList;

public class Workshop implements Upgradable, Printable {
    private static ArrayList<Workshop> workshops = new ArrayList<>();
    private int level, upgradeStep, timeStep;
    private ArrayList<ItemType> inputs;
    private ItemType product;
    private int upgradeCost, turnsToProduct, productTime;
    private Map map;
    private boolean working;
    private String name;

    public Workshop(Map map, ArrayList<ItemType> inputs, ItemType product, int upgradeCost, int productTime, String name, int upgradeStep, int timeStep) {
        this.inputs = inputs;
        this.product = product;
        this.upgradeCost = upgradeCost;
        this.map = map;
        this.level = 0;
        this.productTime = productTime;
        this.name = name;
        this.upgradeStep = upgradeStep;
        this.timeStep = timeStep;
        working = false;
    }

    public Workshop(Workshop workshop, Map map) {
        this.inputs = workshop.inputs;
        this.product = workshop.product;
        this.upgradeCost = workshop.upgradeCost;
        this.map = map;
        this.level = 0;
        this.productTime = workshop.productTime;
        this.name = workshop.name;
        this.upgradeStep = workshop.upgradeStep;
        this.timeStep = workshop.timeStep;
        working = false;
    }

    public static void addWorkshop(Workshop workshop) {
        if (getWorkshopByName(workshop.name) == null)
            workshops.add(workshop);
    }

    public static Workshop getWorkshopByName(String name) {
        Workshop ans = null;
        for (Workshop workshop : workshops) {
            if (workshop.name.equals(name))
                ans = workshop;
        }
        return ans;
    }

    public boolean isWorking() {
        return working;
    }

    public ArrayList<ItemType> getInputs() {
        ArrayList<ItemType> ans = new ArrayList<>();
        for (int i = 0; i < level + 1; i++)
            ans.addAll(inputs);
        return ans;
    }

    public ItemType getProduct() {
        return product;
    }

    public int getLevel() {
        return level;
    }

    public void start() {
        if (working) {
            throw new RuntimeException("Workshop is busy.");
        }
        int i = 0;
        for (; i < inputs.size(); i++) {
            if (!map.getWarehouse().getItemTypes().contains(inputs.get(i)))
                break;
            map.getWarehouse().remove(inputs.get(i));
        }
        if (i < inputs.size()) {
            for (int j = 0; j < i; j++)
                map.getWarehouse().add(inputs.get(j));
            throw new RuntimeException("Inputs are not in storage.");
        }
        working = true;
        turnsToProduct = getProductTime();
    }

    public void nextTurn() {
        if (!working)
            return;
        turnsToProduct--;
        if (turnsToProduct == 0) {
            working = false;
            map.getWarehouse().add(product);
        }
    }

    public String getName() {
        return name;
    }

    public int getUpgradeCost() {
        if(level == Values.WORK_SHOP_MAX_LEVEL || working)
            return 0;
        return level * upgradeStep + upgradeCost;
    }

    public int getProductTime() {
        return productTime - timeStep * level;
    }

    public void upgrade() {
        if (level == Values.WORK_SHOP_MAX_LEVEL)
            throw new RuntimeException("Workshop is already at max level.");
        if (working)
            throw new RuntimeException("Workshop is busy now");
        level++;
    }

    public boolean canUpgrade() {
        return level < Values.WORK_SHOP_MAX_LEVEL && !working;
    }

    public void print() {
        System.out.println("Inputs = ");
        for(ItemType itemType : inputs) {
            System.out.print(itemType.getName() + " ");
        }
        System.out.println();
        System.out.println("Output = " + product.getName());
        System.out.println("Is working = " + isWorking());
        if(isWorking())
            System.out.println("Turns to product = " + turnsToProduct);
    }
}
