package Values;


import java.util.Arrays;
import java.util.List;

public class ItemsCosts {
    public static List<String> names = Arrays.asList("Egg", "Wool", "Plume", "Milk", "Horn", "DriedEggs", "Cake", "FlouryCake",
            "Sewing", "Fabric", "CarnivalDress", "SourCream", "Curd", "Cheese", "ColoredPlume", "Adornment", "BrightHorn",
            "Intermediate", "Souvenir", "Flour", "CheeseFerment", "Varnish", "MegaPie", "SpruceGrizzly", "SpruceLion",
            "SpruceBrownBear", "SpruceJaguar", "SpruceWhiteBear", "CagedGrizzly", "CagedLion", "CagedBrownBear",
            "CagedJaguar", "CagedWhiteBear");
    private static List<Integer> depotSize = Arrays.asList(1, 5, 2, 10, 6, 4, 5, 6, 3, 6, 8, 8, 6, 5, 2, 4, 5, 4, 3, 2, 2, 3, 2, 25,
            25, 25, 25, 25, 20, 20, 20, 20, 20);

    private static List<Integer> buyCost = Arrays.asList(20, 200, 200, 2000, 2000, 100, 200, 400, 300, 400,
            1400, 3000, 4000, 5000, 300, 400, 3000, 4000, 5000, 20, 25, 25, 20000, 2500,
            2500, 2500, 2500, 2500, 80, 150, 100, 200, 100);

    private static List<Integer> saleCost = Arrays.asList(10, 100, 100, 1000, 1000, 50, 100, 200, 150,
            300, 1300, 1500, 2000, 2500, 150, 300, 1500, 2000, 25000, 10, 15, 15, 10000,
            7000, 7000, 7000, 7000, 7000, 80, 150, 100, 200, 100);

    public static int getDepotSize(String name) {
        return depotSize.get(getID(name));
    }

    public static int getBuyCost(String name) {
        return buyCost.get(getID(name));
    }

    public static int getSaleCost(String name) {
        return saleCost.get(getID(name));
    }

    private static int getID(String name) {
        int id = 0;
        int idx = 0;
        for (String s : names) {
            if (s.equals(name)) {
                id = idx;
            }
            idx++;
        }
        return id;
    }

}
