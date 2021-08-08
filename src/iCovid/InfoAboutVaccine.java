package iCovid;

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

public class InfoAboutVaccine implements Initializable {

    @FXML
    private JFXToggleButton toggleCoviShield;
    @FXML
    private Label covLabel;

    @FXML
    private TextArea covBox;

    @FXML
    private Label trialLabel;

    @FXML
    private TextArea trialBox;
    @FXML
    private ImageView covImg;
    @FXML
    private Label covLabel1;

    @FXML
    private TextArea covBox1;

    @FXML
    private ImageView coviImg;

    @FXML
    private Label trialLabel1;

    @FXML
    private TextArea trialBox1;
    private Stage stage1;
    private Scene scene1;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        covLabel1.setOpacity(0);
        covBox1.setOpacity(0);
        trialLabel1.setOpacity(0);
        trialBox1.setOpacity(0);
        coviImg.setOpacity(0);
        covLabel.setOpacity(1);
        covBox.setOpacity(1);
        trialBox.setOpacity(1);
        trialLabel.setOpacity(1);
        covImg.setOpacity(1);
        toggleCoviShield.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(toggleCoviShield.isSelected())
                {
                    covLabel.setOpacity(0);
                    covBox.setOpacity(0);
                    trialBox.setOpacity(0);
                    trialLabel.setOpacity(0);
                    covImg.setOpacity(0);
                    covLabel1.setOpacity(1);
                    covBox1.setOpacity(1);
                    trialLabel1.setOpacity(1);
                    trialBox1.setOpacity(1);
                    coviImg.setOpacity(1);
                }
                else
                {
                    covLabel1.setOpacity(0);
                    covBox1.setOpacity(0);
                    trialLabel1.setOpacity(0);
                    trialBox1.setOpacity(0);
                    coviImg.setOpacity(0);
                    covLabel.setOpacity(1);
                    covBox.setOpacity(1);
                    trialBox.setOpacity(1);
                    trialLabel.setOpacity(1);
                    covImg.setOpacity(1);
                }
            }
        });
    }
    @FXML
    void returnBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("InfoMenu.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }
}
