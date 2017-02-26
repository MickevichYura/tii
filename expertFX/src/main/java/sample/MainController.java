package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class MainController {

    @FXML
    private AnchorPane viewHolder;

    public void setView(Node node) {
        viewHolder.getChildren().setAll(node);
    }

}
