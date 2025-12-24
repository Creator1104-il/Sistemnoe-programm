package org.example.localclient;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ChatController {
    @FXML private ListView<String> chatList;
    @FXML private TextField inputField;
    @FXML private Button sendButton;
    Client client = Client.getInstance();

    @FXML
    public void initialize() {
        client.setMessageField(inputField);
        client.setButtonSend(sendButton);
        client.setMassageListView(chatList);
        chatList.setItems(client.getMassegaList());
    }
    @FXML
    protected void onAboutConnectedInfo(){
        AuthWindow.startWindowWithText(
                "Имя пользователя: " + client.getUsername() + "\n" +
                "ip-адрес: " + client.gethost() + "\n" +
                "порт: " + client.getPort()
        );
    }
    @FXML
    protected void onHintClicked(){
        AuthWindow.startWindowWithText(
                "В центре Вы можете увидеть историю общения, внизу находится поле для ввода сообщения, а внизу справа находится кнопка для его отправки"
        );
    }
}
