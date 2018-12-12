package Model;

import Consts.Consts;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    private int height, width, goalMoney, money;
    private ArrayList<Cell> cells;
    private Warehouse warehouse;
    private Well well;
    private HashMap<Item, Integer> goals;

    public Map(int height, int width, int goalMoney, HashMap<Item, Integer> goals) {
        this.height = height;
        this.width = width;
        this.goalMoney = goalMoney;
        this.goals = goals;
        money = 0;
        cells = new ArrayList<>();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                cells.add(new Cell(i, j));
            }
        }
        warehouse = new Warehouse(Consts.WAREHOUSE_CAPACITY);
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

    public boolean isCompeleted() {
        if(money < goalMoney)
            return false;
        for(Item item : goals.keySet()) {
            int count = 0;
            for(Cell cell : cells) {
                count += cell.count(item);
            }
            if(count < goals.get(item))
                return false;
        }
        return true;
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
}
