package Network.Chatroom;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class ChatRoom {
    ArrayList<Socket> sockets;
    ArrayList<Message> messages;
    ArrayList<Formatter> formatters;

    public ChatRoom() {
        sockets = new ArrayList<>();
        messages = new ArrayList<>();
        formatters = new ArrayList<>();
    }

    public void addSocket(Socket socket) {
        Scanner scanner;
        try {
            scanner = new Scanner(socket.getInputStream());
            formatters.add(new Formatter(socket.getOutputStream()));
        } catch (Exception e) {
            System.err.println("cannot add socket.");
            return;
        }
        sockets.add(socket);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String message = scanner.nextLine();
                    for (Formatter formatter : formatters) {
                        formatter.format(message + " " + 0 + "\n");
                        formatter.flush();
                    }
                }
            }
        }).start();
    }

    public void sendMessage(String value) {
        Message message = new Message(value, true);
        messages.add(message);
    }
}
