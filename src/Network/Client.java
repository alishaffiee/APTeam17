package Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Scanner scanner;
    private Formatter formatter;

    public Client(String name, String id, String host) {
        try {
            socket = new Socket(host, 8050);
            scanner = new Scanner(socket.getInputStream());
            formatter = new Formatter(socket.getOutputStream());

            formatter.format(name + "\n");
            formatter.format(id + "\n");
            formatter.flush();

            int port = Integer.valueOf(scanner.nextLine());
            socket = new Socket(host, port);

        } catch (Exception e) {
            System.out.println("Server not found.");
        }

    }
}
