package org.example.localclient;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AuthController {

    @FXML private TextField ipField;
    @FXML private TextField portField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML private Button connectButton;
    Client client = Client.getInstance();
    //выполняется при запуске кода
    @FXML
    public void initialize() {
        Client.getInstance().setStatusLabel(statusLabel);
    }
    @FXML
    private void onConnectClicked() {
        if((!ipField.getText().isEmpty()) && (!portField.getText().isEmpty()) && (!usernameField.getText().isEmpty()) && (!passwordField.getText().isEmpty())){
            client.setHost(ipField.getText().trim());
            client.setPort(Integer.parseInt(portField.getText().trim()));
            client.setUsername(usernameField.getText().trim());
            client.setPassword(passwordField.getText().trim());
            new Thread(Client.getInstance()).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                statusLabel.setText("Небольшая ошибка, ничего страшного");
            }
            if(client.isConnected()){
                statusLabel.setText("Подключено");
                AuthWindow.startClient();
                Stage stage = (Stage) connectButton.getScene().getWindow();
                stage.close();
            }
            else {
                statusLabel.setText("Ошибка доступа. Проверьте логин и пароль");
            }
        }
        else {
            statusLabel.setText("Все поля должны быть заполнены!");
        }
    }
}