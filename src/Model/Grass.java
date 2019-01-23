package Model;

import Control.CommandController;
import Values.Values;

import java.util.ArrayList;

public class Grass extends Entity {
    private int turnsToDie, value;
    private ArrayList<Cell> cells = new ArrayList<>();
    private final int offset = 4;
    public Grass(Cell cell) {
        Map map = CommandController.commandController.getGame().getCurrentLevel().getMap();
        int x = cell.getPositionX();
        int y = cell.getPositionY();
        for(int dx=-offset; dx<offset; dx++){
            for(int dy=-offset; dy<offset; dy++){
                Cell tmp = map.getCell(x + dx, y + dy);
                if(tmp != null) {
                    cells.add(tmp);
                    tmp.addEntity(this);
                }
            }
        }
        turnsToDie = Values.GRASS_LIFE_TIME;
        value = Values.GRASS_VALUE;
    }

    private void kill() {
        for(Cell cell : cells) {
            cell.delete(this);
        }
    }

    public void nextTurn() {
        turnsToDie--;
        if (turnsToDie == 0) {
            kill();
        }
    }

    public void eat() {
        value--;
        if (value == 0) {
            kill();
        }
    }
}
