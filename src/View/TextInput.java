package View;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;

public class TextInput{
    private String init;
    int x, y;
    private TextField field;
    private boolean change = false;
    private TextField textField;

    public TextInput(String init,int x,int y){
        textField = new TextField(init);
        field = textField;
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!change)
                    textField.setText("");
            }
        });
        textField.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!change)
                    textField.setText(init);
            }
        });
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                change = true;
            }
        });
        if(init.equals("item name") || init.equals("new cost"))
            BazaarScene.root.getChildren().add(textField);
        if(init.equals("send pv"))
            ClientMenuScene.root.getChildren().add(textField);
        else
            MenuScene.root.getChildren().add(textField);


    }
    public String getString(){
        return field.getText();
    }

    public TextField getTextField() {
        return textField;
    }
}
