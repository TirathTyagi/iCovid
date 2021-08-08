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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewPassword extends UserInfoPanel{

    @FXML
    private TextField newpassLabel;

    @FXML
    private TextField confypassLabel;
    Connection connectData;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    void updatePass(ActionEvent event) throws SQLException, IOException {
        connectData = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
        PreparedStatement getData = connectData.prepareStatement("SELECT * FROM logininfo WHERE Username = ?");
        getData.setString(1,userEntered);
        getData.executeQuery();
        String passnew = newpassLabel.getText();
        String confpassnew = confypassLabel.getText();
        if(passnew.equals(confpassnew))
        {
            System.out.println(Name);
            System.out.println(userEntered);
            PreparedStatement newPass = connectData.prepareStatement("UPDATE logininfo SET Password = ? WHERE Username = ?");
            newPass.setString(1, passnew);
            newPass.setString(2, userEntered);
            newPass.executeUpdate();
            Parent root = FXMLLoader.load(getClass().getResource("UserInfoPanel.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"ERROR: Passwords do not match");
        }
    }

}
