package Network;

import Social.Profile;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Server {
    private Socket socket;
    private ServerSocket serverSocket;
    private Scanner scanner;
    private Formatter formatter;
    private ArrayList<Profile> profiles = new ArrayList<>();

    public Server() {
        try {
            ConnectThread connectThread = new ConnectThread(this);
            connectThread.start();
            System.out.println("Building server was successful.");
        } catch (Exception e) {
            System.out.println("Server not found.");
            e.printStackTrace();
        }
    }

    public void addProfile(Profile profile) {
        profiles.add(profile);
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }
}
