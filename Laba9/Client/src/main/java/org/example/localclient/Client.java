package org.example.localclient;

import java.io.*;
import java.net.*;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Client implements Runnable{

    private static final Client client = new Client();
    private String host;
    private int port;
    private String username;
    private String password;;

    private boolean isConnected;

    private List massageList = new List("Massages");

    private Button ButtonSend;
    private TextField textField;
    private ListView massageListView;

    private InputStream in;
    private BufferedReader bReader;
    private OutputStream out;
    private PrintWriter pWriter;

    private String text;

    private Label statusLabel;

    //реализация паттерна одиночки
    private Client() {}
    public static Client getInstance() { return client; }

    public void setStatusLabel(Label l){statusLabel = l;}
    public void setText(String s){
        text = s;
    }
    public String getText(){
        return text;
    }
    //назначение кнопки за отправку сообщений
    public void setButtonSend(Button buttonSend) {
        ButtonSend = buttonSend;
        ButtonSend.setOnAction(e->{
            String s = textField.getText();
            if(Check.isStringNotEmpty(s)) {
                pWriter.write(s + "\n");
                pWriter.flush();
                textField.setText("");
            } else {
                textField.setText("");
            }
        });
    }
    public void setMassageListView(ListView l){
        massageListView = l;
    }
    //получить список сообщений
    public ObservableList getMassegaList(){
        return massageList.getList();
    }

    //откуда брать сообщение
    public void setMessageField(TextField t){
        textField = t;
    }

    public String gethost(){
        return host;
    }
    public int getPort(){
        return port;
    }

    //сетеры
    public void setUsername(String username){
        this.username = username;
    }
    public void setHost(String host){
        this.host = host;
    }
    public void setPort(int port){
        this.port = port;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public boolean isConnected(){
        return isConnected;
    }

    public String getUsername(){
        return this.username;
    }
    public void addMessageToChat(String s){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                massageList.addUToList(s);
                //автопрокрутка
                if(massageListView!=null){
                    massageListView.scrollTo(massageList.getSize()-1);
                }
            }
        });
    }
    @Override
    public void run(){
        try {
            Socket socket = new Socket(host, port);
            in = socket.getInputStream();
            bReader = new BufferedReader(new InputStreamReader(in));
            out = socket.getOutputStream();
            pWriter = new PrintWriter(out);
            pWriter.write(username + "$@?#" + password + "\n");
            pWriter.flush();
            System.out.println("отправлено");
            String answer = bReader.readLine();
            if(answer.equals("AUTH_OK")){
                System.out.println("connected");
                isConnected = true;
                String m;
                //получение сообщений
                while (true){
                    m = bReader.readLine();
                    addMessageToChat(m);
                }
            } else{
                isConnected = false;
            }
        } catch (Exception e){
            //нужно потомучто обновляем из другого класса
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    statusLabel.setText("Ошибка при подключении");
                }
            });
        }
        finally {
            try {
                //закрытие всех i/o потоков
                if(in!=null) {in.close();}
                if (bReader!=null) {bReader.close();}
                if(out!=null){out.close();}
                if(pWriter!=null){ pWriter.close();}
            } catch (Exception ex) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        statusLabel.setText("Ошибка при закрытии i/o потоков");
                    }
                });
            }
        }
    }
}
