package org.example.localclient;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class AuthWindow extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AuthWindow.class.getResource("auth_window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Локальный мессенджер - подключение");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public static void startClient(){
        FXMLLoader fxmlLoader = new FXMLLoader(AuthWindow.class.getResource("ChatWindow.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Локальный мессенджер");
            stage.setScene(scene);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.exit(0);
                }
            });
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void startWindowWithText(String s){
        FXMLLoader fxmlLoader = new FXMLLoader(AuthWindow.class.getResource("TextWindow.fxml"));
        Scene scene = null;
        Client.getInstance().setText(s);
        try {
            scene = new Scene(fxmlLoader.load(), 480, 320);
            Stage stage = new Stage();
            stage.setTitle("Информация");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}