package Control;

public class CommandController {
    public static CommandController commandController = new CommandController(Game.game);
    private Game game;

    private CommandController(Game game) {
        this.game = game;
    }

    public void buyAnimal(String name) {

    }

    public void pickup(int x, int y) {

    }

    public void cage(int x, int y) {

    }

    public void plant(int x, int y) {

    }

    public void well() {

    }

    public void startWorkshop(String name) {

    }

    public void upgrade(String name) {

    }

    public void load(String path) {

    }

    public void run(String mapName) {

    }

    public void saveGame(String path) {

    }

    public void loadGame(String path) {

    }

    public void print(String name) {

    }

    public void nextTurn(int count) {

    }
}
