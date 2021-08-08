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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class VaccineCentre extends VaccineInfo implements Initializable{
    Connection dataConnection;
    ResultSet rsr;
    Long Centre_ID;
    String Address;
    String State_Name;
    String District_Name;
    String Block_Name;
    long pincode;
    Time From;
    Time To;
    String Fee_Type;
    int dose1;
    int dose2;
    String VaccName;
    int agelim;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public Label centreLabel;

    @FXML
    public Label centrenameLabel;

    @FXML
    public Label addressLabel;
    @FXML
    public Label stateNameLabel;

    @FXML
    public Label districtNameLabel;

    @FXML
    public Label blockNameLabel;

    @FXML
    public Label pincodeLabel;

    @FXML
    private Label fromLabel;

    @FXML
    public Label toLabel;

    @FXML
    public Label feeLabel;

    @FXML
    public Label d1Label;

    @FXML
    public Label d2Label;

    @FXML
    private Button backButton;
    @FXML
    private Label vaccnameLabel;

    @FXML
    private Label agelimLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try{
            dataConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            PreparedStatement psdat = dataConnection.prepareStatement("SELECT * FROM vaccineinformation WHERE Name = ?");
            psdat.setString(1,centre_chosen);
            rsr = psdat.executeQuery();
            while(rsr.next())
            {
                Centre_ID = rsr.getLong("Centre_ID");
                System.out.println(Centre_ID);
                Address = rsr.getString("Address");
                State_Name = rsr.getString("State_Name");
                District_Name = rsr.getString("District_Name");
                Block_Name = rsr.getString("Block_Name");
                pincode = rsr.getLong("Pincode");
                From = rsr.getTime("From_Time");
                To = rsr.getTime("To_Time");
                Fee_Type = rsr.getString("Fee_Type");
                dose1 = rsr.getInt("Dose1");
                dose2 = rsr.getInt("Dose2");
                VaccName = rsr.getString("Vaccine");
                agelim = rsr.getInt("Min_Age_Limit");
            }
            centreLabel.setText(String.valueOf(Centre_ID));
            centrenameLabel.setText(centre_chosen);
            addressLabel.setText(Address);
            stateNameLabel.setText(State_Name);
            districtNameLabel.setText(District_Name);
            blockNameLabel.setText(Block_Name);
            pincodeLabel.setText(String.valueOf(pincode));
            fromLabel.setText(String.valueOf(From));
            toLabel.setText(String.valueOf(To));
            feeLabel.setText(Fee_Type);
            d1Label.setText(String.valueOf(dose1));
            d2Label.setText(String.valueOf(dose2));
            vaccnameLabel.setText(VaccName);
            agelimLabel.setText(String.valueOf(agelim));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    void backPress(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("VaccineInfo.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
