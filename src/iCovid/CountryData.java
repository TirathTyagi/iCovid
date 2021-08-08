package iCovid;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CountryData implements Initializable {
    BufferedReader reader;
    HttpURLConnection connection;
    String line;
    Connection dataConnect;
    URL url;
    StringBuffer response = new StringBuffer();
    ObservableList CountryChoice = FXCollections.observableArrayList();
    ResultSet countres;
    String inf;
    String test;
    String rec;
    String decease;
    String country_Chosen;
    @FXML
    private ChoiceBox<?> countryChoice;

    @FXML
    private Button countsubButton;

    @FXML
    private Label countryName;

    @FXML
    private Label infectNum;

    @FXML
    private Label testedNum;

    @FXML
    private Label recoverNum;

    @FXML
    private Label deceaseNum;
    private Stage stage1;
    private Scene scene1;

    @FXML
    void countsubAction(ActionEvent event) throws SQLException {
        PreparedStatement stmt = dataConnect.prepareStatement("SELECT * FROM WorldData WHERE Country = ?");
        stmt.setString(1,country_Chosen);
        countres = stmt.executeQuery();
        while(countres.next())
        {
            inf = countres.getString("Infected");
            test = countres.getString("Tested");
            rec = countres.getString("Recovered");
            decease = countres.getString("Deceased");
        }
        countryName.setText(country_Chosen);
        if(test.equals("")) {
            testedNum.setText("NA");
        }
        else {
            testedNum.setText(test);
        }
        if(inf.equals(""))
        {
            infectNum.setText("NA");
        }
        else {
            infectNum.setText(inf);
        }
        if(rec.equals(""))
        {
            recoverNum.setText("NA");
        }
        else {
            recoverNum.setText(rec);
        }
        if(decease.equals(""))
        {
            deceaseNum.setText("NA");
        }
        else {
            deceaseNum.setText(decease);
        }

    }
    void countryFetch() throws IOException, SQLException {
        try {
            dataConnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try {
           url = new URL("https://api.apify.com/v2/key-value-stores/tVaYRsPHLjNdNBu7S/records/LATEST?disableRedirect=true");
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(5000);
        connection.setConnectTimeout(5000);
        int status = connection.getResponseCode();
        System.out.println(status);
        if(status > 299)
        {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while((line =reader.readLine())!=null);
            {
                response.append(line);
            }
            reader.close();
        }
        else
        {
            reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine())!=null)
            {
                response.append(line);
            }
            reader.close();
        }
        String data = response.toString();
        JSONArray jarr = new JSONArray(data);
        PreparedStatement deltable = dataConnect.prepareStatement("DROP TABLE IF EXISTS WorldData");
        deltable.executeUpdate();
        PreparedStatement newTable = dataConnect.prepareStatement("CREATE TABLE WorldData(Country TEXT, Infected VARCHAR(128),Tested VARCHAR(128),Recovered VARCHAR(128),Deceased VARCHAR(128));");
        newTable.executeUpdate();
        String country = null;
        String infected = null;
        String tested=null;
        String recovered=null;
        String deceased=null;
        for(int i = 0;i<jarr.length();i++) {
            JSONObject j2 = jarr.getJSONObject(i);
            country = j2.optString("country");
            infected = j2.optString("infected");
            tested = j2.optString("tested");
            recovered = j2.optString("recovered");
            deceased = j2.optString("deceased");
            CountryChoice.addAll(country);
            PreparedStatement addWorld = dataConnect.prepareStatement("INSERT INTO WorldData(Country,Infected,Tested,Recovered,Deceased) VALUES (?,?,?,?,?)");
            addWorld.setString(1, country);
            addWorld.setString(2, infected);
            addWorld.setString(3, tested);
            addWorld.setString(4, recovered);
            addWorld.setString(5, deceased);
            addWorld.executeUpdate();
        }
    }

    public static void main(String[] args) throws SQLException, IOException {
        CountryData c1 = new CountryData();
        c1.countryFetch();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            countryFetch();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        countryChoice.getItems().addAll(CountryChoice);
        countryChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                System.out.println("Option has been Changed");
                try{
                    dataConnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                country_Chosen = (String) countryChoice.getSelectionModel().getSelectedItem();
                System.out.println(country_Chosen);
            }
        });
    }

    public void letsBack(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }
}
