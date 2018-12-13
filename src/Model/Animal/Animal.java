package Model.Animal;

import Model.Entity;
import Model.Map;

abstract public class Animal extends Entity {
    protected Map map;
    public Animal(Map map) {
        this.map = map;
    }
}
