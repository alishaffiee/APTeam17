package Social;

import Network.Server;

import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

public class Profile {
    private User user;
    private Socket socket;
    private Server server;
    private ArrayList<Profile> friends;

    public Profile(User user, Socket socket) {
        this.user = user;
        this.socket = socket;
        friends = new ArrayList<>();
    }

    public Socket getSocket() {
        return socket;
    }

    public User getUser() {
        return user;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public int getFriends() {
        return friends.size();
    }

    public void addFriend(Profile profile) {
        for (Profile friend : friends) {
            if(friend.getUser().getId().equals(profile.getUser().getId()))
                return;
        }
        friends.add(profile);
    }
}
