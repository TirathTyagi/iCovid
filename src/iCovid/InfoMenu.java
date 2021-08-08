package iCovid;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoMenu {
    private Stage stage1;
    private Scene scene1;
    private Parent root;
    @FXML
    void backAct(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }

    @FXML
    void indiaInfo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("IndiaEffect.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }

    @FXML
    void infectionInfo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("InfectionInfo.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }

    @FXML
    void precautionInfo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PrecautionsPage.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }

    @FXML
    void vaccineInform(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("InfoAboutVaccine.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }

    @FXML
    void worldInfo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("WorldEffect.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }

}
