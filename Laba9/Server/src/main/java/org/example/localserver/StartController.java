package org.example.localserver;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StartController {
    @FXML
    private TextField portField;
    @FXML
    private Label errorLabel;

    @FXML
    protected void onSubmitClicked(){
        try {
            if(Integer.parseInt(portField.getText())>0) {
                ChatServer.getInstance().setPORT(Integer.parseInt(portField.getText()));
                new Thread(ChatServer.getInstance()).start();
                FirstWindow.startServerWindow();
                Stage stage = (Stage) portField.getScene().getWindow();
                stage.close();
            }
            else {
                errorLabel.setText("Порт должен быть больше 0");
            }
        } catch (Exception e){
            errorLabel.setText("Произошла непредвиденная ошибка. Проверьте правильность порта");
        }
    }
}
