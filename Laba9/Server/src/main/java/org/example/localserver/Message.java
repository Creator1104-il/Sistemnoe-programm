package org.example.localserver;
import java.io.Serializable;

public class Message implements Serializable {
    private String sender;
    private String text;
    private String timestamp;

    public Message(String sender, String text, String timestamp) {
        this.sender = sender;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getSender() { return sender; }
    public String getText() { return text; }
    public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + sender + ": " + text;
    }
}
