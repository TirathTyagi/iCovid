package iCovid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdatePassword extends ForgotPasswordPanel {

    @FXML
    private TextField newPass;

    @FXML
    private TextField confNewPass;
    String newDataPass;
    String confyPass;
    private Stage stage;
    private Scene scene;
    private Parent root;
    ResultSet getPass1;
    String passy;
    @FXML
    void submitNewPass(ActionEvent event) throws SQLException, IOException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
        newDataPass = newPass.getText();
        confyPass = confNewPass.getText();
        if(newDataPass.equals(confyPass))
        {
            PreparedStatement getPass = connection.prepareStatement("SELECT * FROM logininfo WHERE Name = ?");
            getPass.setString(1,name);
            getPass1 = getPass.executeQuery();
            while(getPass1.next())
            {
                passy = getPass1.getString("Password");
            }
            if(!(newDataPass.equals(passy))) {
                PreparedStatement getData = connection.prepareStatement("UPDATE logininfo SET Password = ? WHERE Name = ?");
                getData.setString(1, newDataPass);
                getData.setString(2, name);
                getData.executeUpdate();
                Parent root = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"This password matches your old existing password. Please Choose a new password");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Passwords Do Not Match");
        }
    }
}
