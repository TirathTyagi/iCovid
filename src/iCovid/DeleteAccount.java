package iCovid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DeleteAccount extends LoginPanel implements Initializable {
    @FXML
    private Label userLabel;
    @FXML
    private TextField passLabel;
    @FXML
    private TextField confPassLabel;
    String passEntered;
    String confPassEntered;
    Connection connectDel;
    ResultSet restGet;
    String dataPass;
    private Stage stage1;
    private Scene scene1;
    @FXML
    void subButton(ActionEvent event) throws SQLException, IOException {
        passEntered = passLabel.getText();
        confPassEntered = confPassLabel.getText();
        if(passEntered.equals(confPassEntered))
        {
            connectDel = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
            PreparedStatement connectPs = connectDel.prepareStatement("SELECT * FROM logininfo WHERE Username = ?;");
            connectPs.setString(1,userEntered);
            restGet = connectPs.executeQuery();
            while(restGet.next())
            {
                dataPass = restGet.getString("Password");
                System.out.println(dataPass);
            }
            if(dataPass.equals(passEntered))
            {
                PreparedStatement delAcc = connectDel.prepareStatement("DELETE FROM logininfo WHERE Username = ?");
                delAcc.setString(1,userEntered);
                delAcc.execute();
                JOptionPane.showMessageDialog(null,"Your profile has been deleted successfully.");
                Parent root = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
                stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene1 = new Scene(root);
                stage1.setScene(scene1);
                stage1.show();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Password Incorrect!!!! Not Found in database.");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Entered Passwords are not matching");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLabel.setText(userEntered);
    }

    public void backButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }
}
