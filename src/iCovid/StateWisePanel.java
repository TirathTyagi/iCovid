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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class StateWisePanel implements Initializable {
    ObservableList statelist = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox<?> stateChoose;
    @FXML
    private Label stateLab;
    @FXML
    private Label activeLab;
    @FXML
    private Label newinfLab;
    @FXML
    private Label recLab;
    @FXML
    private Label newRecLab;
    @FXML
    private Label decLab;
    @FXML
    private Label newDecLab;
    @FXML
    private Label totalinfLab;
    public HttpURLConnection connection;
    BufferedReader reader;
    String line;
    StringBuffer response = new StringBuffer();
    Double activeCas;
    Double newInf;
    Double rec;
    Double newrec;
    Double decease;
    Double newDecease;
    Double totalinf;
    String state_chosen;
    private Stage stage1;
    private Scene scene1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            URL url1 = new URL("https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true");
            connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            int state = connection.getResponseCode();
            if (state > 299) {
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
            JSONArray stateArr = j1.getJSONArray("regionData");

            for (int i = 0; i < stateArr.length(); i++) {
                JSONObject j2 = stateArr.getJSONObject(i);
                String states = j2.getString("region");
                statelist.addAll(states);
            }
            stateChoose.getItems().addAll(statelist);
            stateChoose.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    state_chosen = (String) stateChoose.getSelectionModel().getSelectedItem();
                    for (int j = 0; j < stateArr.length(); j++) {
                        JSONObject j2 = stateArr.getJSONObject(j);
                        String states = j2.getString("region");
                        if (state_chosen.equals(states)) {
                            activeCas = j2.getDouble("activeCases");
                            newInf = j2.getDouble("newInfected");
                            rec = j2.getDouble("recovered");
                            newrec = j2.getDouble("newRecovered");
                            decease = j2.getDouble("deceased");
                            newDecease = j2.getDouble("newDeceased");
                            totalinf = j2.getDouble("totalInfected");

                        }
                    }
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void backButNow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }
    @FXML
    void subPress(ActionEvent event) {
        stateLab.setText(state_chosen);
        activeLab.setText(String.valueOf(activeCas));
        newinfLab.setText(String.valueOf(newInf));
        recLab.setText(String.valueOf(rec));
        newRecLab.setText(String.valueOf(newrec));
        decLab.setText(String.valueOf(decease));
        newDecLab.setText(String.valueOf(newDecease));
        totalinfLab.setText(String.valueOf(totalinf));
    }
}
