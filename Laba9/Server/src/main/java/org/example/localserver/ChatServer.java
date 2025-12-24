package org.example.localserver;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer implements Runnable{
    private static final ChatServer server = new ChatServer();
    private String ip;
    private int PORT;
    private static final List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private ChatServer(){}
    public static ChatServer getInstance(){
       return server;
    }
    public void setPORT(int port){
        this.PORT = port;
    }
    public int getPORT(){
        return PORT;
    }
    public String getIpAddress(){
        return ip;
    }
    public void run() {
        Database.init();
        try {
            ip = InetAddress.getLocalHost().toString();
            int i = ip.lastIndexOf('/');
            ip = ip.substring(i+1);
        } catch (UnknownHostException e) {
            ip = "ошибка cети";
            LogWriter.getInstance().Log("Ошибка: " + e.getMessage());
        }
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LogWriter.getInstance().Log("Сервер запущен на порту " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            LogWriter.getInstance().Log("Ошибка: " + e.getMessage());
        }
    }
    // отправка сообщения всем
    public static void broadcast(Message message) {
        saveMessage(message); // сохраняем в БД
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
    // сохранить сообщение в БД
    private static void saveMessage(Message msg) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO messages(sender, text, timestamp) VALUES(?,?,?)")) {
            ps.setString(1, msg.getSender());
            ps.setString(2, msg.getText());
            ps.setString(3, msg.getTimestamp());
            ps.executeUpdate();
        } catch (SQLException e) {
            LogWriter.getInstance().Log("Ошибка записи сообщения в БД: " + e.getMessage());
        }
    }
    // загрузить последние N сообщений
    public static List<Message> loadLastMessages(int limit) {
        List<Message> messages = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT sender, text, timestamp FROM messages ORDER BY id DESC LIMIT ?")) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                        rs.getString("sender"),
                        rs.getString("text"),
                        rs.getString("timestamp")));
            }
        } catch (SQLException e) {
            LogWriter.getInstance().Log("Ошибка БД при загрузке последних сообщений: " + e.getMessage());
        }
        Collections.reverse(messages); // чтобы в правильном порядке
        return messages;
    }

    // обработчик клиента
    static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader bReader;
        private PrintWriter pWriter;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                InputStream in = socket.getInputStream();
                bReader = new BufferedReader(new InputStreamReader(in));
                OutputStream out = socket.getOutputStream();
                pWriter = new PrintWriter(out);
                // ждём авторизацию
                String quest = bReader.readLine();
                AuthRequest auth = Spliter.splitNameAndPass(quest);
                if (checkUser(auth.getUsername(), auth.getPassword())) {
                    username = auth.getUsername();
//                        out.writeObject("AUTH_OK");
                    pWriter.write("AUTH_OK\n");
                    pWriter.flush();
                    clients.add(this);
                    pWriter.flush();
                    LogWriter.getInstance().Log(username + " вошёл в чат");
                    // отправляем историю последних 100 сообщений
                    List<Message> history = ChatServer.loadLastMessages(100);
                    for (Message m : history) {
                        pWriter.write( m.toString() + "\n");
                        pWriter.flush();
                    }
                } else {
                    pWriter.write("AUTH_FAIL\n");
                    pWriter.flush();
                    socket.close();
                    return;
                }
                //  слушаем новые сообщения
                String m;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss");
                while ((m = bReader.readLine()) != null) {
                    Message msg = new Message(username, m, sdf.format(new Date()));
                    LogWriter.getInstance().Log("Пользователь " + username + " отправил: " + m);
                    ChatServer.broadcast(msg);
                }
            } catch (IOException e) {
                LogWriter.getInstance().Log("Ошибка i/o при общении с клиентом");
                LogWriter.getInstance().Log("Клиент " + username + " отключился");
            }
             finally {
                clients.remove(this);
                try { socket.close(); } catch (IOException e) {LogWriter.getInstance().Log("Ошибка: " + e.getMessage());}
            }
        }

        private boolean checkUser(String username, String password) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "SELECT * FROM users WHERE username=? AND password=?")) {
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                LogWriter.getInstance().Log("Ошибка БД при проверке пользователя: " + e.getMessage());
                return false;
            }
        }

        public void sendMessage(Message msg) {
            pWriter.write(msg.toString() + "\n");
            pWriter.flush();
        }
    }
}

