package Model;

import java.util.ArrayList;

public class Map {
    private int height, width;
    private ArrayList<Cell> cells;

    public Map(int height, int width) {
        this.height = height;
        this.width = width;
        cells = new ArrayList<>();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                cells.add(new Cell(i, j));
            }
        }
    }

    public void nextTurn() {
        for(Cell cell : cells) {
            cell.nextTurn();
        }
    }
}
