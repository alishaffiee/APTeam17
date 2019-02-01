package Network.Bazaar;

import Model.Item;
import Model.ItemType;
import Values.ItemsCosts;

import java.lang.reflect.Array;
import java.net.Socket;
import java.util.*;

public class Bazaar {
    HashMap<String , Integer> cost;
    ArrayList<Socket> sockets;
    ArrayList<Formatter> formatters;
    ArrayList<ItemType> itemTypes;

    public Bazaar() {
        sockets = new ArrayList<>();
        formatters = new ArrayList<>();
        itemTypes = new ArrayList<>();
        cost = new HashMap<>();
        for (String name : ItemsCosts.names) {
            ItemType itemType = ItemType.getItemType(name);
            cost.put(itemType.getName().toLowerCase(), itemType.getBuyCost());
        }
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String command = scanner.nextLine();
                    List<String> parts = Arrays.asList(command.split(" "));
                    switch (parts.get(0)) {
                        case "cost": {
                            formatter.format(cost.get(parts.get(1).toLowerCase()) + "\n");
                            formatter.flush();
                        }
                    }
                }
            }
        }).start();
    }
}
