package org.example.localserver;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class FirstWindow extends Application {
    @Override
    //запуск окна запуска сервера
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FirstWindow.class.getResource("FirstWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 360);
        stage.setTitle("Запуск локального сервера");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
    //запуск главного окна
    public static void startServerWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(FirstWindow.class.getResource("ServerWindow.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Локальный сервер");
            stage.setScene(scene);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    LogWriter.getInstance().Log("Сервер выключен");
                    System.exit(0);
                }
            });
            stage.show();
        } catch (IOException e) {
            LogWriter.getInstance().Log("Ошибка при запуске главного окна " + e.getMessage());
        }
    }
    //запуск окна подсказки
    public static void startWindowWithText(){
        FXMLLoader fxmlLoader = new FXMLLoader(FirstWindow.class.getResource("Hint.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 480, 320);
            Stage stage = new Stage();
            stage.setTitle("Информация");
            stage.setScene(scene);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.exit(0);
                }
            });
            stage.show();
        } catch (IOException e) {
            LogWriter.getInstance().Log("Ошибка при запуске окна руководства: " + e.getMessage());
        }
    }
}