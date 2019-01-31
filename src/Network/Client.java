package Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Scanner scanner;
    private Formatter formatter;
    private ClientCommandController clientCommandController;

    public Client(String name, String id, String host) throws Exception{
        try {
            socket = new Socket(host, 8050);
            scanner = new Scanner(socket.getInputStream());
            formatter = new Formatter(socket.getOutputStream());
            formatter.format(name + "\n");
            formatter.format(id + "\n");
            formatter.flush();
            int port = Integer.valueOf(scanner.nextLine());

            socket = new Socket(host, port);
            clientCommandController = new ClientCommandController(this);
            clientCommandController.start();
        } catch (Exception e) {
            System.out.println("Server not found.");
            e.printStackTrace();
            socket.close();
            throw new Exception("Server not found.");
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public String addCommand(String command) {
        return clientCommandController.sendCommand(command);
    }
}
