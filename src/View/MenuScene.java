package View;

import Control.CommandController;
import Model.Level;
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

import java.sql.Time;


public class MenuScene {
    public static MenuScene menuScene = new MenuScene();

    public static Group root;
    private Stage primaryStage;
    private Scene scene;

    public Scene getScene() {
        return scene;
    }

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
        ImageView speaker = GameScene.getImage("./Graphic/Menu/mute.png");
        ImageView mute = GameScene.getImage("./Graphic/Menu/speaker.png");

        addButton(100, 100, speaker);
        speaker.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Sound.init();
                Sound.play("menu");
                root.getChildren().remove(speaker);
                addButton(100, 100, mute);
            }
        });

        mute.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Sound.mute();
                root.getChildren().remove(mute);
                addButton(100, 100, speaker);
            }
        });
        ImageView guide = GameScene.getImage("./Graphic/Menu/guide.png");
        guide.setX(1000);
        guide.setY(50);
        root.getChildren().add(guide);
        ImageView guideback = GameScene.getImage("./Graphic/Menu/guideback.png");
        guideback.setOnMouseClicked(new EventHandler<MouseEvent>() {
            int cnt = 3;
            @Override
            public void handle(MouseEvent event) {
                if(cnt == 0) {
                    Sound.mute();
                    root.getChildren().remove(guideback);
                    cnt = 3;
                }
                else
                    cnt--;
            }
        });
        guide.setOnMouseClicked(new EventHandler<MouseEvent>() {
            int cnt = 1;

            @Override
            public void handle(MouseEvent event) {
                if(cnt < 3) {
                    cnt++;
                    return;
                }
                if(cnt >= 3){
                    cnt++;
                    Sound.init();
                    Sound.mute();
                    System.err.println("PRANKED :D");
                    Sound.play("guide");

                    root.getChildren().add(guideback);
                    return;
                }

            }
        });
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
                LevelScene.LevelScene.setPrimaryStage(primaryStage);
                LevelScene.LevelScene.start();
            }
        });
    }

    private void addLoadButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 113, y + 57, "Load");

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
                GameScene.gameScene.start(null, null);
            }
        });
    }

    private void addQuitButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 120, y + 60, "Quit");

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
                TextInput name = new TextInput("admin", 200, 500);
                TextInput id = new TextInput("admin", 200, 500 + dy);
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

                        Client client;
                        try {
                            client = new Client(name.getString(), id.getString(), "localhost");
                            client.setServer(true);
                        } catch (Exception e) {
                            return;
                        }

                        ClientMenuScene.clientMenuScene.setPrimaryStage(primaryStage);
                        ClientMenuScene.clientMenuScene.start(client);

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
        Text text = new Text(x + 110, y + 57, "Client");
        addButton(x, y, button);
        addText(text);

        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(200, 200, 200));
                int dy = 30;
                TextInput name = new TextInput("name", 800, 330);
                TextInput id = new TextInput("id", 800, 330 + dy);
                TextInput host = new TextInput("localhost", 800, 330 + dy * 2);
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

    private void addRankingButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 92, y + 57, "Ranking");

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
                //    GameScene.gameScene.setPrimaryStage(primaryStage);
                //    GameScene.gameScene.start();
            }
        });
    }
}
