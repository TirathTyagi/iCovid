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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


import javafx.scene.image.ImageView;

public class RegisterPanel{
    static int countdigi(long num) {
        int count = 0;
        while (num != 0) {
            num = num / 10;
            ++count;
        }
        return count;
    }

    Connection connection;
    ResultSet res;
    ResultSet result;
    @FXML
    private TextField userField;

    @FXML
    private TextField passField;

    @FXML
    private TextField cpassField;

    @FXML
    private TextField mobField;

    @FXML
    private TextField emailField;
    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private DatePicker datePick;
    LocalDate birth;
    LocalDate current;
    Long time_duration;
    @FXML
    private ImageView covimg;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    void backInvoke(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void submitInvoke(ActionEvent event) {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String passWord = passField.getText();
            String user = userField.getText();
            String cPass = cpassField.getText();
            String em = emailField.getText();
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String dateFormat;
            String fomat;
            birth = datePick.getValue();
            dateFormat = birth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            current = current.now();
            fomat = current.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            time_duration = ChronoUnit.YEARS.between(birth,current);
            System.out.println(time_duration);
            System.out.println(fomat);
            System.out.println(dateFormat);
            Long mob = Long.parseLong(mobField.getText());
            double digi = countdigi(mob);
            System.out.println(digi);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM logininfo WHERE username = ?");
            ps.setString(1, user);
            res = ps.executeQuery();
            if (res.next() == false) {
                PreparedStatement ins = connection.prepareStatement("INSERT INTO logininfo (Name,Username,Age,Password,DateOfBirth,Phone_Number,email) VALUES (?,?,?,?,?,?,?)");
                if(!(nameField.getText().isEmpty())) {
                    ins.setString(1, name);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Name not filled");
                }
                if(!(userField.getText().isEmpty())) {
                    ins.setString(2, user);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Username is not filled");
                }
                if(!(ageField.getText().isEmpty())) {
                    ins.setInt(3, age);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Age not filled");
                }
                if(time_duration == age) {
                    ins.setDate(5, Date.valueOf(birth));
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Your age does not match the date added in the DOB column. Please verify your credentials and try again.");
                }
                if (digi == 10) {
                    ins.setDouble(6, mob);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Mobile Number");
                }
                if (em.contains("@") && em.contains(".")) {
                    ins.setString(7, em);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Email Address");
                }
                if (passWord.equals(cPass)) {
                    try {
                        ins.setString(4, passWord);
                        ins.execute();
                        System.out.println("ID created");
                        Parent root = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Passwords do not match!!!!!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username Already Exists");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}



