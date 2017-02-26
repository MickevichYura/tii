package sample;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.FileNotFoundException;

public class InputController {
    public JFXListView<JFXToggleButton> list;

    @FXML
    public void initialize() throws FileNotFoundException {
        JFXToggleButton jfxToggleButton;
        for (int i = 0; i < 10; i++) {
            jfxToggleButton = new JFXToggleButton();
            jfxToggleButton.setText("qw " + i);
            list.getItems().add(jfxToggleButton);
        }
    }


    @FXML
    void nextPane(ActionEvent event) {
        ViewNavigator.loadView(ViewNavigator.EXPERT);
    }
}
