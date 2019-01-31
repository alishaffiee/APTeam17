package Social;

public class User {
    private String name, id;
    private int buyCount, sellCount, score;

    public User (String name, String id) {
        this.name = name;
        this.id = id;
        score = name.length();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getScore() {
        return name.length();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void buy() {
        buyCount++;
    }

    public void sell() {
        sellCount++;
    }
}
