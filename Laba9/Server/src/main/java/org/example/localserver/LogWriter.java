package org.example.localserver;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWriter {
    private static final LogWriter writer = new LogWriter();
    private ObservableList<String> Logs = FXCollections.observableArrayList();
    private LogWriter(){}
    private boolean needToWriteFile;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss");
    private ListView list;
    private Label statusLabel;
    public static LogWriter getInstance(){
        return writer;
    }
    public ObservableList<String> getLogList(){
        return Logs;
    }
    public void setNeedToWriteFile(boolean b){
        needToWriteFile = b;
    }
    public void setHeadList(ListView l){
        list = l;
    }
    //сюда ошибки при записи логов в файл
    public void setStatusLabel(Label l){
        statusLabel = l;
    }
    public void Log(String s){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Logs.add("[" + sdf.format(new Date()) + "] " + s);
                //автопрокрутка listview
                if(list!=null){
                    list.scrollTo(Logs.size()-1);
                }
            }
        });
        if(needToWriteFile) {
            try (FileWriter writer = new FileWriter("Log.txt", true)) {
                //запись в файл если установлен флаг
                writer.write("[" + sdf.format(new Date()) + "] " + s + "\n");
                writer.flush();
            } catch (IOException e) {
                //нужно потому что обновляем из другого потока
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        statusLabel.setText("[" + sdf.format(new Date()) + "] Ошибка при записи логов");
                    }
                });
            }
        }
    }
}