package org.example.localclient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TextWindow {
    @FXML
    private Label TextLabel;
    @FXML
    public void initialize() {
        TextLabel.setText(Client.getInstance().getText());
    }

    @FXML
    protected void onExitClicked(){
        Stage stage = (Stage) TextLabel.getScene().getWindow();
        stage.close();
    }
}
