package sample;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputController {
    public JFXListView<JFXCheckBox> list;
    public TextField characteristicsTextField;
    public TextField subjectsTextField;
    public Button characteristicsAddButton;
    public Button subjectsAddButton;

    private List<List<Integer>> matrix;
    private List<String> characteristics;
    private List<String> subjects;

    @FXML
    public void initialize() throws FileNotFoundException {
        matrix = new ArrayList<>();
        characteristics = new ArrayList<>();
        subjects = new ArrayList<>();
    }


    void nextPane() {
        ViewNavigator.loadView(ViewNavigator.EXPERT);
    }

    public void addCharacteristic(ActionEvent actionEvent) {
        String text = characteristicsTextField.getText();
        if (!text.equals("") && !characteristics.contains(text)) {
            characteristics.add(text);
        }
        characteristicsTextField.clear();
        updateList();
    }

    public void addSubject(ActionEvent actionEvent) {
        String text = subjectsTextField.getText();
        if (!text.equals("") && !subjects.contains(text)) {
            subjects.add(text);
        }
        subjectsTextField.clear();
        updateList();
    }

    public void updateList() {
        JFXCheckBox jfxToggleButton;
        list.getItems().clear();
        if (characteristics.size() > 0 && subjects.size() > 0) {
            for (String subject : subjects)
                for (String s : characteristics) {
                    jfxToggleButton = new JFXCheckBox();
                    jfxToggleButton.prefWidth(500);
                    jfxToggleButton.setText(subject + " имеет " + s + "?");
                    list.getItems().add(jfxToggleButton);
                }
        }
    }

    public void saveData(ActionEvent actionEvent) {
        int size = list.getItems().size();
        int subjectsSize = subjects.size();

        for (int i = 0; i < characteristics.size(); i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < subjects.size(); j++) {
                matrix.get(i).add(0);
            }
        }

        int temp;
        int count = 0;
        for (int i = 0; i < subjects.size(); i++) {
            for (int j = 0; j < characteristics.size(); j++) {
                temp = 0;
                if (list.getItems().get(count++).isSelected()) {
                    temp = 1;
                }
                matrix.get(j).set(i, temp);
            }
        }

        System.out.println(matrix);

        try {
            Expert.write(new Data(matrix, characteristics, subjects));
        } catch (IOException e) {
            e.printStackTrace();
        }
        matrix.clear();
        nextPane();
    }

    public void cancel(ActionEvent actionEvent) {
        nextPane();
    }
}
