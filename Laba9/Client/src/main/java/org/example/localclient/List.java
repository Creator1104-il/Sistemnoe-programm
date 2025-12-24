package org.example.localclient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class List {
    private ObservableList<String> users; //список всех пользователей
    private String name;
    public List(String name){
        this.users = FXCollections.observableArrayList();
        this.name = name;
    }
    //добавление нового пользователя
    public void addUToList(String t){
        users.add(t);
    }
    public int getSize(){
        return users.size();
    }
    //получение списка
    public ObservableList getList(){
        return users;
    }
}
