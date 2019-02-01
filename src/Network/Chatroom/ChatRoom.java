package Network.Chatroom;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class ChatRoom {
    ArrayList<Socket> sockets;
    ArrayList<String> messages;
    ArrayList<Formatter> formatters;

    public ChatRoom() {
        sockets = new ArrayList<>();
        messages = new ArrayList<>();
        formatters = new ArrayList<>();
    }

    private void sendData(Formatter formatter) {
        formatter.format(messages.size() + "\n");
        for(String  meassage : messages) {
            formatter.format(meassage + "\n");
        }
        formatter.flush();
    }

    public void addSocket(Socket socket) {
        Scanner scanner;
        Formatter formatter;
        try {
            scanner = new Scanner(socket.getInputStream());
            formatter = new Formatter(socket.getOutputStream());
            formatters.add(formatter);
        } catch (Exception e) {
            System.err.println("cannot add socket.");
            return;
        }
        sockets.add(socket);

        sendData(formatter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String message = scanner.nextLine();
                    messages.add(message);
                    for (Formatter formatter : formatters) {
                        sendData(formatter);
                    }
                }
            }
        }).start();
    }

    public void sendMessage(String value) {
        messages.add(value);
    }
}
