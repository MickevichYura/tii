package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.FileNotFoundException;

public class ExpertController {
    public Label question;
    public Button yes;
    public Button no;

    private Data data;

    @FXML
    public void initialize() throws FileNotFoundException {
        data = Expert.init();
        question.setText(data.getCharacteristics().get(data.getMinIndex()) + "?");
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

    @FXML
    void nextPane(ActionEvent event) {
        ViewNavigator.loadView(ViewNavigator.INPUT);
    }
}
