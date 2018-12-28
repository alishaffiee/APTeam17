package Model;

import Interfaces.Printable;
import Model.Animal.Animal;
import Model.Vehicle.Helicopter;
import Model.Vehicle.Truck;

import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Printable, Serializable {
    private int height, width, money, time;
    private ArrayList<Cell> cells;
    private Warehouse warehouse;
    private Well well;
    private Helicopter helicopter;
    private Truck truck;
    private ArrayList<Workshop> workshops;

    public Map(int height, int width, int initialMoney) {
        this.height = height;
        this.width = width;
        money = initialMoney;
        cells = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells.add(new Cell(i, j, this));
            }
        }
        warehouse = new Warehouse();
        well = new Well(this);
        workshops = new ArrayList<>();
        helicopter = new Helicopter(this);
        truck = new Truck(this);
        time = 0;
    }

    public void addWorkshop(Workshop workshop) {
        workshops.add(workshop);
    }

    private void calc(ArrayList<Animal> animals) {
        for (Animal animal : animals) {
            animal.setCell(animal.nextMove());
            animal.getCell().addEntity(animal);
        }
    }

    public void nextTurn() {
        time++;
        for (Cell cell : cells) {
            cell.nextTurn();
        }

        ArrayList<Animal> wildAnimals = new ArrayList<>();
        ArrayList<Animal> pets = new ArrayList<>();
        ArrayList<Animal> dogs = new ArrayList<>();
        ArrayList<Animal> cats = new ArrayList<>();

        for (Cell cell : cells) {
            wildAnimals.addAll(cell.getWildAnimals());
            pets.addAll(cell.getPets());
            cats.addAll(cell.getCats());
            dogs.addAll(cell.getDogs());
            cell.deleteAnimals();
        }

        calc(pets);
        calc(cats);
        calc(wildAnimals);
        calc(dogs);

        for (Workshop workshop : workshops) {
            workshop.nextTurn();
        }
        truck.nextTurn();
        helicopter.nextTurn();
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    int count(ItemType itemType) {
        return warehouse.count(itemType);
    }

    public int getDistance(Cell a, Cell b) {
        int dx = (a.getPositionX() - b.getPositionX());
        int dy = (a.getPositionY() - b.getPositionY());
        return dx * dx + dy * dy;
    }

    public Cell getNearestGrass(Cell cell) {
        int mn = (int) 2e9;
        Cell ans = null;
        for (Cell cell1 : cells) {
            if (cell1.hasGrass() && mn < getDistance(cell, cell1)) {
                mn = getDistance(cell, cell1);
                ans = cell1;
            }
        }
        return ans;
    }

    public Cell getNearestAnimal(Cell cell) {
        int mn = (int) 2e9;
        Cell ans = null;
        for (Cell cell1 : cells) {
            if (cell1.getPets().size() > 0 && mn < getDistance(cell, cell1)) {
                mn = getDistance(cell, cell1);
                ans = cell1;
            }
        }
        return ans;
    }

    public Cell getNearestItem(Cell cell) {
        int mn = (int) 2e9;
        Cell ans = null;
        for (Cell cell1 : cells) {
            if (cell1.getItems().size() > 0 && mn < getDistance(cell, cell1)) {
                mn = getDistance(cell, cell1);
                ans = cell1;
            }
        }
        return ans;
    }

    public Cell getNearestWildAnimal(Cell cell) {
        int mn = (int) 2e9;
        Cell ans = null;
        for (Cell cell1 : cells) {
            if (cell1.getWildAnimals().size() > 0 && mn < getDistance(cell, cell1)) {
                mn = getDistance(cell, cell1);
                ans = cell1;
            }
        }
        return ans;
    }

    public Cell getRandomCell() {
        return cells.get((int) (Math.random() * cells.size()));
    }

    public int getMoney() {
        return money;
    }

    public Cell getCell(int x, int y) {
        for (Cell cell : cells) {
            if (cell.getPositionX() == x && cell.getPositionY() == y)
                return cell;
        }
        return null;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void addGrass(int x, int y) {
        if (well.getWaterValue() == 0)
            throw new RuntimeException("Not enough water.");
        well.decreaseWater();
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                int p = x + dx, q = y + dy;
                Cell cell = getCell(p, q);
                if (cell == null)
                    continue;
                if (cell.hasGrass())
                    cell.getEntities().remove(cell.getGrass());
                cell.getEntities().add(new Grass(cell));
            }
        }
    }

    public void fillWell() {
        if (well.getFillCost() > money)
            throw new RuntimeException("Not enough money.");
        money -= well.getFillCost();
        well.fill();
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public Well getWell() {
        return well;
    }

    public Workshop getWorkshopByName(String name) {
        for (Workshop workshop : workshops) {
            if (workshop.getName().equals(name))
                return workshop;
        }
        return null;
    }

    public Helicopter getHelicopter() {
        return helicopter;
    }

    public Truck getTruck() {
        return truck;
    }

    private ArrayList<Animal> getAnimals() {
        ArrayList<Animal> animals = new ArrayList<>();
        for (Cell cell : cells) {
            animals.addAll(cell.getAnimals());
        }
        return animals;
    }

    private ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        for (Cell cell : cells) {
            items.addAll(cell.getItems());
        }
        return items;
    }

    private ArrayList<Cell> getGrassCells() {
        ArrayList<Cell> grassCells = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell.hasGrass())
                grassCells.add(cell);
        }
        return grassCells;
    }

    public void print() {
        ArrayList<Cell> grassCells = getGrassCells();
        ArrayList<Animal> animals = getAnimals();
        ArrayList<Item> items = getItems();
        if(grassCells.size() == 0) {
            System.out.println("There isn't any grass in the map.");
        }
        else {
            System.out.println("Grass cells : ");
            for(Cell cell : grassCells) {
                System.out.println(" + " + cell.getPositionX() + ", " + cell.getPositionY());
            }
        }

        if(animals.size() == 0) {
            System.out.println("There isn't any animal in the map.");
        }
        else {
            System.out.println("Animals : ");
            for(Animal animal : animals) {
                System.out.println(" + " + animal.getName() + " in position " + animal.getCell().getPositionX() + ", "  + animal.getCell().getPositionY());
            }
        }

        if(items.size() == 0) {
            System.out.println("There isn't any item in the map.");
        }
        else {
            System.out.println("Items : ");
            for(Item item : items) {
                System.out.println(" + " + item.getItemType().getName() + " in position " + item.getCell().getPositionX() + ", "  + item.getCell().getPositionY());
            }
        }
    }

    public int getTime() {
        return time;
    }

    public int countAnimal(String name) {
        int count = 0;
        for (Cell cell : cells) {
            count += cell.countAnimal(name);
        }
        return count;
    }
}
