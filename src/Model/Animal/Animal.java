package Model.Animal;

import Model.Cell;
import Model.Entity;
import Model.Map;
import View.MoveAnimal;

import java.io.Serializable;
import java.util.Random;

abstract public class Animal extends Entity implements Serializable {
    protected Map map;
    protected Cell cell;
    protected String name;
    protected MoveAnimal moveAnimal;

    public Animal(Map map, String name) {
        this.map = map;
        this.name = name;
        this.cell = map.getRandomCell();
    }

    protected void start() {
        moveAnimal.setPositionX(this.cell.getPositionX() + 212);
        moveAnimal.setPositionY(this.cell.getPositionY() + 190);
        moveAnimal.start();
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
            int speed = random.nextInt(getSpeed());
            int x = random.nextInt(5);
            int[] dx = {0, -1, 1, 0, 0};
            int[] dy = {0, 0, 0, 1, -1};
            Cell cell1 = map.getCell(cell.getPositionX() + dx[x] * speed, cell.getPositionY() + dy[x] * speed);
            if (cell1 != null)
                return cell1;
        }
    }

    protected Cell move(Cell start, Cell end) {
        int dx = start.getPositionX() - end.getPositionX();
        int dy = start.getPositionY() - end.getPositionY();
        if(dx != 0){
            if(start.getPositionX() > end.getPositionX()){
                moveAnimal.setDirection(0);
                return map.getCell(start.getPositionX() - 1, start.getPositionY());
            }
            else{
                moveAnimal.setDirection(2);
                return map.getCell(start.getPositionX() + 1, start.getPositionY());
            }
        }
        else {
            if (start.getPositionY() > end.getPositionY()) {
                moveAnimal.setDirection(1);
                return map.getCell(start.getPositionX(), start.getPositionY() - 1);
            } else {
                moveAnimal.setDirection(3);
                return map.getCell(start.getPositionX(), start.getPositionY() + 1);
            }
        }
    }
    public String getName() {
        return name;
    }
}