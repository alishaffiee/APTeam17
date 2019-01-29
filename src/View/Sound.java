package View;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Sound {

    private static HashMap<String, MediaPlayer> library = new HashMap<>();

    public static void init() {
        library.clear();
        Media sound = new Media(new File("Sounds/menu.mp3").toURI().toString());
        library.put("menu", new MediaPlayer(sound));
    }

    public static void mute() {
        for (Map.Entry<String, MediaPlayer> entry : library.entrySet()) {
            entry.getValue().stop();
        }
    }

    public static void play(String type) {
        library.get(type).play();
    }


}