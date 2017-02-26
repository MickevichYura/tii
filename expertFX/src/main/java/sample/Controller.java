package sample;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.FileNotFoundException;

public class Controller {
    public Label question;
    public Label answer;
    public Button yes;
    public Button no;
    public JFXListView<JFXToggleButton> list;

    private Data data;

    @FXML
    public void initialize() throws FileNotFoundException {
        data = Expert.init();
        question.setText(data.getCharacteristics().get(data.getMinIndex()) + "?");
        JFXToggleButton jfxToggleButton;
        for (int i = 0; i < 10; i++){
            jfxToggleButton = new JFXToggleButton();
            jfxToggleButton.setText("qw " + i);
            list.getItems().add(jfxToggleButton);
        }
    }

    public void calculate(ActionEvent actionEvent) throws FileNotFoundException {
        Button button = (Button) actionEvent.getSource();
        String result = data.calculate(button.getText());
        question.setText(result);
    }

    public void clear(ActionEvent actionEvent) throws FileNotFoundException {
        initialize();
    }

    public void input(ActionEvent actionEvent) throws FileNotFoundException {
        initialize();
    }

}
