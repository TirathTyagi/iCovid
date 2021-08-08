package iCovid;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.util.Date;
import javafx.util.Duration;
import java.util.ResourceBundle;

public class HomePage extends DataFetch implements Initializable {

    @FXML
    private Label totalcaseCount;
    @FXML
    private Label activecaseCount;
    @FXML
    private Label recovercaseCount;
    @FXML
    private Label totaldeceaseCount;
    @FXML
    private Label recactiveCount;
    @FXML
    private Label recrecoverCount;
    @FXML
    private Label recdecCount;
    @FXML
    private Button vaccineButton;
    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private LineChart<?, ?> covGraph;
    int count = 0;

    @FXML
    private NumberAxis Cases;
    @FXML
    private CategoryAxis Time;
    @FXML
    private Stage stage1;
    private Scene scene1;
    private Parent root;
    Connection connection;
    @FXML
    private Button otherCountButton;
    @FXML
    private LineChart<?, ?> recGraph1;

    @FXML
    private CategoryAxis Timeline2;

    @FXML
    private NumberAxis Recovery1;

    @FXML
    private Button stateButton;
    @FXML
    private ToggleButton toggleSwitch;
    @FXML
    private BarChart<?, ?> dailyCasGraph;

    @FXML
    private BarChart<?, ?> dailyRecGraph;

    @FXML
    private Button selfAssess;
    @FXML
    private Label dateShow;

    @FXML
    void covInfo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("InfoMenu.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }


    @FXML
    void logOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }

    @FXML
    void userInfo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserInfoPanel.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }
    @FXML
    void selfAssessAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SelfAssessment.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }
    @FXML
    void stateWiseRep(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StateWisePanel.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }
    @FXML
    void deleteAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DeleteAccount.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }
    @FXML
    void otherCountAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CountryData.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }
    @FXML
    void vaccineAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("VaccineInfo.fxml"));
        stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();
    }
    ResultSet timeRes;
    PreparedStatement timeps;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        dateLabel.setText("Date: "+LocalDate.now());
        Timeline currenttime = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime ct = LocalTime.now();
            timeLabel.setText("Time: "+ct.getHour() + ":" + ct.getMinute() + ":" + ct.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        currenttime.setCycleCount(Animation.INDEFINITE);
        currenttime.play();
        try {
            Process p = Runtime.getRuntime().exec("python timelineData.py");
            System.out.println("Executed");
        } catch (IOException e) {
            e.printStackTrace();
        }
        dailyRecGraph.setOpacity(0);
        dailyCasGraph.setOpacity(0);
        covGraph.setOpacity(1);
        recGraph1.setOpacity(1);
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/timelinedatabase", "root","Tirathtyagi@112");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String date;
        long dconfirmed;
        long ddeceased;
        long drecovered;
        long tconfirmed;
        long tdeceased;
        long trecovered;
        try {
            timeps = connection.prepareStatement("SELECT * FROM timelinedata");
            timeRes = timeps.executeQuery();
            XYChart.Series series = new XYChart.Series();
            XYChart.Series series2 = new XYChart.Series();
            while (timeRes.next()) {
                date = timeRes.getString("Date");
                dconfirmed = timeRes.getLong("DailyConfirmed");
                ddeceased = timeRes.getLong("DailyDeceased");
                drecovered = timeRes.getLong("DailyRecovered");
                tconfirmed = timeRes.getLong("TotalConfirmed");
                tdeceased = timeRes.getLong("TotalDeceased");
                trecovered = timeRes.getLong("TotalRecovered");
                series.getData().add(new XYChart.Data(date, tconfirmed));
                series2.getData().add(new XYChart.Data(date,trecovered));
            }
            covGraph.getData().addAll(series);
            recGraph1.getData().addAll(series2);
            toggleSwitch.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                        if (toggleSwitch.isSelected()) {
                            if(count<1) {
                                ResultSet timeRes;
                                PreparedStatement timeps;
                                covGraph.setOpacity(0);
                                recGraph1.setOpacity(0);
                                try {
                                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/timelinedatabase", "root", "Tirathtyagi@112");
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                String date;
                                Long dconfirmed;
                                Long ddeceased;
                                Long drecovered;
                                Long tconfirmed;
                                Long tdeceased;
                                Long trecovered;
                                try {
                                    timeps = connection.prepareStatement("SELECT * FROM timelinedata");
                                    timeRes = timeps.executeQuery();
                                    XYChart.Series barseries1 = new XYChart.Series();
                                    XYChart.Series barseries2 = new XYChart.Series();
                                    while (timeRes.next()) {
                                        date = timeRes.getString("Date");
                                        dconfirmed = timeRes.getLong("DailyConfirmed");
                                        ddeceased = timeRes.getLong("DailyDeceased");
                                        drecovered = timeRes.getLong("DailyRecovered");
                                        tconfirmed = timeRes.getLong("TotalConfirmed");
                                        tdeceased = timeRes.getLong("TotalDeceased");
                                        trecovered = timeRes.getLong("TotalRecovered");
                                        barseries1.getData().add(new XYChart.Data(date, dconfirmed));
                                        barseries2.getData().add(new XYChart.Data(date, drecovered));
                                    }
                                    dailyCasGraph.getData().addAll(barseries1);
                                    dailyRecGraph.getData().addAll(barseries2);
                                    dailyCasGraph.setOpacity(1);
                                    dailyRecGraph.setOpacity(1);
                                    count++;
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                            }
                            else
                            {
                                dailyCasGraph.setOpacity(1);
                                dailyRecGraph.setOpacity(1);
                                covGraph.setOpacity(0);
                                recGraph1.setOpacity(0);
                            }
                        }
                    else
                    {
                        dailyCasGraph.setOpacity(0);
                        dailyRecGraph.setOpacity(0);
                        covGraph.setOpacity(1);
                        recGraph1.setOpacity(1);
                    }

                }
            });
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        DataFetch d1 = new DataFetch();
        totalcaseCount.setText(String.valueOf(d1.totalindiaCases));
        activecaseCount.setText(String.valueOf(d1.activeindiaCases));
        recovercaseCount.setText(String.valueOf(d1.recoverindiaCases));
        totaldeceaseCount.setText(String.valueOf(d1.deceaseIndiaCases));
        recrecoverCount.setText(String.valueOf(d1.newrecoverIndiaCases));
        recdecCount.setText(String.valueOf(d1.newdeceasedIndiaCases));
        dates = dates.substring(0,10);
        dateShow.setText(dates);
    }
}
