package Control;

import Interfaces.Printable;
import Interfaces.Upgradable;
import Model.Animal.*;
import Model.*;
import Values.Values;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CommandController {
    public static CommandController commandController = new CommandController(Game.game);
    private Game game;

    private CommandController(Game game) {
        this.game = game;
    }

    public void buyAnimal(String name) {
        if (game.getCurrentLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        Animal animal;
        int cost;
        switch (name) {
            case "Chicken": {
                animal = new Chicken(game.getCurrentLevel().getMap());
                cost = Values.CHICKEN_COST;
                break;
            }
            case "Sheep": {
                animal = new Sheep(game.getCurrentLevel().getMap());
                cost = Values.SHEEP_COST;
                break;
            }
            case "Turkey": {
                animal = new Turkey(game.getCurrentLevel().getMap());
                cost = Values.TURKEY_COST;
                break;
            }
            case "Cow": {
                animal = new Cow(game.getCurrentLevel().getMap());
                cost = Values.COW_COST;
                break;
            }
            case "Cat": {
                animal = new Cat(game.getCurrentLevel().getMap());
                cost = Values.CAT_COST;
                break;
            }
            case "Dog": {
                animal = new Dog(game.getCurrentLevel().getMap());
                cost = Values.DOG_COST;
                break;
            }
            default: {
                System.out.println("Animal not found.");
                return;
            }
        }
        if (cost > game.getCurrentLevel().getMap().getMoney()) {
            System.out.println("Not enough money.");
            return;
        }
        game.getCurrentLevel().getMap().addMoney(-cost);
        animal.getCell().addEntity(animal);
        animal.start();

        System.out.println("Buying animal was successful.");
    }

    public void pickup(int x, int y) {
        if (game.getCurrentLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        Cell cell = game.getCurrentLevel().getMap().getCell(x, y);
        try {
            cell.pickup();
            System.out.println("Pickup was successful.");
        } catch (Exception e) {
            System.out.println("Not enough storage.");
        }
    }

    public void cage(int x, int y) {
        if (game.getCurrentLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        Cell cell = game.getCurrentLevel().getMap().getCell(x, y);
        try {
            cell.cage();
            System.out.println("Cage was successful.");
        } catch (Exception e) {
            System.out.println("Not enough storage.");
        }
    }

    public void plant(int x, int y) {
        if (game.getCurrentLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        if (game.getCurrentLevel().getMap().getWell().getWaterValue() == 0) {
            System.out.println("not enough water.");
            return;
        }
        for (int dx = -3; dx < 4; dx++) {
            for (int dy = -3; dy < 4; dy++) {
                Cell cell = game.getCurrentLevel().getMap().getCell(x + dx, y + dy);
                if (cell == null)
                    continue;
                if (cell.hasGrass())
                    cell.getEntities().remove(cell.getGrass());
                cell.addEntity(new Grass(cell));
            }
        }
        game.getCurrentLevel().getMap().getWell().decreaseWater();
        System.out.println("plant was successful.");
    }

    public void well() {
        if (game.getCurrentLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        try {
            game.getCurrentLevel().getMap().fillWell();
            System.out.println("filling well was successful.");
        } catch (Exception e) {
            System.out.println("Not enough money");
        }
    }

    public void startWorkshop(String name) {
        if (game.getCurrentLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        Workshop workshop = game.getCurrentLevel().getMap().getWorkshopByName(name);
        if (workshop == null) {
            System.out.println("Workshop not found.");
            return;
        }
        try {
            workshop.start();
            System.out.println("Stating workshop was successful.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void upgrade(String name) {
        if (game.getCurrentLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        Upgradable upgradable;
        switch (name) {
            case "warehouse": {
                upgradable = game.getCurrentLevel().getMap().getWarehouse();
                break;
            }
            case "well": {
                upgradable = game.getCurrentLevel().getMap().getWell();
                break;
            }
            case "truck": {
                upgradable = game.getCurrentLevel().getMap().getTruck();
                break;
            }
            case "helicopter": {
                upgradable = game.getCurrentLevel().getMap().getHelicopter();
                break;
            }
            default: {
                upgradable = Workshop.getWorkshopByName(name);
            }
        }

        if (upgradable == null) {
            System.out.println("invalid argument.");
            return;
        }

        if (!upgradable.canUpgrade()) {
            System.out.println("You cannot upgrade this object.");
            return;
        }

        if (game.getCurrentLevel().getMap().getMoney() < upgradable.getUpgradeCost()) {
            System.out.println("Not enough money.");
            return;
        }
        game.getCurrentLevel().getMap().addMoney(-upgradable.getUpgradeCost());
        try {
            upgradable.upgrade();
            System.out.println("Upgrading was successful.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }

    String read(String path) {
        String ans = "";
        try {
            FileReader inputStream = new FileReader(path);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                ans = ans + scanner.nextLine();
            }
            inputStream.close();
        } catch (IOException e) {
            System.out.println("File not found.");
        }
        return ans;
    }

    public void loadWorkshop(String name) {
        String json = read("./Data/Workshops/" + name + ".json");
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        JsonArray inputs = jsonObject.get("inputs").getAsJsonArray();
        ArrayList<ItemType> itemTypes = new ArrayList<>();
        for (JsonElement jsonElement : inputs) {
            itemTypes.add(ItemType.getItemType(jsonElement.getAsString()));
        }

        ItemType output = ItemType.getItemType(jsonObject.get("output").getAsString());

        int product_time = jsonObject.get("production_time").getAsInt();
        int time_decrease_step = jsonObject.get("time_decrease_step").getAsInt();
        int upgrade_cost = jsonObject.get("upgrade_cost").getAsInt();
        int cost_increase_step = jsonObject.get("cost_increase_step").getAsInt();

        Workshop workshop = new Workshop(null, itemTypes, output,
                upgrade_cost, product_time, name, cost_increase_step, time_decrease_step);

        Workshop.addWorkshop(workshop);
    }

    public void run(String mapName) {
        String json = read("./Data/Levels/" + mapName + ".json");
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        int levelNumber = Integer.valueOf(mapName.substring(5));
        JsonArray jsonGoals = jsonObject.get("goals").getAsJsonArray();
        HashMap<ItemType, Integer> goals = new HashMap<>();
        HashMap<String, Integer> goalAnimals = new HashMap<>();

        for (JsonElement element : jsonGoals) {
            String name = element.getAsJsonObject().get("item").getAsString();
            ItemType item = ItemType.getItemType(name);
            int count = element.getAsJsonObject().get("count").getAsInt();
            if (item != null)
                goals.put(item, count);
            else
                goalAnimals.put(name, count);
        }

        int initialMoney = jsonObject.get("initial_money").getAsInt();
        int goalMoney = jsonObject.get("goal_money").getAsInt();

        Level level = new Level(goals, goalAnimals, levelNumber, goalMoney, initialMoney);

        game.addLevel(level);
        game.startLevel(level);

        System.out.println("Level successfully started.");
    }

    public void addWorkshop(String name) {
        if (game.getCurrentLevel() == null) {
            System.out.println("level not found.");
            return;
        }

        Workshop workshop = Workshop.getWorkshopByName(name);

        if (workshop == null) {
            System.out.println("Workshop not found.");
            return;
        }

        game.getCurrentLevel().getMap().addWorkshop(new Workshop(workshop, game.getCurrentLevel().getMap()));

    }

    public void saveGame(String name) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./Data/" + name + ".out"));
            objectOutputStream.writeObject(game.getCurrentLevel());
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("there is an error in saving game.");
            return;
        }
        System.out.println("saving game was successful.");
    }

    public void loadGame(String name) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("./Data/" + name + ".out"));
            Level level = (Level) objectInputStream.readObject();
            game.startLevel(level);
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("there is an error in loading game.");
            return;
        }
        System.out.println("loading game was successful.");
    }

    public void print(String name) {
        if (game.getCurrentLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        if (name.equals("info")) {
            System.out.println("Money = " + game.getCurrentLevel().getMap().getMoney());
            System.out.println("Time = " + game.getCurrentLevel().getMap().getTime());
            System.out.println("Goals : ");
            for (ItemType itemType : game.getCurrentLevel().getGoals().keySet()) {
                System.out.println(" + " + itemType.getName() + " : " + game.getCurrentLevel().getGoals().get(itemType));
            }
            for (String animalName : game.getCurrentLevel().getGoalAnimals().keySet()) {
                System.out.println(" + " + animalName + " : " + game.getCurrentLevel().getGoalAnimals().get(animalName));
            }
            System.out.println("Is compeleted : " + game.getCurrentLevel().isCompleted());
            return;
        }
        Printable printable;
        switch (name) {
            case "map": {
                printable = game.getCurrentLevel().getMap();
                break;
            }
            case "warehouse": {
                printable = game.getCurrentLevel().getMap().getWarehouse();
                break;
            }
            case "well": {
                printable = game.getCurrentLevel().getMap().getWell();
                break;
            }
            case "truck": {
                printable = game.getCurrentLevel().getMap().getTruck();
                break;
            }
            case "helicopter": {
                printable = game.getCurrentLevel().getMap().getHelicopter();
                break;
            }
            default: {
                printable = game.getCurrentLevel().getMap().getWorkshopByName(name);
                break;
            }
        }
        if (printable == null) {
            System.out.println("Object not found.");
            return;
        }
        printable.print();
    }

    public void nextTurn(int count) {
        if (game.getCurrentLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        for (int i = 0; i < count; i++)
            game.getCurrentLevel().nextTurn();

        if (game.getCurrentLevel().isCompleted()) {
            System.out.println("Level completed!");
            game.startLevel(null);
        }

        System.out.println("passing time was successful.");
    }

    public Game getGame() {
        return game;
    }
}
