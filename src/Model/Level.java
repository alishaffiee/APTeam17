package Model;

import Model.Animal.Bear;
import Model.Animal.Lion;
import Values.Values;
import com.sun.xml.internal.bind.v2.model.impl.BuiltinLeafInfoImpl;

import java.util.HashMap;
import java.util.Random;

public class Level {
    private int goalMoney, levelNumber;
    private HashMap<ItemType, Integer> goals;
    private Map map;

    public Level(HashMap<ItemType, Integer> goals, int levelNumber, int goalMoney, int initialMoney) {
        this.goalMoney = goalMoney;
        this.levelNumber = levelNumber;
        this.goals = goals;
        map = new Map(Values.MAP_HEIGHT, Values.MAP_WIDTH, initialMoney);
    }

    public boolean isCompleted() {
        if(map.getMoney() < goalMoney)
            return false;
        for(ItemType itemType : goals.keySet()) {
            if(map.count(itemType) < goals.get(itemType))
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
        while (random.nextInt(30) < 2) {
            Bear bear = new Bear(map);
            bear.getCell().addEntity(bear);
        }
        while (random.nextInt(30) < 2) {
            Lion lion = new Lion(map);
            lion.getCell().addEntity(lion);
        }
        map.nextTurn();
    }
}
