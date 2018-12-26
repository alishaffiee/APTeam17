package Control;

import Interfaces.Printable;
import Interfaces.Upgradable;
import Model.Animal.*;
import Model.Cell;
import Model.Grass;
import Model.ItemType;
import Model.Workshop;
import Values.Values;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandController {
    public static CommandController commandController = new CommandController(Game.game);
    private Game game;

    private CommandController(Game game) {
        this.game = game;
    }

    public void buyAnimal(String name) {
        if (game.getCurrnetLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        Animal animal;
        int cost;
        switch (name) {
            case "Chicken": {
                animal = new Chicken(game.getCurrnetLevel().getMap());
                cost = Values.CHICKEN_COST;
                break;
            }
            case "Sheep": {
                animal = new Sheep(game.getCurrnetLevel().getMap());
                cost = Values.SHEEP_COST;
                break;
            }
            case "Turkey": {
                animal = new Turkey(game.getCurrnetLevel().getMap());
                cost = Values.TURKEY_COST;
                break;
            }
            case "Cow": {
                animal = new Cow(game.getCurrnetLevel().getMap());
                cost = Values.COW_COST;
                break;
            }
            case "Cat": {
                animal = new Cat(game.getCurrnetLevel().getMap());
                cost = Values.CAT_COST;
                break;
            }
            case "Dog": {
                animal = new Dog(game.getCurrnetLevel().getMap());
                cost = Values.DOG_COST;
                break;
            }
            default: {
                System.out.println("Animal not found.");
                return;
            }
        }
        if (cost > game.getCurrnetLevel().getMap().getMoney()) {
            System.out.println("Not enough money.");
            return;
        }
        game.getCurrnetLevel().getMap().addMoney(-cost);
        animal.getCell().addEntity(animal);
    }

    public void pickup(int x, int y) {
        if (game.getCurrnetLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        Cell cell = game.getCurrnetLevel().getMap().getCell(x, y);
        try {
            cell.pickup();
            System.out.println("Pickup was successful.");
        } catch (Exception e) {
            System.out.println("Not enough storage.");
        }
    }

    public void cage(int x, int y) {
        if (game.getCurrnetLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        Cell cell = game.getCurrnetLevel().getMap().getCell(x, y);
        try {
            cell.cage();
            System.out.println("Cage was successful.");
        } catch (Exception e) {
            System.out.println("Not enough storage.");
        }
    }

    public void plant(int x, int y) {
        if (game.getCurrnetLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        if (game.getCurrnetLevel().getMap().getWell().getWaterValue() == 0) {
            System.out.println("not enough water.");
            return;
        }
        for (int dx = -3; dx < 4; dx++) {
            for (int dy = -3; dy < 4; dy++) {
                Cell cell = game.getCurrnetLevel().getMap().getCell(x + dx, y + dy);
                if (cell == null)
                    continue;
                if (cell.hasGrass())
                    cell.getEntities().remove(cell.getGrass());
                cell.addEntity(new Grass(cell));
            }
        }
        game.getCurrnetLevel().getMap().getWell().decreaseWater();
        System.out.println("plant was successful.");
    }

    public void well() {
        if (game.getCurrnetLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        try {
            game.getCurrnetLevel().getMap().fillWell();
            System.out.println("filling well was successful.");
        } catch (Exception e) {
            System.out.println("Not enough money");
        }
    }

    public void startWorkshop(String name) {
        if (game.getCurrnetLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        Workshop workshop = game.getCurrnetLevel().getMap().getWorkshopByName(name);
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
        if (game.getCurrnetLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        Upgradable upgradable;
        switch (name) {
            case "warehouse": {
                upgradable = game.getCurrnetLevel().getMap().getWarehouse();
                break;
            }
            case "well": {
                upgradable = game.getCurrnetLevel().getMap().getWell();
                break;
            }
            case "truck": {
                upgradable = game.getCurrnetLevel().getMap().getTruck();
                break;
            }
            case "helicopter": {
                upgradable = game.getCurrnetLevel().getMap().getHelicopter();
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

        if (game.getCurrnetLevel().getMap().getMoney() < upgradable.getUpgradeCost()) {
            System.out.println("Not enough money.");
            return;
        }
        game.getCurrnetLevel().getMap().addMoney(-upgradable.getUpgradeCost());
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
        } catch (IOException e) {
            System.out.println("File not found.");
        }
        return ans;
    }

    public void loadWorkshop(String name) {
        String json = read(".\\Data\\" + name + ".json");
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

    }

    public void saveGame(String path) {

    }

    public void loadGame(String path) {

    }

    public void print(String name) {
        if (game.getCurrnetLevel() == null) {
            System.out.println("level not found.");
            return;
        }
        if (name.equals("info")) {
            System.out.println("Money = " + game.getCurrnetLevel().getMap().getMoney());
            System.out.println("Time = " + game.getCurrnetLevel().getMap().getTime());
            System.out.println("Goals : ");
            for (ItemType itemType : game.getCurrnetLevel().getGoals().keySet()) {
                System.out.println(itemType.getName() + " : " + game.getCurrnetLevel().getGoals().get(itemType));
            }
            System.out.println("Is compeleted : " + game.getCurrnetLevel().isCompeleted());
            return;
        }
        Printable printable;
        switch (name) {
            case "map": {
                printable = game.getCurrnetLevel().getMap();
                break;
            }
            case "warehouse": {
                printable = game.getCurrnetLevel().getMap().getWarehouse();
                break;
            }
            case "well": {
                printable = game.getCurrnetLevel().getMap().getWell();
                break;
            }
            case "truck": {
                printable = game.getCurrnetLevel().getMap().getTruck();
                break;
            }
            case "helicopter": {
                printable = game.getCurrnetLevel().getMap().getHelicopter();
                break;
            }
            default: {
                printable = game.getCurrnetLevel().getMap().getWorkshopByName(name);
                break;
            }
        }
        if(printable == null) {
            System.out.println("Object not found.");
            return;
        }
        printable.print();
    }

    public void nextTurn(int count) {
        for (int i = 0; i < count; i++)
            game.getCurrnetLevel().getMap().nextTurn();
    }
}
