package Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Server {
    private Socket socket;
    private ServerSocket serverSocket;
    private Scanner scanner;
    private Formatter formatter;

    public Server() {
        try {
            serverSocket = new ServerSocket(8050);
            ConnectThread connectThread = new ConnectThread(serverSocket, this);
            connectThread.run();
            socket = serverSocket.accept();
            scanner = new Scanner(socket.getInputStream());
            formatter = new Formatter(socket.getOutputStream());
            formatter.format("8060");
            int value = scanner.nextInt();

        } catch (Exception e) {
            System.out.println("Server not found.");
        }
    }
}
