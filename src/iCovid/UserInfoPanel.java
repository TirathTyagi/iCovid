package iCovid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class UserInfoPanel extends LoginPanel implements Initializable {

    @FXML
    private Label nameyLabel = new Label();

    @FXML
    private Label userLabel= new Label();

    @FXML
    private Label dobLabel= new Label();

    @FXML
    private Label ageLabel= new Label();

    @FXML
    private Label phoneLabel= new Label();

    @FXML
    private Label emailLabel= new Label();

    @FXML
    private Label passLabel= new Label();

    @FXML
    private Button passUpdate;
    Connection connectus;
    ResultSet rest;
    static String Name;
    String Password;
    Date Dob;
    Long phone;
    String email;
    int age;
    String bday;
    private Stage stage1;
    private Scene scene1;
    private Parent root;
    @FXML
    void backButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }
    @FXML
    void updateAll(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("NewPassword.fxml"));
            stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene1 = new Scene(root);
            stage1.setScene(scene1);
            stage1.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    void updateAllLabels()
    {
        nameyLabel.setText(Name);
        userLabel.setText(userEntered);
        ageLabel.setText(String.valueOf(age));
        phoneLabel.setText(String.valueOf(phone));
        dobLabel.setText(bday);
        passLabel.setText(Password);
        emailLabel.setText(email);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            connectus = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
            PreparedStatement stmt = connectus.prepareStatement("SELECT * FROM logininfo WHERE Username = ?");
            stmt.setString(1,userEntered);
            rest = stmt.executeQuery();
            while(rest.next())
            {
                Name = rest.getString("Name");
                age = rest.getInt("Age");
                phone = rest.getLong("Phone_Number");
                Dob = rest.getDate("DateOfBirth");
                email = rest.getString("email");
                Password = rest.getString("Password");
                DateFormat dateform = new SimpleDateFormat("dd-MM-yyyy");
                bday = dateform.format(Dob);
            }
            updateAllLabels();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
