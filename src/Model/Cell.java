package Model;

import java.util.ArrayList;

public class Cell {
    private int positionX, positionY;
    private ArrayList<Entity> entities;

    public Cell(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        entities = new ArrayList<>();
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Grass getGrass() {
        for(Entity entity : entities) {
            if(entity instanceof Grass) {
                return (Grass) entity;
            }
        }
        return null;
    }

    public boolean hasGrass() {
        return (getGrass() != null);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void nextTurn() {
        for(Entity entity : entities) {
            entity.nextTurn();
        }
    }
}
