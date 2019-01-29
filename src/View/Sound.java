package View;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Sound {

    private static HashMap<String, MediaPlayer> mediaPlayerHashMap = new HashMap<>();

    public static void init() {
        mediaPlayerHashMap.clear();
        Media sound = new Media(new File("Sounds/menu.mp3").toURI().toString());
        mediaPlayerHashMap.put("menu", new MediaPlayer(sound));
    }

    public static void mute() {
        for (Map.Entry<String, MediaPlayer> entry : mediaPlayerHashMap.entrySet()) {
            entry.getValue().stop();
        }
    }

    public static void play(String type) {

        mediaPlayerHashMap.get(type).play();
        System.out.println(mediaPlayerHashMap.get("menu"));
        System.out.println("TYPE " + type);
    }


}