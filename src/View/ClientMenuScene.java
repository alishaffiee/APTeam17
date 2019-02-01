package View;

import Network.Client;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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

import java.util.ArrayList;
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
        int offset = 100;

        addStartButton(410, offset);
        addProfileButton(410, 130 + offset);
        addUsersButton(410, 260 + offset);
        addRankingButton(410, 390 + offset);
        addChatRoomButton(410, 520 + offset);

        primaryStage.setTitle("Farm Frenzy");
        primaryStage.setScene(scene);
        primaryStage.show();
        ImageView speaker = GameScene.getImage("./Graphic/Menu/mute.png");
        ImageView mute = GameScene.getImage("./Graphic/Menu/speaker.png");
        int pos = 50;
        if (Sound.MenuSound)
            addButton(pos, pos, mute);
        else
            addButton(pos, pos, speaker);

        speaker.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Sound.init();
                Sound.play("menu");
                root.getChildren().remove(speaker);
                addButton(pos, pos, mute);
            }
        });

        mute.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Sound.mute();
                root.getChildren().remove(mute);
                addButton(pos, pos, speaker);
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
                GameScene.gameScene.setPrimaryStage(primaryStage);
                GameScene.gameScene.start("Level0", client);
            }
        });
    }

    private void addProfileButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        ImageView profile = GameScene.getImage("./Graphic/profile.png");
        ImageView cancel = GameScene.getImage("./Graphic/Menu/cancel.png");
        Image image = cancel.getImage();
        Text text = new Text(x + 102, y + 57, "Profile");

        addButton(x, y, button);
        addText(text);
        int profilex = 720;
        int profiley = 50;
        profile.setX(profilex);
        profile.setY(profiley + 100);
        cancel.setX(profilex);
        cancel.setY(profiley);

        ArrayList<Node> show = new ArrayList<>();
        show.add(profile);
        TextInput textInput = new TextInput("send pv", 720, 10);
        root.getChildren().remove(textInput.getTextField());
        String target = "";
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().add(textInput.getTextField());
                text.setFill(Color.rgb(200, 200, 200));
                String ans = client.addCommand("get profile");
                System.out.println("PROFILES " + ans);
                String[] array = ans.split(" ");
                int n = array.length;
                int cur = profiley;
                cancel.setImage(image);
                for (int i = 0; i < n; i += 5) {
                    String s = "";
                    String id = array[i];
                    s = s + " ID: " + array[i] + "\n";
                    s = s + " Buy: " + array[i + 2] + "\n";
                    s = s + " Sell: " + array[i + 3] + "\n";
                    s = s + " Friends: " + array[i + 4] + "\n";
                    Label prof = new Label();
                    prof.setStyle("-fx-font: normal bold 20px 'Comic Sans MS'");
                    prof.setText(s);
                    prof.relocate(profilex + 100, cur);
                    ImageView addFriend = GameScene.getImage("./Graphic/addfriend.png");
                    addFriend.setX(profilex + 100 - 32);
                    addFriend.setY(cur);
                    ImageView sendMessage = GameScene.getImage("./Graphic/email.png");
                    sendMessage.setX(profilex + 100 - 32);
                    sendMessage.setY(cur + 50);
                    sendMessage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            client.addCommand("send pv " + client.getId() + " " + id + " " + textInput.getString());
                        }
                    });

                    addFriend.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            System.out.println("ss");
                            client.addCommand("friend " + client.getId() + " " + id);
                            for (Node o : show)
                                if (o instanceof Label) {
                                    ((Label) o).setText("");
                                }
                            root.getChildren().removeAll(show);
                            cancel.setImage(null);
                            text.setFill(Color.BLACK);
                        }
                    });
                    show.add(sendMessage);
                    show.add(prof);
                    show.add(addFriend);
                    cur += 120;
                }
                //    show.add(cancel);
                for (Node o : show) {
                    root.getChildren().add(o);
                }
                if (!root.getChildren().contains(cancel))
                    root.getChildren().add(cancel);
            }
        });

        cancel.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().remove(textInput.getTextField());
                for (Node o : show)
                    if (o instanceof Label) {
                        ((Label) o).setText("");
                    }
                root.getChildren().removeAll(show);
                cancel.setImage(null);
                text.setFill(Color.BLACK);
        }
    });
        /*
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
        */
}


    private void addUsersButton(int x, int y) {
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        ImageView profile = GameScene.getImage("./Graphic/profile.png");
        Text text = new Text(x + 110, y + 57, "Users");

        addButton(x, y, button);
        addText(text);
        Label users = new Label();
        int profilex = 800;
        int profiley = 50;
        profile.setX(profilex);
        profile.setY(profiley);
        users.setStyle("-fx-font: normal bold 20px 'Comic Sans MS'");
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(200, 200, 200));
                String ans = client.addCommand("get users");
                System.out.println("USERS " + ans);
                String[] array = ans.split(" ");
                int n = array.length;
                String text = "";
                for (int i = 0; i < n; i++) {
                    String s = String.valueOf(i + 1) + ". ";
                    s = s + array[i];
                    text = text + s + '\n';
                }
                users.setText(text);
                users.relocate(profilex + 100, profiley);
                root.getChildren().add(profile);
                root.getChildren().add(users);
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setFill(Color.rgb(0, 0, 0));
                root.getChildren().remove(profile);
                root.getChildren().remove(users);
            }
        });
    }

    private void addRankingButton(int x, int y) {
        int rankx = 50;
        int ranky = 150;
        ImageView button = GameScene.getImage("./Graphic/Menu/Button.png");
        ImageView ranks = GameScene.getImage("./Graphic/ranking.png");
        ranks.setX(rankx);
        ranks.setY(ranky);

        Text text = new Text(x + 92, y + 57, "Ranking");

        addButton(x, y, button);
        addText(text);
        Label scoreboard = new Label();


        scoreboard.setStyle("-fx-font: normal bold 20px 'Comic Sans MS'");

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
                    array[i / 2] = arr[i];
                }
                int n = array.length;
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j + 1 < n; j++) {
                        if (score.get(array[j]) < score.get(array[j + 1])) {
                            String tmp = array[j];
                            array[j] = array[j + 1];
                            array[j + 1] = tmp;
                        }
                    }
                }
                String text = "";
                for (int i = 0; i < n; i++) {
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
                ChatScene.chatScene.setPrimaryStage(primaryStage);
                ChatScene.chatScene.start(client.getChatSocket(), client.getId());
            }
        });
    }

    public Scene getScene() {
        return scene;
    }
}
