package Model;

import Values.Values;

import java.util.HashMap;

public class Level {
    private int goalMoney, levelNumber;
    private HashMap<ItemType, Integer> goals;
    private Map map;

    public Level(HashMap<ItemType, Integer> goals, int levelNumber, int goalMoney) {
        this.goalMoney = goalMoney;
        this.levelNumber = levelNumber;
        this.goals = goals;
        map = new Map(Values.MAP_HEIGHT, Values.MAP_WIDTH, Values.INITIAL_MONEY);
    }

    public boolean isCompeleted() {
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
}
