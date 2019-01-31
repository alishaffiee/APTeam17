package View;

import Control.CommandController;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class Countdown {
    Label label;
    static String decrease(String s){
        int minute = Integer.parseInt(s.substring(0, 1));
        int second = Integer.parseInt(s.substring(2, 3)) * 10 + Integer.parseInt(s.substring(3, 4));
        int val = minute * 60 + second;
        if(val == 0)
            return "END";
        val--;
        minute = val / 60;
        second = val % 60;
        return String.valueOf(minute) + ":" + String.valueOf(second / 10) + String.valueOf(second % 10);
    }
    public Countdown(Group root, int x, int y, Stage pri){
        label = new Label("3:00");
        label.relocate(x, y);
        label.setStyle("-fx-font: normal bold 30px 'serif'");
        root.getChildren().add(label);
        new AnimationTimer() {
            long prv = -1;

            @Override
            public void handle(long now) {
                if (now - prv < 1e9) {
                    //   System.out.println(1);
                    return;
                }
                prv = now;
                String next = decrease(label.getText());
                if(next.equals("END")){
                    root.getChildren().clear();
                    pri.setScene(MenuScene.menuScene.getScene());
                }
                else{
                    label.setText(next);
                }
            }
        }.start();
    }
}
