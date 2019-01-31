package Network;

import Social.Profile;

import java.net.Socket;
import java.util.Scanner;

public class Connection {
    private Socket socket;
    private Server server;
    private Scanner scanner;
    private Profile profile;

    public Connection(Socket socket, Server server, Profile profile) {
        this.socket = socket;
        this.server = server;
        this.profile = profile;
        try {
            scanner = new Scanner(socket.getInputStream());
        } catch (Exception e) {
            System.out.println("Cannot build connection.");
        }
    }

    public String getCommand() {
        return scanner.nextLine();
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Socket getSocket() {
        return socket;
    }

    public Server getServer() {
        return server;
    }

    public Profile getProfile() {
        return profile;
    }
}
