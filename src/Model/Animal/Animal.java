package Model.Animal;

import Model.Cell;
import Model.Entity;
import Model.Map;

abstract public class Animal extends Entity {
    protected Map map;
    protected Cell cell;
    public Animal(Map map) {
        this.map = map;
        this.cell = map.getRandomCell();
    }

    public abstract Cell nextMove();

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }
}
