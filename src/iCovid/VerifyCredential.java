package iCovid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class VerifyCredential extends ForgotPasswordPanel{
    @FXML
    private DatePicker verifyDOB;

    @FXML
    private TextField verifyEmail;

    @FXML
    private TextField verifyPhone;
    LocalDate verifyDate;
    String email_id;
    Long phonenum;
    ResultSet setres;
    Long dataPhone;
    String dataEmail;
    String dataDO;
    String verifydate;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    void validateAll(ActionEvent event) throws SQLException, IOException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
        verifyDate = verifyDOB.getValue();
        verifydate = verifyDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        email_id = verifyEmail.getText();
        phonenum = Long.parseLong(verifyPhone.getText());
        PreparedStatement psp = connection.prepareStatement("SELECT * FROM logininfo WHERE Name = ?");
        psp.setString(1,name);
        setres = psp.executeQuery();
        while(setres.next())
        {
            dataDO = setres.getString("DateOfBirth");
            dataPhone = setres.getLong("Phone_Number");
            dataEmail = setres.getString("email");
        }
        System.out.println(dataDO+"\t"+verifydate);
        if(verifydate.equals(dataDO))
        {
            if(email_id.equals(dataEmail))
            {
                if(phonenum.equals(dataPhone))
                {
                    Parent root = FXMLLoader.load(getClass().getResource("UpdatePassword.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Phone Number is not Valid");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Email ID is not Valid");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Date Of Birth is not Valid");
        }
    }
}
