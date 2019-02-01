package Network;

import Social.Profile;
import Social.User;

import java.net.Socket;
import java.util.ArrayList;
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
        System.out.println("*" + socket.getPort());
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
            case "get users": {
                ArrayList<Profile> profiles = connection.getServer().getProfiles();
                String ans = "";
                for(Profile profile : profiles) {
                    User user = profile.getUser();
                    ans = ans + " " + user.getId();
                }
                ans = ans.trim();
                sendCommand(ans);
                break;
            }
            case "get scores": {
                ArrayList<Profile> profiles = connection.getServer().getProfiles();
                String ans = "";
                for(Profile profile : profiles) {
                    ans = ans + " " + profile.getUser().getName() + " " + profile.getUser().getScore();
                }
                ans = ans.trim();
                sendCommand(ans);
                break;
            }
            case "get profile": {
                ArrayList<Profile> profiles = connection.getServer().getProfiles();
                String ans = "";
                for(Profile profile : profiles) {
                    User user = profile.getUser();
                    ans = ans + " " + user.getId() + " " + user.getName() + " " + user.getBuyCount() + " " + user.getSellCount() + " " + profile.getFriends();
                }
                ans = ans.trim();
                sendCommand(ans);
                break;
            }
            default: {
                System.out.println("here");
                String[] parts = command.split(" ");
                ArrayList<Profile> profiles = connection.getServer().getProfiles();
                Profile profile1 = null, profile2 = null;
                for(Profile profile : profiles) {
                    if(profile.getUser().getId().equals(parts[1]))
                        profile1 = profile;
                    if(profile.getUser().getId().equals(parts[2]))
                        profile2 = profile;
                }
                sendCommand("completed!");
                if(parts[1].equals(parts[2]))
                    return;
                System.out.println("salamll");
                profile1.addFriend(profile2);
                profile2.addFriend(profile1);
            }
        }
    }

    private void sendCommand(String command) {
        formatter.format(command + "\n");
        formatter.flush();
    }
}
