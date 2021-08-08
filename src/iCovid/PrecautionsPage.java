package iCovid;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PrecautionsPage {
    private Stage stage1;
    private Scene scene1;
    private Parent root;
    @FXML
    void goBackButt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("InfoMenu.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }

}
