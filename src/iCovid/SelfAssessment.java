package iCovid;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SelfAssessment extends LoginPanel{

    @FXML
    private TextField ageField;

    @FXML
    private RadioButton q1yes;

    @FXML
    private RadioButton q1no;

    @FXML
    private RadioButton q2covax;

    @FXML
    private RadioButton q2covi;

    @FXML
    private RadioButton q2notvac;

    @FXML
    private RadioButton q3yes;

    @FXML
    private RadioButton q3no;
    @FXML
    private JFXCheckBox q4cough;

    @FXML
    private JFXCheckBox q4fever;

    @FXML
    private JFXCheckBox q4sore;

    @FXML
    private JFXCheckBox q4chest;

    @FXML
    private JFXCheckBox q4body;

    @FXML
    private JFXCheckBox q4breathe;

    @FXML
    private JFXCheckBox q4smell;

    @FXML
    private JFXCheckBox q4pink;

    @FXML
    private JFXCheckBox q4hearing;

    @FXML
    private JFXCheckBox q4stomach;

    @FXML
    private JFXCheckBox q4none;

    @FXML
    private JFXCheckBox q5diab;

    @FXML
    private JFXCheckBox q5hypertension;

    @FXML
    private JFXCheckBox q5lung;

    @FXML
    private JFXCheckBox q5heart;

    @FXML
    private JFXCheckBox q5kidney;

    @FXML
    private JFXCheckBox q5asthma;

    @FXML
    private JFXCheckBox q5none;

    @FXML
    private RadioButton q6yes;

    @FXML
    private RadioButton q6no;
    @FXML
    private ToggleGroup q1Group;

    @FXML
    private ToggleGroup q2Group;
    @FXML
    private ToggleGroup q3Group;
    @FXML
    private ToggleGroup q6Group;

    @FXML
    private Button subAnswers;
    static int chance = 0;
    Connection connection;
    ResultSet res1;
    String datauser;
    @FXML
    private Button backButt;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    void backAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void subActionAns(ActionEvent event) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM logininfo WHERE Username = ?");
        ps.setString(1, userEntered);
        res1 = ps.executeQuery();
        while (res1.next()) {
            datauser = res1.getString("Age");
        }
        String age = ageField.getText();
        if (age.equals(datauser)) {
            {
                if (q1no.isSelected()) {
                    chance += 10;
                }
                if (q3yes.isSelected()) {
                    chance -= 5;
                }
                if (q4cough.isSelected()) {
                    chance += 2;
                }
                if (q4fever.isSelected()) {
                    chance += 2;
                }
                if (q4sore.isSelected()) {
                    chance += 2;
                }
                if (q4chest.isSelected()) {
                    chance += 5;
                }
                if (q4body.isSelected()) {
                    chance += 4;
                }
                if (q4breathe.isSelected()) {
                    chance += 5;
                }
                if (q4smell.isSelected()) {
                    chance += 3;
                }
                if (q4pink.isSelected()) {
                    chance += 3;
                }
                if (q4hearing.isSelected()) {
                    chance += 3;
                }
                if (q4stomach.isSelected()) {
                    chance += 3;
                }
                if (q5diab.isSelected()) {
                    chance += 5;
                }
                if (q5hypertension.isSelected()) {
                    chance += 3;
                }
                if (q5lung.isSelected()) {
                    chance += 10;
                }
                if (q5heart.isSelected()) {
                    chance += 10;
                }
                if (q5kidney.isSelected()) {
                    chance += 8;
                }
                if (q5asthma.isSelected()) {
                    chance += 10;
                }
                if (q6yes.isSelected()) {
                    chance += 15;
                }
                if (chance >= 0 && chance < 25) {
                    JOptionPane.showMessageDialog(null, "You have low risk of infection. Still it is advisable to remain in your homes and maintain social distancing.");
                } else if (chance >= 25 && chance < 60) {
                    JOptionPane.showMessageDialog(null, "You have moderate risk of infection. Please stay inside your home and don't go out until and unless it is very necessary.");
                } else if (chance >= 60) {
                    JOptionPane.showMessageDialog(null, "You have high risk of infection. Please consult your doctor ASAP.");
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Age does not match");
        }
    }
}
