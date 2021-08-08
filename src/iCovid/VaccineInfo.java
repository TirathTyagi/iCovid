package iCovid;

import com.sun.net.httpserver.Headers;
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
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.time.LocalDate;

public class VaccineInfo implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    HttpURLConnection connection;
    BufferedReader reader;
    StringBuffer response = new StringBuffer();
    String line;
    Connection databaseConnection;
    ResultSet res;
    String state_name;
    Connection conn;
    int state_id;
    ResultSet rs;
    String district_chosen;
    public static String centre_chosen;
    ObservableList<String> centre_select;
    static ObservableList states = FXCollections.observableArrayList();
    ObservableList districts = FXCollections.observableArrayList();
    ObservableList vaccCentres = FXCollections.observableArrayList();
    static int district_ID;
    static LocalDate dateSelect;
    @FXML
    private ListView<String> vaccList;
    @FXML
    private ChoiceBox<?> stateChoice;

    @FXML
    private ChoiceBox<?> districtChoice;

    @FXML
    private Button submitButton;
    @FXML
    private DatePicker datePick;
    @FXML
    private Button centreDetails;

    @FXML
    private Label vaccLabel;
    @FXML
    private Button btmButton;
    @FXML
    void btmProcess(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    void stateFetch() throws SQLException, IOException {
        databaseConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
        PreparedStatement dtable = databaseConnection.prepareStatement("DROP TABLE IF EXISTS VaccineInformation");
        dtable.executeUpdate();
        PreparedStatement cTable = databaseConnection.prepareStatement("CREATE TABLE VaccineInformation(ID INT NOT NULL PRIMARY KEY auto_increment,Centre_ID LONG,Name VARCHAR(128),Address VARCHAR(128),State_Name TEXT,District_Name TEXT,Block_Name TEXT,Pincode LONG,From_Time VARCHAR(128),To_Time VARCHAR(128),Fee_Type TEXT,Dose1 INT,Dose2 INT,Fee INT,Min_Age_Limit INT,Vaccine TEXT);");
        cTable.executeUpdate();
        Headers head = new Headers();
        URL url = new URL("https://cdn-api.co-vin.in/api/v2/admin/location/states");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36");
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();
        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        }
        String data = response.toString();
        JSONObject j1 = new JSONObject(data);
        JSONArray arrstate = j1.getJSONArray("states");
        PreparedStatement stateTable = databaseConnection.prepareStatement("CREATE TABLE IF NOT EXISTS State(state_name TEXT , state_id INT)");
        stateTable.execute();
        for (int i = 0; i < arrstate.length(); i++) {
            JSONObject j2 = arrstate.getJSONObject(i);
            state_name = j2.getString("state_name");
            state_id = j2.getInt("state_id");
            PreparedStatement addAtt = databaseConnection.prepareStatement("INSERT INTO State(state_name,state_id) VALUES (?,?)");
            addAtt.setString(1,state_name);
            addAtt.setInt(2,state_id);
            addAtt.execute();
            states.addAll(state_name);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            stateFetch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stateChoice.getItems().addAll(states);
        String statelists = (String) stateChoice.getValue();
        stateChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){

            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                System.out.println("Option Changed");

                try {
                    databaseConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                String state_chosen = (String) stateChoice.getSelectionModel().getSelectedItem();
                System.out.println(state_chosen);
                try {
                    PreparedStatement getID = databaseConnection.prepareStatement("SELECT * FROM State WHERE state_name = ?");
                    getID.setString(1,state_chosen);
                    System.out.println(getID);
                    res = getID.executeQuery();
                    while(res.next())
                    {
                        state_id = res.getInt("state_id");
                    }
                    System.out.println(state_id);
                    URL url1 = new URL("https://cdn-api.co-vin.in/api/v2/admin/location/districts/"+state_id);
                    connection = (HttpURLConnection) url1.openConnection();
                    connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36");
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    int stat = connection.getResponseCode();
                    StringBuffer distresp = new StringBuffer();
                    if(stat>299)
                    {
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        while((line = reader.readLine())!=null)
                        {
                            distresp.append(line);
                        }
                        reader.close();
                    }
                    else
                    {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        while((line=reader.readLine())!=null)
                        {
                            distresp.append(line);
                        }
                    reader.close();
                    }
                    String distData = distresp.toString();
                    JSONObject dis = new JSONObject(distData);
                    PreparedStatement dropTab = databaseConnection.prepareStatement("DROP TABLE IF EXISTS District");
                    dropTab.execute();
                    PreparedStatement disTab = databaseConnection.prepareStatement("CREATE TABLE District(District_Name TEXT,District_ID INT)");
                    disTab.execute();
                    JSONArray disinfo = dis.getJSONArray("districts");
                    for(int j = 0;j<disinfo.length();j++)
                    {
                        JSONObject diss = disinfo.getJSONObject(j);
                        String district_state = diss.getString("district_name");
                        int district_id = diss.getInt("district_id");
                        PreparedStatement addDat = databaseConnection.prepareStatement("INSERT INTO District(District_Name,District_ID) VALUES (?,?)");
                        addDat.setString(1,district_state);
                        addDat.setInt(2,district_id);
                        addDat.execute();
                    }
                    PreparedStatement disFetch = databaseConnection.prepareStatement("SELECT District_Name FROM District");
                    rs = disFetch.executeQuery();
                    districts.clear();
                    districtChoice.getItems().clear();
                    System.out.println("When Deleted: " + districts);
                    while(rs.next())
                    {
                        String distrname = rs.getString("District_Name");
                        districts.add(distrname);
                    }
                    System.out.println(districts);
                    districtChoice.getItems().addAll(districts);
                } catch (SQLException | MalformedURLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        districtChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                System.out.println("Option Changed");
                try {
                    databaseConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerinfo", "root", "Tirathtyagi@112");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                district_chosen = (String) districtChoice.getSelectionModel().getSelectedItem();
                System.out.println(district_chosen);
                try{
                    PreparedStatement disID = databaseConnection.prepareStatement(" SELECT * FROM District WHERE District_Name = ?");
                    disID.setString(1,district_chosen);
                    System.out.println(disID);
                    ResultSet rsdis = disID.executeQuery();
                    System.out.println(rsdis);

                    while(rsdis.next())
                    {
                        district_ID = rsdis.getInt("District_ID");
                    }
                    System.out.println(district_ID);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws SQLException, IOException {
        VaccineInfo v1 = new VaccineInfo();
        v1.stateFetch();
    }

    public void vaccSubmit(ActionEvent actionEvent) throws IOException, SQLException {
        String dateFormat = null;
        try {
            dateSelect = datePick.getValue();
            dateFormat = dateSelect.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
        catch(NullPointerException e )
        {
            JOptionPane.showMessageDialog(null,"Please fill the date column");
            return;
        }
        System.out.println(dateFormat);
        URL urlCentre = new URL("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?"+"district_id="+district_ID+"&"+"date="+dateFormat);
        connection = (HttpURLConnection) urlCentre.openConnection();
        connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36");
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        int centreStat = connection.getResponseCode();
        StringBuffer centreResp = new StringBuffer();
        if(centreStat >299)
        {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while((line = reader.readLine())!=null)
            {
                centreResp.append(line);
            }
            reader.close();
        }
        else
        {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine())!=null)
            {
                centreResp.append(line);
            }
            reader.close();
        }
        String centreData = centreResp.toString();
        JSONObject centreObj = new JSONObject(centreData);
        PreparedStatement dtable = databaseConnection.prepareStatement("DROP TABLE IF EXISTS VaccineInformation");
        dtable.executeUpdate();
        PreparedStatement cTable = databaseConnection.prepareStatement("CREATE TABLE VaccineInformation(ID INT NOT NULL PRIMARY KEY auto_increment,Centre_ID LONG,Name VARCHAR(128),Address VARCHAR(128),State_Name TEXT,District_Name TEXT,Block_Name TEXT,Pincode LONG,From_Time VARCHAR(128),To_Time VARCHAR(128),Fee_Type TEXT,Dose1 INT,Dose2 INT,Fee INT,Min_Age_Limit INT,Vaccine TEXT);");
        cTable.executeUpdate();
        JSONArray centrearr = centreObj.getJSONArray("sessions");
        if(centrearr.length() > 0)
        {
            for (int k = 0; k < centrearr.length(); k++) {
                JSONObject centrearr2 = centrearr.getJSONObject(k);
                String centreName = centrearr2.getString("name");
                Long centreID = centrearr2.getLong("center_id");
                String address = centrearr2.getString("address");
                String state_name = centrearr2.getString("state_name");
                String district_name = centrearr2.getString("district_name");
                String Block_Name = centrearr2.getString("block_name");
                Long pincode = centrearr2.getLong("pincode");
                String from_time = centrearr2.getString("from");
                String To_Time = centrearr2.getString("to");
                String Fee_Type = centrearr2.getString("fee_type");
                int dose1 = centrearr2.getInt("available_capacity_dose1");
                int dose2 = centrearr2.getInt("available_capacity_dose2");
                int fee = centrearr2.getInt("fee");
                int min_age = centrearr2.getInt("min_age_limit");
                String vaccine = centrearr2.getString("vaccine");
                PreparedStatement putDat = databaseConnection.prepareStatement("INSERT INTO vaccineinformation(Centre_ID,Name,Address,State_Name,District_Name,Block_Name,Pincode,From_Time,To_Time,Fee_Type,Dose1,Dose2,Fee,Min_Age_Limit,Vaccine) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
                putDat.setLong(1,centreID);
                putDat.setString(2,centreName);
                putDat.setString(3,address);
                putDat.setString(4,state_name);
                putDat.setString(5,district_name);
                putDat.setString(6,Block_Name);
                putDat.setLong(7,pincode);
                putDat.setString(8,from_time);
                putDat.setString(9,To_Time);
                putDat.setString(10,Fee_Type);
                putDat.setInt(11,dose1);
                putDat.setInt(12,dose2);
                putDat.setInt(13,fee);
                putDat.setInt(14,min_age);
                putDat.setString(15,vaccine);
                putDat.execute();
            }
        }
        else
        {
            System.out.println("Data Not present for given date");
        }
        PreparedStatement disChose = databaseConnection.prepareStatement("SELECT * FROM vaccineinformation WHERE District_Name = ?");
        disChose.setString(1,district_chosen);
        ResultSet resDischo = disChose.executeQuery();
        vaccCentres.clear();
        vaccList.getItems().clear();
        while(resDischo.next())
        {
            String centre_Name = resDischo.getString("Name");
            vaccCentres.add(centre_Name);
        }
        vaccList.getItems().addAll(vaccCentres);
        if(vaccCentres.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"There are no centres available");
        }
        else
        {
            centreDetails.setDisable(false);
            vaccList.setOpacity(1);
            vaccLabel.setOpacity(1);
            centreDetails.setOpacity(1);
        }
    }

    @FXML
    void centreDet(ActionEvent event) throws IOException {
        centre_select = vaccList.getSelectionModel().getSelectedItems();
        centre_chosen = centre_select.get(0);
        Parent root = FXMLLoader.load(getClass().getResource("VaccineCentre.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}