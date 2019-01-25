package Model.Animal;

import Model.Cell;
import Model.Entity;
import Model.Map;
import View.GameScene;
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

    public void start() {
        moveAnimal.setPositionX(cell.getPositionX() + GameScene.leftBoundery);
        moveAnimal.setPositionY(cell.getPositionY() + GameScene.upBoundery);
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

    protected Cell randomMove() {
        int x = this.cell.getPositionX();
        int y = this.cell.getPositionY();
        int direction = moveAnimal.getDirection();
        int p = x + MoveAnimal.dx[direction];
        int q = y + MoveAnimal.dy[direction];
        if(map.getCell(p, q) != null) {
            return map.getCell(p, q);
        }
        moveAnimal.setDirection((moveAnimal.getDirection() + 1) % 4);
        direction = moveAnimal.getDirection();
        return map.getCell(x + MoveAnimal.dx[direction], y + MoveAnimal.dy[direction]);
    }

    protected Cell move(Cell start, Cell end) {
        if(start.getPositionX() != end.getPositionX()){
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

    public MoveAnimal getMoveAnimal() {
        return moveAnimal;
    }
}