package Control;

import Model.ItemType;
import Model.Level;
import Values.Values;

import java.util.ArrayList;

public class Game {
    public static Game game = new Game();
    private ArrayList<Level> levels;

    private Game() {
        ItemType.addItemType(
                new ItemType("Egg", Values.EGG_VOLUME, Values.EGG_BUY_COST, Values.EGG_SELL_COST, Values.EGG_LIFE_TIME)
        );
        ItemType.addItemType(
                new ItemType("Milk", Values.MILK_VOLUME, Values.MILK_BUY_COST, Values.MILK_SELL_COST, Values.MILK_LIFE_TIME)
        );
        ItemType.addItemType(
                new ItemType("Wool", Values.WOOL_VOLUME, Values.WOOL_BUY_COST, Values.WOOL_SELL_COST, Values.WOOL_LIFE_TIME)
        );
        ItemType.addItemType(
                new ItemType("Lion", Values.LION_VOLUME, Values.LION_BUY_COST, Values.LION_SELL_COST, Values.LION_LIFE_TIME)
        );
        ItemType.addItemType(
                new ItemType("Bear", Values.BEAR_VOLUME, Values.BEAR_BUY_COST, Values.BEAR_SELL_COST, Values.BEAR_LIFE_TIME)
        );
    }

    void addLevel(Level level) {
        levels.add(level);
    }

}
