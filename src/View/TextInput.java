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
    public TextInput(String init,int x,int y){
        TextField textField = new TextField(init);
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
        MenuScene.root.getChildren().add(textField);

    }
    public String getString(){
        return field.getText();
    }

}
