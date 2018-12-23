package Model.Animal;

import Model.Cell;
import Model.Entity;
import Model.Map;

import java.util.Random;

abstract public class Animal extends Entity {
    protected Map map;
    protected Cell cell;
    public Animal(Map map) {
        this.map = map;
        this.cell = map.getRandomCell();
    }

    protected int sign(int x) {
        return (x > 0 ? 1 : -1);
    }

    public abstract Cell nextMove();

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }

    public Cell randomMove(Cell cell) {
        while (true) {
            Random random = new Random();
            int x = random.nextInt(5);
            int[] dx = {0, -1, 1, 0, 0};
            int[] dy = {0, 0, 0, 1, -1};
            Cell cell1 = map.getCell(cell.getPositionX() + dx[x], cell.getPositionY() + dy[x]);
            if(cell1 != null)
                return cell1;
        }
    }
}
