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
            serverSocket = new ServerSocket(8050);
            ConnectThread connectThread = new ConnectThread(this);
            connectThread.run();
        } catch (Exception e) {
            System.out.println("Server not found.");
        }
    }

    public void addProfile(Profile profile) {
        profiles.add(profile);
    }
}
