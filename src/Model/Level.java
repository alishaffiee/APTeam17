package Model;

import Model.Animal.Bear;
import Model.Animal.Lion;
import Values.Values;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

public class Level implements Serializable {
    private int goalMoney, levelNumber;
    private HashMap<ItemType, Integer> goals;
    private HashMap<String, Integer> goalAnimals;
    private Map map;

    public Level(HashMap<ItemType, Integer> goals, HashMap<String, Integer> goalAnimals, int levelNumber, int goalMoney, int initialMoney) {
        this.goalMoney = goalMoney;
        this.levelNumber = levelNumber;
        this.goalAnimals = goalAnimals;
        this.goals = goals;
        map = new Map(Values.MAP_HEIGHT, Values.MAP_WIDTH, initialMoney);
    }

    public boolean isCompleted() {
        if (map.getMoney() < goalMoney)
            return false;

        for (ItemType itemType : goals.keySet()) {
            if (map.count(itemType) < goals.get(itemType))
                return false;
        }

        for (String name : goalAnimals.keySet()) {
            if (map.countAnimal(name) < goalAnimals.get(name))
                return false;
        }

        return true;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public Map getMap() {
        return map;
    }

    public HashMap<ItemType, Integer> getGoals() {
        return goals;
    }

    public void nextTurn() {
        Random random = new Random();

        if (random.nextInt(400) < 2) {
            Bear bear = new Bear(map);
            bear.getCell().addEntity(bear);
            bear.start();
            bear.setKillAnimation();
        }
        if (random.nextInt(400) < 2) {
            Lion lion = new Lion(map);
            lion.getCell().addEntity(lion);
            lion.start();
            lion.setKillAnimation();
        }

        map.nextTurn();
    }

    public HashMap<String, Integer> getGoalAnimals() {
        return goalAnimals;
    }
}
