package Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Client {
    private Socket socket, chatSocket, bazaarSocket;
    private Scanner scanner;
    private Formatter formatter;
    private ClientCommandController clientCommandController;
    private String name, id;

    public Client(String name, String id, String host) throws Exception{
        this.name = name;
        this.id = id;
        try {

            socket = new Socket(host, 8050);
            scanner = new Scanner(socket.getInputStream());
            formatter = new Formatter(socket.getOutputStream());
            formatter.format(name + "\n");
            formatter.format(id + "\n");
            formatter.flush();
            int port = Integer.valueOf(scanner.nextLine());
            socket.close();
            if(port == -1) {
                System.out.println("salam");
                throw new Exception("cannot build client.");
            }
            while (true) {
                try {
                    socket = new Socket(host, port);
                    break;
                }
                catch (Exception e){
                    System.out.println("not this time : (");
                }
            }
            clientCommandController = new ClientCommandController(this);
            clientCommandController.start();

            while (true) {
                try {
                    chatSocket = new Socket(host, port + 1);
                    break;
                }
                catch (Exception e){
                    System.out.println("not this time : (");
                }
            }

            while (true) {
                try {
                    bazaarSocket = new Socket(host, port + 2);
                    break;
                }
                catch (Exception e){
                    System.out.println("not this time : (");
                }
            }
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

    public Socket getChatSocket() {
        return chatSocket;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Socket getBazaarSocket() {
        return bazaarSocket;
    }
}
