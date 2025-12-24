package org.example.localserver;

import javafx.application.Platform;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ServerWindowController {

    @FXML private TableView<User> tableUsers;
    @FXML private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colPassword;
    @FXML private TextField fieldUsername;
    @FXML private TextField fieldPassword;
    @FXML private TextField fieldSearch;
    @FXML private ListView<String> logListView;
    @FXML private CheckBox checkLog;
    @FXML private Label ConnectInfo;
    @FXML private Label statusLabel;

    private boolean hide = true;
    private ObservableList<User> userList;
    private FilteredList<User> filteredList;

    //выполняется при запуске окна
    @FXML
    public void initialize() {

        logListView.setItems(LogWriter.getInstance().getLogList());
        LogWriter.getInstance().setNeedToWriteFile(checkLog.isSelected());
        LogWriter.getInstance().setHeadList(logListView);
        LogWriter.getInstance().setStatusLabel(statusLabel);
        //обновление таблиц
        tableUsers.getColumns().clear();
        tableUsers.getColumns().addAll(colUsername, colPassword);
        tableUsers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colUsername.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getUsername()));
        colPassword.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPassword()));
        tableUsers.setPlaceholder(new Label("Пользователи отсутствуют"));
        refreshTable();

        // фильтрация по имени
        fieldSearch.textProperty().addListener((obs, oldVal, newVal) -> {
            String filter = newVal.toLowerCase().trim();
            filteredList.setPredicate(user ->
                    user.getUsername().toLowerCase().contains(filter)
            );
        });
    }

    //при добавлении пользователя
    @FXML
    private void onAddUser() {
        String username = fieldUsername.getText().trim();
        String password = fieldPassword.getText().trim();
        if (!username.isEmpty() && !password.isEmpty()) {
            if (Database.addUser(username, password)) {
                refreshTable();
                fieldUsername.clear();
                fieldPassword.clear();
            } else {
                showAlert("Ошибка", "Не удалось добавить пользователя.");
            }
        }
    }

    //при удалении пользователя
    @FXML
    private void onDeleteUser() {
        User selected = tableUsers.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (Database.deleteUser(selected.getUsername())) {
                refreshTable();
            } else {
                showAlert("Ошибка", "Не удалось удалить пользователя.");
            }
        }
    }

    //при обновлении пароля
    @FXML
    private void onUpdatePassword() {
        User selected = tableUsers.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String newPassword = fieldPassword.getText().trim();
            if (!newPassword.isEmpty()) {
                if (Database.updatePassword(selected.getUsername(), newPassword)) {
                    refreshTable();
                    fieldPassword.clear();
                } else {
                    showAlert("Ошибка", "Не удалось обновить пароль.");
                }
            }
        }
    }

    //обновить таблицу
    private void refreshTable() {
        userList = FXCollections.observableArrayList(Database.getAllUsers());
        filteredList = new FilteredList<>(userList, p -> true);
        tableUsers.setItems(filteredList);
    }

    //сообщение об ошибке
    private void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
    @FXML
    private void setNeedToWrite(){
        LogWriter.getInstance().Log("Запись логов сервера в файл - " + checkLog.isSelected());
        LogWriter.getInstance().setNeedToWriteFile(checkLog.isSelected());
    }
    //показ окна с руководством
    @FXML
    private void onHintButtonClicked(){
        FirstWindow.startWindowWithText();
    }
    //показывание сетевых параметров
    @FXML
    private void onConnectionInfoClicked(){
        ConnectInfo.setText("Сервер запущен(ip:port) на: " + ChatServer.getInstance().getIpAddress() + ":" + ChatServer.getInstance().getPORT());
        if(!ConnectInfo.isVisible()){
            ConnectInfo.setVisible(true);
        } else {
            ConnectInfo.setVisible(false);
        }
    }
}
