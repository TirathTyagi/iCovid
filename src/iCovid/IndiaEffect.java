package iCovid;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndiaEffect implements Initializable {

    @FXML
    private JFXToggleButton toggle2021;
    @FXML
    private Label label2020;

    @FXML
    private TextArea box2020;
    @FXML
    private ImageView img2020;
    @FXML
    private Label label2021;

    @FXML
    private JFXTextArea box2021;
    @FXML
    private ImageView img2021;
    private Stage stage1;
    private Scene scene1;
    private Parent root;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label2020.setOpacity(1);
        box2020.setOpacity(1);
        img2020.setOpacity(1);
        label2021.setOpacity(0);
        box2021.setOpacity(0);
        img2021.setOpacity(0);
        toggle2021.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(toggle2021.isSelected())
                {
                    label2020.setOpacity(0);
                    box2020.setOpacity(0);
                    img2020.setOpacity(0);
                    label2021.setOpacity(1);
                    box2021.setOpacity(1);
                    img2021.setOpacity(1);
                }
                else
                {
                    label2020.setOpacity(1);
                    box2020.setOpacity(1);
                    img2020.setOpacity(1);
                    label2021.setOpacity(0);
                    box2021.setOpacity(0);
                    img2021.setOpacity(0);
                }
            }
        });
    }
    @FXML
    void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("InfoMenu.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }
}
