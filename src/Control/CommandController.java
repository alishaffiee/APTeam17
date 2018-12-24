package Control;

import Interfaces.Upgradable;
import Model.Animal.*;
import Model.Cell;
import Model.Grass;
import Model.Warehouse;
import Model.Workshop;
import Values.Values;

public class CommandController {
    public static CommandController commandController = new CommandController(Game.game);
    private Game game;

    private CommandController(Game game) {
        this.game = game;
    }

    public void buyAnimal(String name) {
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
        Cell cell = game.getCurrnetLevel().getMap().getCell(x, y);
        try {
            cell.pickup();
            System.out.println("Pickup was successful.");
        } catch (Exception e) {
            System.out.println("Not enough storage.");
        }
    }

    public void cage(int x, int y) {
        Cell cell = game.getCurrnetLevel().getMap().getCell(x, y);
        try {
            cell.cage();
            System.out.println("Cage was successful.");
        } catch (Exception e) {
            System.out.println("Not enough storage.");
        }
    }

    public void plant(int x, int y) {
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
        try {
            game.getCurrnetLevel().getMap().fillWell();
            System.out.println("filling well was successful.");
        } catch (Exception e) {
            System.out.println("Not enough money");
        }
    }

    public void startWorkshop(String name) {
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
        Upgradable upgradable = null;
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

    public void load(String path) {

    }

    public void run(String mapName) {

    }

    public void saveGame(String path) {

    }

    public void loadGame(String path) {

    }

    public void print(String name) {

    }

    public void nextTurn(int count) {
        for (int i = 0; i < count; i++)
            game.getCurrnetLevel().getMap().nextTurn();
    }
}
