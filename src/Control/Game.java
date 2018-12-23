package Control;

import Model.ItemType;
import Model.Level;
import Values.ItemsCosts;

import java.util.ArrayList;

public class Game {
    public static Game game = new Game();
    private ArrayList<Level> levels;
    private Level currnetLevel;

    private Game() {
        for(String name : ItemsCosts.names) {
            ItemType.addItemType(
                    new ItemType(name, ItemsCosts.getDepotSize(name), ItemsCosts.getSaleCost(name), ItemsCosts.getBuyCost(name), 10)
            );
        }
    }

    void addLevel(Level level) {
        levels.add(level);
    }

    public Level getLevel(int id) {
        for(Level level : levels) {
            if (level.getLevelNumber() == id)
                return level;
        }
        return null;
    }

    public void startLevel(Level level) {
        currnetLevel = level;
    }

    public Level getCurrnetLevel() {
        return currnetLevel;
    }
}
