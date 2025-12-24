package org.example.localserver;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HintController {
    @FXML private Label label;
    @FXML
    public void initialize() {
        label.setText(" В окне логов вы можете видеть все действия сервера: пользователей, вошедших или вышедших из чата, а также сообщения которые они пишут. \n " +
                        " В окне работы с пользователем вы можете увидеть всех пользователей, которые зарегистрированы в базе данных. Вы можете добавить нового пользователя, заполнив поля логин, пароль и нажав на соответствующую кнопку. Вы также можете выбрать пользователя в таблице и заполнив поле пароль, нажать на кнопку сменить пароль для замены данных. Также можно удалить выбранного пользователя. \n" +
                        "Для того, чтобы подключиться к серверу, пользователь должен ввести локальный ip и порт, указанный при запуске сервера, и введя свои логин и пароль, заранее созданные вами.\n" +
                "ip: " + ChatServer.getInstance().getIpAddress() + "\n"
                +"порт: " + ChatServer.getInstance().getPORT());
    }
    @FXML
    public void onExitClicked(){
        Stage stage = (Stage) label.getScene().getWindow();
        stage.close();
    }
}
