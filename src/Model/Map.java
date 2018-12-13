package Model;

import Model.Animal.Animal;
import Values.Values;

import java.util.ArrayList;

public class Map {
    private int height, width, money;
    private ArrayList<Cell> cells;
    private Warehouse warehouse;
    private Well well;

    public Map(int height, int width, int initialMoney) {
        this.height = height;
        this.width = width;
        money = initialMoney;
        cells = new ArrayList<>();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                cells.add(new Cell(i, j));
            }
        }
        warehouse = new Warehouse(Values.WAREHOUSE_CAPACITY);
        well = new Well();
    }

    public void nextTurn() {
        for(Cell cell : cells) {
            cell.nextTurn();
        }

        ArrayList<Animal> allAnimals = new ArrayList<>();

        for(Cell cell : cells) {
            ArrayList<Animal> animals = cell.getAnimals();
            for(Animal animal : animals) {
                animal.setCell(animal.nextMove());
                allAnimals.add(animal);
            }
            cell.deleteAnimals();
        }

        for(Animal animal : allAnimals) {
            animal.getCell().addEntity(animal);
        }
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    int count(ItemType itemType) {
        int ans = 0;
        for(Cell cell : cells)
            ans += cell.count(itemType);
        return ans;
    }

    public int getDistance(Cell a, Cell b) {
        int dx = (a.getPositionX() - b.getPositionX());
        int dy = (a.getPositionY() - b.getPositionY());
        return dx * dx + dy * dy;
    }

    public Cell getNearestGrass(Cell cell) {
        int mn = (int)2e9;
        Cell ans = null;
        for(Cell cell1 : cells) {
            if(cell1.hasGrass() && mn < getDistance(cell, cell)) {
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
        for(Cell cell : cells) {
            if(cell.getPositionY() == x && cell.getPositionY() == y)
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
}
