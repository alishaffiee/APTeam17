package Network.Chatroom;

public class Message {
    private String value;
    private boolean mine;

    public Message(String value, boolean mine) {
        this.mine = mine;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isMine() {
        return mine;
    }
}
