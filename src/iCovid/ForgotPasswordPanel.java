package iCovid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

public class ForgotPasswordPanel {

    @FXML
    private TextField userCheck;

    @FXML
    private TextField nameCheck;

    @FXML
    private Button subChecking;
    static String name;
    String user;
    static String dataname;
    static String datauser;
    Connection connection;
    ResultSet resget;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    void subCheck(ActionEvent event) throws SQLException, IOException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
        name = nameCheck.getText();
        user = userCheck.getText();
        PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM logininfo WHERE Name = ?");
        ps1.setString(1, name);
        resget = ps1.executeQuery();
        while (resget.next()) {
            dataname = resget.getString("Name");
            datauser = resget.getString("Username");
        }
        System.out.println(dataname + "\t" + datauser);
        System.out.println(name + "\t" + user);
        if (dataname.equals(name) && datauser.equals(user)) {
            Parent root = FXMLLoader.load(getClass().getResource("VerifyCredential.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            JOptionPane.showMessageDialog(null, "No profile found with such Username/Name");
        }
    }
}
