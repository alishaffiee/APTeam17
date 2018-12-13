package Model;

import Model.Animal.Animal;

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
            if(entity instanceof Animal) {
                Animal animal = (Animal) entity;
                Cell cell = animal.nextMove();
            }
        }
    }

    public ArrayList<Animal> getAnimals() {
        ArrayList<Animal> animals= new ArrayList<>();
        for(Entity entity : entities) {
            if(entity instanceof Animal) {
                animals.add((Animal)entity);
            }
        }
        return animals;
    }

    public void deleteAnimals() {
        for(Entity entity : entities) {
            if(entity instanceof Animal) {
                entities.remove(entity);
            }
        }
    }

    public int count(ItemType itemType) {
        int ans = 0;
        for(Entity entity : entities) {
            if(entity instanceof Item) {
                Item item1 = (Item) entity;
                if(item1.getItemType().equals(itemType))
                    ans++;
            }
        }
        return ans;
    }
}
