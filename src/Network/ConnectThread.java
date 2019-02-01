package Network;

import Social.Profile;
import Social.User;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class ConnectThread extends Thread{
    private Server server;

    public ConnectThread(Server server) {
        this.server = server;
    }

    private boolean validate(String name, String id) {
        for (Profile profile : server.getProfiles()) {
            if(profile.getUser().getId().equals(id))
                return false;
        }
        if(name.length() == 0 || id.length() == 0)
            return false;
        return true;
    }

    @Override
    public void run() {
        int cnt = 8051;
        while (true) {
            ServerSocket serverSocket;
            try {
                serverSocket = new ServerSocket(8050);
                Socket socket = serverSocket.accept();
                System.err.println("connected new user");
                Formatter formatter = new Formatter(socket.getOutputStream());
                Scanner scanner = new Scanner(socket.getInputStream());

                String name = scanner.nextLine();
                String id = scanner.nextLine();

                if(!validate(name, id)) {
                    formatter.format("-1\n");
                    formatter.flush();
                    serverSocket.close();
                    System.err.println("invalid name of id.");
                    continue;
                }

                System.err.println("name = " + name);
                System.err.println("user = " + id);

                formatter.format(Integer.toString(cnt) + '\n');
                System.err.println("port is " + cnt);
                formatter.flush();

                serverSocket.close();
                serverSocket = new ServerSocket(cnt);
                cnt++;
                socket = serverSocket.accept();
                System.out.println(socket.getPort());
                System.err.println("User adding");


                Profile profile = new Profile(new User(name, id), socket);
                server.addProfile(profile);
                profile.setServer(server);

                ServerSocket serverSocket1 = new ServerSocket(cnt);
                cnt++;
                Socket socket1 = serverSocket1.accept();
                server.getChatRoom().addSocket(socket1);

                System.err.println("User added");

                new ServerCommandController(new Connection(socket, server, profile)).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
