package Social;

import Network.Server;

import java.net.Socket;

public class Profile {
    private User user;
    private Socket socket;
    private Server server;

    public Profile(User user, Socket socket) {
        this.user = user;
        this.socket = socket;
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
}
