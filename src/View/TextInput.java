package View;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TextInput{
    private String init;
    int x, y;
    private TextField field;
    public TextInput(String init,int x,int y){
        TextField textField = new TextField(init);
        field = textField;
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                textField.setText("");
            }
        });
        MenuScene.root.getChildren().add(textField);

    }
    public String getString(){
        return field.getText();
    }

}
