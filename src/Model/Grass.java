package Model;

import Values.Values;

public class Grass extends Entity{
    private int turnsToDie, value;
    private Cell cell;

    public Grass(Cell cell) {
        this.cell = cell;
        turnsToDie = Values.GRASS_LIFE_TIME;
        value = Values.GRASS_VALUE;
    }

    private void kill() {
        cell.delete(this);
    }

    public void nextTurn() {
        turnsToDie--;
        if(turnsToDie == 0) {
            kill();
        }
    }

    public void eat() {
        value--;
        if(value == 0) {
            kill();
        }
    }
}
