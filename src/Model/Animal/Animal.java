package Model.Animal;

import Control.CommandController;
import Model.Cell;
import Model.Entity;
import Model.Map;
import Model.Well;
import View.GameScene;
import View.MoveAnimal;
import javafx.animation.AnimationTimer;

import java.io.Serializable;

abstract public class Animal extends Entity implements Serializable {
    protected Map map;
    protected Cell cell;
    protected String name;
    protected MoveAnimal moveAnimal;
    private boolean killed;

    public Animal(Map map, String name) {
        this.map = map;
        this.name = name;
        this.cell = map.getRandomCell();
        killed = false;
    }

    public boolean isKilled() {
        return killed;
    }

    public void kill() {
        killed = true;
        moveAnimal.kill();
        new AnimationTimer() {
            long prv = -1;
            @Override
            public void handle(long now) {
                if(prv == -1)
                {
                    prv = now;
                    return;
                }
                if(now - prv < 6e9)
                    return;
                GameScene.root.getChildren().remove(moveAnimal.getDeath());
                stop();
            }
        }.start();
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

    protected Cell randomMove(int speed) {
        int x = this.cell.getPositionX();
        int y = this.cell.getPositionY();
        int direction = moveAnimal.getDirection();
        int p = x + MoveAnimal.dx[direction] * speed;
        int q = y + MoveAnimal.dy[direction] * speed;
        if(map.getCell(p, q) != null) {
            return map.getCell(p, q);
        }
        moveAnimal.setDirection((moveAnimal.getDirection() + 1) % 4);
        direction = moveAnimal.getDirection();
        return map.getCell(x + MoveAnimal.dx[direction], y + MoveAnimal.dy[direction]);
    }

    protected Cell move(Cell start, Cell end, int speed) {
        int direction = moveAnimal.getDirection();
        if(Math.abs(start.getPositionX() - end.getPositionX()) > speed && (Math.abs(start.getPositionY() - end.getPositionY()) > speed || direction % 2 == 0)){
            if(start.getPositionX() > end.getPositionX()){
                moveAnimal.setDirection(0);
                return map.getCell(Math.max(start.getPositionX() - speed, 0), start.getPositionY());
            }
            else{
                moveAnimal.setDirection(2);
                return map.getCell(Math.min(start.getPositionX() + speed, map.getWidth() - 1), start.getPositionY());
            }
        }
        else {
            if (start.getPositionY() > end.getPositionY()) {
                moveAnimal.setDirection(1);
                return map.getCell(start.getPositionX(), Math.max(0, start.getPositionY() - speed));
            } else {
                moveAnimal.setDirection(3);
                return map.getCell(start.getPositionX(), Math.min(start.getPositionY() + speed, map.getHeight() - 1));
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