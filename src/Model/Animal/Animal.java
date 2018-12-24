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

    public abstract Cell nextMove();

    public abstract int getSpeed();

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }

    protected Cell randomMove(Cell cell) {
        while (true) {
            Random random = new Random();
            int x = random.nextInt(5);
            int[] dx = {0, -1, 1, 0, 0};
            int[] dy = {0, 0, 0, 1, -1};
            Cell cell1 = map.getCell(cell.getPositionX() + dx[x] * getSpeed(), cell.getPositionY() + dy[x] * getSpeed());
            if (cell1 != null)
                return cell1;
        }
    }

    protected Cell move(Cell start, Cell end, int speed) {
        int dx = start.getPositionX() - end.getPositionX();
        int dy = start.getPositionY() - end.getPositionY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        int x = start.getPositionX() + (int) Math.min(1.0, speed / distance) * dx;
        int y = start.getPositionY() + (int) Math.min(1.0, speed / distance) * dy;
        return map.getCell(x, y);
    }
}