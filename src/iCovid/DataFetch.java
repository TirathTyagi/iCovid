package iCovid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import org.json.*;
public class DataFetch {
    public static long totalindiaCases;
    public static long activeindiaCases;
    private static HttpURLConnection connection;
    public static long recoverindiaCases;
    public static long deceaseIndiaCases;
    public static long newactiveIndiaCases;
    public static long newrecoverIndiaCases;
    public static long newdeceasedIndiaCases;
    public static String dates;
    DataFetch() {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try {
            URL url = new URL("https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();

            }
            String data = responseContent.toString();
            JSONObject j1 = new JSONObject(data);
            JSONArray arr = j1.getJSONArray("regionData");
            totalindiaCases = j1.getLong("totalCases");
            activeindiaCases = j1.getLong("activeCases");
            recoverindiaCases = j1.getLong("recovered");
            deceaseIndiaCases = j1.getLong("deaths");
            newactiveIndiaCases = j1.getLong("activeCasesNew");
            newrecoverIndiaCases = j1.getLong("recoveredNew");
            newdeceasedIndiaCases = j1.getLong("deathsNew");
            dates = j1.getString("lastUpdatedAtApify");
            System.out.println(dates);
            System.out.println(totalindiaCases);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject j2 = arr.getJSONObject(i);
                String state = j2.getString("region");

                Double totalInfected = j2.getDouble("totalInfected");

                Double activeCases = j2.getDouble("activeCases");

                Double newInfected = j2.getDouble("newInfected");

                Double recovered = j2.getDouble("recovered");

                Double newRecovered = j2.getDouble("newRecovered");

                Double deceased = j2.getDouble("deceased");

                Double newDeceased = j2.getDouble("newDeceased");

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }
}