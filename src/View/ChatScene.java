package View;

import Network.Server;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

import static View.GameScene.getImage;

public class ChatScene {
    public static ChatScene chatScene = new ChatScene();

    public static Group root;
    private Stage primaryStage;
    private Scene scene;

    private ChatScene() {
        root = new Group();
        scene = new Scene(root, 1100, 825);
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start(Socket socket, String id) {
        ImageView background = GameScene.getImage("./Graphic/Menu/Background.png");
        root.getChildren().add(background);

        VBox vBox = new VBox(10);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);

        scrollPane.setMinHeight(400);
        scrollPane.setMinWidth(300);
        scrollPane.setMaxHeight(400);

        scrollPane.relocate(400, 150);
        root.getChildren().add(scrollPane);

        TextField textField = new TextField();
        textField.relocate(400, 570);
        textField.setMinWidth(260);
        textField.setMinHeight(30);
        root.getChildren().add(textField);

        ImageView check = GameScene.getImage("./Graphic/Menu/check.png");
        check.relocate(670, 570);
        root.getChildren().add(check);

        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            Formatter formatter = new Formatter(socket.getOutputStream());

            check.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (textField.getText().equals(""))
                        return;
                    formatter.format(id + ": " + textField.getText() + "\n");
                    formatter.flush();
                    vBox.getChildren().add(new Label(id + ": " + textField.getText()));
                    textField.setText("");
                }
            });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        String cnt = scanner.nextLine();
                        int num = Integer.valueOf(cnt);
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                vBox.getChildren().clear();
                            }
                        });

                        for(int i = 0; i < num; i++) {
                            String message = scanner.nextLine();
                            Platform.runLater(new Runnable() {
                                @Override public void run() {
                                    vBox.getChildren().add(new Label(message));
                                }
                            });
                        }
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        addBackButton();
        primaryStage.setTitle("Farm frenzy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addBackButton(){
        ImageView back = getImage("./Graphic/backbutton.png");
        back.setX(1000);
        back.setY(20);
        root.getChildren().add(back);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().clear();
                primaryStage.setScene(ClientMenuScene.clientMenuScene.getScene());
            }
        });
    }
}
