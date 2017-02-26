package sample;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ViewNavigator {
    public static final String MAIN = "main.fxml";
    public static final String EXPERT = "expert.fxml";
    public static final String INPUT = "input.fxml";

    private static MainController mainController;

    public static void setMainController(MainController mainController) {
        ViewNavigator.mainController = mainController;
    }

    public static void loadView(String fxml) {
        try {
            mainController.setView(FXMLLoader.load(ViewNavigator.class.getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
