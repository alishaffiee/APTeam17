package Network;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class ClientCommandController {
    private Client client;
    private Scanner scanner;
    private Formatter formatter;
    private ArrayList<String> infos = new ArrayList<>();

    public ClientCommandController(Client client) {
        this.client = client;
    }

    public void start() {
        Socket socket = client.getSocket();
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
                    System.out.println(command);
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
                infos.add(command);
            }
        }
    }

    public String sendCommand(String command) {
        formatter.format(command + "\n");
        formatter.flush();

        while (true) {
            if (infos.size() == 0)
                continue;
            String info = infos.get(0);
            infos.remove(info);
            return info;
        }
    }
}
