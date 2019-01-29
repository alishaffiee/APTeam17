package Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class ConnectThread extends Thread{
    private Server server;

    public ConnectThread(Server server) {
        this.server = server;
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
                String id = scanner.nextLine();
                System.err.println("user id is : " + id);

                formatter.format(Integer.toString(cnt) + '\n');
                System.err.println("port is " + cnt);
                formatter.flush();

                System.err.println("waiting for finishing");
                String connected = scanner.nextLine();
                System.err.println("connected");

                formatter.close();
                scanner.close();

                if(connected.equals("false")) {
                    System.out.println("cannot connect");
                    continue;
                }

                serverSocket.close();
                serverSocket = new ServerSocket(cnt);
                cnt++;
                socket = serverSocket.accept();
                System.err.println("User adding");

                /*
                Profile profile = new Profile(new Person(id, id), socket);
                profiles.add(profile);
                profile.setServer(me);

                profile.run();

                System.err.println("User added");
                */
            } catch (Exception e) {
                System.err.println("Server's problem");
                e.printStackTrace();
            }
        }
    }
}
