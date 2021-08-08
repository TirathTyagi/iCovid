package iCovid;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;
public class LoginPanel extends Application {
    Connection connect;
    @FXML
    private TextField userlogField;
    @FXML
    private PasswordField passlogField;
    @FXML
    private Button logButton;
    @FXML
    private Button regButton;
    private Stage stage;
    private Scene scene;
    private Parent root;
    ResultSet userres;
    ResultSet passres;
    static String userEntered;
    @FXML
    void forgotAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ForgotPasswordPanel.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void logButtonAction(ActionEvent event) throws SQLException, IOException {
        try{
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        userEntered = userlogField.getText();
        String passEntered  = passlogField.getText();
        PreparedStatement userps = connect.prepareStatement("SELECT * FROM logininfo WHERE Username = ?");
        PreparedStatement passps = connect.prepareStatement("SELECT * FROM logininfo WHERE Password = ?");
        passps.setString(1,passEntered);
        userps.setString(1, userEntered);
        passres= passps.executeQuery();
        userres = userps.executeQuery();
        if(userres.next())
        {
            if(passres.next())
            {
                Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "INCORRECT USERNAME OR PASSWORD");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "INCORRECT USERNAME OR PASSWORD");
        }
    }

    @FXML
    void regButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("RegisterPanel.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
            primaryStage.setTitle("Login Panel");
            primaryStage.setScene(new Scene(root, 642, 469));
            primaryStage.show();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        launch(args);
    }
}



