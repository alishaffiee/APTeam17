package Control;

import Model.ItemType;
import Model.Level;
import Values.ItemsCosts;

import java.util.ArrayList;

public class Game {
    public static Game game = new Game();
    private ArrayList<Level> levels = new ArrayList<>();
    private Level currentLevel;

    private Game() {

    }

    void addLevel(Level level) {
        if (getLevel(level.getLevelNumber()) == null)
            levels.add(level);
    }

    public Level getLevel(int id) {
        for (Level level : levels) {
            if (level.getLevelNumber() == id)
                return level;
        }
        return null;
    }

    public void startLevel(Level level) {
        currentLevel = level;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
