package Model;

import Model.Animal.*;

import java.util.ArrayList;

public class Cell {
    private int positionX, positionY;
    private ArrayList<Entity> entities, deletes, adds;
    private Map map;

    public Cell(int positionX, int positionY, Map map) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.map = map;
        entities = new ArrayList<>();
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Grass getGrass() {
        for (Entity entity : entities) {
            if (entity instanceof Grass) {
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

    public void delete(Entity entity) {
        deletes.add(entity);
    }

    public void add(Entity entity) {
        adds.add(entity);
    }

    public void nextTurn() {
        adds = new ArrayList<>();
        deletes = new ArrayList<>();
        for (Entity entity : entities) {
            entity.nextTurn();
        }
        ArrayList<Entity> entities1 = new ArrayList<>();
        for(Entity entity : entities) {
            if(!deletes.contains(entity))
                entities1.add(entity);
        }
        entities = entities1;
        entities.addAll(adds);
    }

    public ArrayList<Pet> getPets() {
        ArrayList<Pet> pets = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Pet) {
                pets.add((Pet) entity);
            }
        }
        return pets;
    }

    public ArrayList<Dog> getDogs() {
        ArrayList<Dog> dogs = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Dog) {
                dogs.add((Dog) entity);
            }
        }
        return dogs;
    }

    public ArrayList<Cat> getCats() {
        ArrayList<Cat> cats = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Cat) {
                cats.add((Cat) entity);
            }
        }
        return cats;
    }

    public ArrayList<Animal> getAnimals() {
        ArrayList<Animal> animals = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Animal) {
                animals.add((Animal) entity);
            }
        }
        return animals;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Item) {
                items.add((Item) entity);
            }
        }
        return items;
    }

    public void deleteAnimals() {
        ArrayList<Entity> entities1 = new ArrayList<>();
        for (Entity entity : entities) {
            if (!(entity instanceof Animal)) {
                entities1.add(entity);
            }
        }
        entities = entities1;
    }

    public int count(ItemType itemType) {
        int ans = 0;
        for (Entity entity : entities) {
            if (entity instanceof Item) {
                Item item1 = (Item) entity;
                if (item1.getItemType().equals(itemType))
                    ans++;
            }
        }
        return ans;
    }

    public void pickup() {
        ArrayList<Item> items = getItems();
        int sum = 0;
        for (Item item : items) {
            sum += item.getVolume();
        }
        if (sum > map.getWarehouse().getFreeCapacity()) {
            throw new RuntimeException("Not enough storage.");
        }
        for (Item item : items) {
            map.getWarehouse().add(item.getItemType());
            entities.remove(item);
        }
    }

    public ArrayList<WildAnimal> getWildAnimals() {
        ArrayList<WildAnimal> wildAnimals = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof WildAnimal) {
                wildAnimals.add((WildAnimal) entity);
            }
        }
        return wildAnimals;
    }

    public void decreaseHealth() {
        ArrayList<WildAnimal> wildAnimals = getWildAnimals();
        for (WildAnimal wildAnimal : wildAnimals)
            wildAnimal.decreaseHealth();
    }

    public void cage() {
        ArrayList<WildAnimal> wildAnimals = getWildAnimals();
        int sum = 0;
        for (WildAnimal wildAnimal : wildAnimals) {
            sum += wildAnimal.getVolume();
        }
        if (sum > map.getWarehouse().getFreeCapacity())
            throw new RuntimeException("Not enough storage.");
        for (WildAnimal wildAnimal : wildAnimals) {
            map.getWarehouse().add(wildAnimal.getItemType());
            entities.remove(wildAnimal);
        }
    }

    public int countAnimal(String name) {
        int count = 0;
        for (Animal animal : getAnimals()) {
            if (animal.getName().equals(name))
                count++;
        }
        return count;
    }
}
