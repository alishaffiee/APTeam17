package Network;

import java.net.Socket;
import java.util.Scanner;

public class Connection {
    private Socket socket;
    private Server server;
    private Scanner scanner;

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
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
}
