package View;

import Control.CommandController;
import Network.Client;
import Network.Server;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MenuScene {
    public static MenuScene menuScene = new MenuScene();

    public static Group root;
    private Stage primaryStage;
    private Scene scene;

    private MenuScene() {
        root = new Group();
        scene = new Scene(root, 1100, 825);
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start() {
        ImageView background = GameScene.getImage("./Graphic/Menu/Background.png");
        root.getChildren().add(background);
        int offset = -10;
        addStartButton(410, 90 - offset);
        addLoadButton(410, 220 - offset);
        addClientButton(410, 350 - offset);
        addHostButton(410, 480 - offset);
        addQuitButton(410, 610 - offset);
        primaryStage.setTitle("Farm Frenzy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addButton(int x, int y, ImageView button) {
        button.setX(x);
        button.setY(y);
        root.getChildren().add(button);
    }

    private void addText(Text text) {
        text.setFont(Font.font(null, FontWeight.BOLD, 32));
        root.getChildren().add(text);
    }

    private void addStartButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 64, y + 57, "New Game!");

        addButton(x, y, button);
        addText(text);

        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(200, 200, 200));
            }
        });

        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(0, 0, 0));
                GameScene.gameScene.setPrimaryStage(primaryStage);
                GameScene.gameScene.start("Level0");
            }
        });
    }

    private void addLoadButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 107, y + 57, "Load");

        addButton(x, y, button);
        addText(text);

        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(200, 200, 200));
            }
        });

        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(0, 0, 0));
                CommandController.commandController.loadGame("gameData");
                GameScene.gameScene.setPrimaryStage(primaryStage);
                GameScene.gameScene.start(null);
            }
        });
    }

    private void addQuitButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 112, y + 57, "Quit");

        addButton(x, y, button);
        addText(text);

        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(200, 200, 200));
            }
        });

        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(0, 0, 0));
                primaryStage.close();
            }
        });
    }

    private void addHostButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 115, y + 57, "Host");
        addButton(x, y, button);
        addText(text);

        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(200, 200, 200));
                int dy = 30;
                TextInput name = new TextInput("name", 200, 500);
                TextInput id = new TextInput("id", 200, 500 + dy);
                ImageView check = GameScene.getImage("./Graphic/Menu/check.png");
                ImageView cancel = GameScene.getImage("./Graphic/Menu/cancel.png");
                cancel.setX(335);
                cancel.setY(570);
                check.setX(200);
                check.setY(570);
                root.getChildren().add(cancel);
                root.getChildren().add(check);
                cancel.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        root.getChildren().remove(name.getTextField());
                        root.getChildren().remove(id.getTextField());
                        root.getChildren().remove(check);
                        root.getChildren().remove(cancel);
                        text.setFill(Color.BLACK);
                    }
                });

                check.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Server server = new Server();
                        root.getChildren().remove(name.getTextField());
                        root.getChildren().remove(id.getTextField());
                        root.getChildren().remove(check);
                        root.getChildren().remove(cancel);
                        text.setFill(Color.BLACK);
                    }
                });
            }
        });

        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextField textField = new TextField();
            }
        });

    }

    private void addClientButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 105, y + 57, "Client");
        addButton(x, y, button);
        addText(text);

        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(200, 200, 200));
                int dy = 30;
                TextInput name = new TextInput("name", 800, 330);
                TextInput id = new TextInput("id", 800, 330 + dy);
                TextInput host = new TextInput("host", 800, 330 + dy * 2);
                ImageView check = GameScene.getImage("./Graphic/Menu/check.png");
                ImageView cancel = GameScene.getImage("./Graphic/Menu/cancel.png");
                cancel.setX(935);
                cancel.setY(420);
                check.setX(800);
                check.setY(420);
                root.getChildren().add(cancel);
                root.getChildren().add(check);
                cancel.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        root.getChildren().remove(name.getTextField());
                        root.getChildren().remove(id.getTextField());
                        root.getChildren().remove(host.getTextField());
                        root.getChildren().remove(check);
                        root.getChildren().remove(cancel);
                        text.setFill(Color.BLACK);
                    }
                });

                check.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Client client;
                        try {
                            client = new Client(name.getString(), id.getString(), host.getString());
                        } catch (Exception e) {
                            root.getChildren().remove(name.getTextField());
                            root.getChildren().remove(id.getTextField());
                            root.getChildren().remove(host.getTextField());
                            root.getChildren().remove(check);
                            root.getChildren().remove(cancel);
                            text.setFill(Color.BLACK);
                            return;
                        }

                        root.getChildren().remove(host.getTextField());
                        root.getChildren().remove(name.getTextField());
                        root.getChildren().remove(id.getTextField());
                        root.getChildren().remove(check);
                        root.getChildren().remove(cancel);
                        text.setFill(Color.BLACK);

                        ClientMenuScene.clientMenuScene.setPrimaryStage(primaryStage);
                        ClientMenuScene.clientMenuScene.start(client);
                    }
                });
            }
        });
    }
}
