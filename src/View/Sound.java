package View;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Sound {

    private static HashMap<String, MediaPlayer> library = new HashMap<>();
    public static boolean MenuSound = false;
    public static void init() {
        library.clear();
        Media sound = new Media(new File("Sounds/menu.mp3").toURI().toString());
        library.put("menu", new MediaPlayer(sound));
    }

    public static void play(String name) {
        MediaPlayer sound = library.get(name);
        if(name.equals("menu"))
            sound.setCycleCount(1000);
        sound.play();
        MenuSound = true;

    }

    public static void mute() {
        for (Map.Entry<String, MediaPlayer> entry : library.entrySet()) {
            entry.getValue().stop();
            MenuSound = false;
        }
    }



}