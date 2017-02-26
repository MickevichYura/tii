package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Expert");

        stage.setScene(createScene(loadMainPane()));

        stage.show();
    }

    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Main.class.getResource(ViewNavigator.MAIN));
        Pane mainPane = loader.load(getClass().getResourceAsStream(ViewNavigator.MAIN));

        MainController mainMainController = loader.getController();

        ViewNavigator.setMainController(mainMainController);
        ViewNavigator.loadView(ViewNavigator.EXPERT);

        return mainPane;
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);

        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
