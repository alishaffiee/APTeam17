package Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Scanner scanner;
    private Formatter formatter;

    public Client() {
        try {
            socket = new Socket("loaclhost", 8050);
            scanner = new Scanner(socket.getInputStream());
            formatter = new Formatter(socket.getOutputStream());
            int port = scanner.nextInt();
            formatter.format("1234");

        } catch (Exception e) {
            System.out.println("Server not found.");
        }

    }
}
