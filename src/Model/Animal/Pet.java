package Model.Animal;

import Model.*;

public class Pet extends Animal {
    private ItemType prouduction;
    private int turnsToDie, turnsToProduct, productTime, health, speed, hungrySpeed;

    public Pet(Map map, ItemType prouduction, int productTime, int health, int speed, int hungrySpeed) {
        super(map);
        this.prouduction = prouduction;
        this.productTime = productTime;
        this.health = health;
        this.speed = speed;
        this.hungrySpeed = hungrySpeed;
    }

    public boolean isHungry() {
        return turnsToDie < health / 5;
    }

    @Override
    public int getSpeed() {
        return isHungry() ? hungrySpeed : speed;
    }

    public Cell nextMove() {
        if (isHungry() && map.getNearestGrass(cell) != null) {
            if (super.cell.hasGrass())
                return super.cell;
            return move(cell, map.getNearestGrass(cell), getSpeed());
        }
        return randomMove(cell);
    }

    public void nextTurn() {
        turnsToDie--;
        turnsToProduct--;
        if (turnsToProduct == 0) {
            cell.addEntity(new Item(prouduction, cell));
            turnsToProduct = productTime;
        }
        if (cell.hasGrass() && isHungry()) {
            Grass grass = cell.getGrass();
            grass.eat();
            turnsToDie += 15;
        }
        if (turnsToDie == -1) {
            cell.getEntities().remove(this);
        }
    }
}
