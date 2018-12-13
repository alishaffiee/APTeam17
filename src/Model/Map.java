package Model;

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
}
