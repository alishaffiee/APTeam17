package Interfaces;

public interface Upgradable {
    void upgrade();
    int getUpgradeCost();
    boolean canUpgrade();
    int getLevel();
}

