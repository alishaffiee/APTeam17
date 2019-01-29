package Network;

import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class ServerCommandController {
    private Connection connection;
    private Scanner scanner;
    private Formatter formatter;

    public ServerCommandController(Connection connection) {
        this.connection = connection;
    }

    public void start() {
        Socket socket = connection.getSocket();
        try {
            scanner = new Scanner(socket.getInputStream());
            formatter = new Formatter(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("Cannot start client command controller");
            e.printStackTrace();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String command = scanner.nextLine();
                    handle(command);
                }
            }
        }).start();
    }

    private void handle(String command) {
        switch (command) {
            case "show": {
                break;
            }
            default: {
                System.out.println("command is invalid.");
            }
        }
    }

    private void sendCommand(String command) {
        formatter.format(command + "\n");
    }
}
