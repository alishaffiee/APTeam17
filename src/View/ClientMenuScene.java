package View;

import Network.Client;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Control.CommandController;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.HashMap;

public class ClientMenuScene {
    public static ClientMenuScene clientMenuScene = new ClientMenuScene();

    public static Group root;
    private Stage primaryStage;
    private Scene scene;
    private Client client;

    private ClientMenuScene() {
        root = new Group();
        scene = new Scene(root, 1100, 825);
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start(Client client) {
        this.client = client;
        ImageView background = GameScene.getImage("./Graphic/Menu/Background.png");
        root.getChildren().add(background);
        int offset = 60;

        addProfileButton(410, offset);
        addUsersButton(410, 130 + offset);
        addRankingButton(410, 260 + offset);
        addChatRoomButton(410, 390 + offset);

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

    private void addProfileButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 82, y + 57, "Profile");

        addButton(x, y, button);
        addText(text);

        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(200, 200, 200));
                String ans = client.addCommand("get profile");
                System.out.println(ans);
            }
        });

        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(0, 0, 0));
            }
        });
    }

    private void addRankingButton(int x, int y) {
        int rankx = 50;
        int ranky = 50;
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        ImageView ranks = GameScene.getImage("./Graphic/ranking.png");
        ranks.setX(rankx);
        ranks.setY(ranky);

        Text text = new Text(x + 82, y + 57, "Ranking");

        addButton(x, y, button);
        addText(text);
        Label scoreboard = new Label();
        scoreboard.setStyle("-fx-font: normal bold 30px 'serif'");
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(200, 200, 200));
                String ans = client.addCommand("get scores");
                System.out.println(ans);
                HashMap<String, Integer> score = new HashMap<>();
                String[] arr = ans.split(" ");
                String[] array = new String[arr.length / 2];
                for (int i = 0; i < arr.length; i += 2) {
                    String player = arr[i];
                    int x = Integer.valueOf(arr[i + 1]);
                    score.put(player, x);
                    array[i/2] = arr[i];
                }
                int n = array.length;
                for (int i = 0; i < n; i++){
                    for (int j = 0; j + 1 < n; j++) {
                        if (score.get(array[j]) < score.get(array[j + 1])) {
                            String tmp = array[j];
                            array[j] = array[j + 1];
                            array[j + 1] = tmp;
                        }
                    }
                }
                String text = "";
                for(int i=0; i<n; i++){
                    String s = String.valueOf(i + 1) + ". ";
                    s = s + array[i] + " ";
                    s = s + score.get(array[i]);
                    text = text + s + "\n";
                }

                scoreboard.relocate(rankx + 100, ranky);
                scoreboard.setText(text);
                root.getChildren().add(scoreboard);
                root.getChildren().add(ranks);

            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(0, 0, 0));
                root.getChildren().remove(scoreboard);
                root.getChildren().remove(ranks);

            }
        });
    }

    private void addUsersButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 82, y + 57, "Users");

        addButton(x, y, button);
        addText(text);

        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(200, 200, 200));
                String ans = client.addCommand("get users");
                System.out.println(ans);
            }
        });

        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(0, 0, 0));
            }
        });
    }

    private void addChatRoomButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        Text text = new Text(x + 72, y + 57, "Chat Room");

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
            }
        });
    }
}
