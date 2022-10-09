package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CountryInformation;
import model.PhoneInforamtion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPhoneController implements GetController {

    private static final String GET_PHONE_LIST = "https://onlinesim.ru/api/getFreePhoneList?country=%d";

    private HttpURLConnection connection;
    private CountryInformation countryInformation;

    public GetPhoneController(){
        countryInformation = new CountryInformation();
    }

    public GetPhoneController( CountryInformation countryInformation) {
        this.countryInformation = countryInformation;
    }

    public ObservableList<PhoneInforamtion> getPhoneList(){
        try {
            String stringURL = String.format(GET_PHONE_LIST, countryInformation.getNumber());
            connection = getConnection(stringURL);
            ObservableList<PhoneInforamtion> result = parseJsonResponse(getResponse(connection));
            connection.disconnect();
            return result;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return FXCollections.emptyObservableList();
        }
    }

    public JsonArray getPhoneListJson(){
        try {
            String stringURL = String.format(GET_PHONE_LIST, countryInformation.getNumber());
            connection = getConnection(stringURL);
            JsonArray result = getResponse(connection).get("numbers").getAsJsonArray();
            connection.disconnect();
            return result;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return new JsonArray();
        }
    }

    public void setCountryInformation( CountryInformation countryInformation){
        this.countryInformation = countryInformation;
    }

    @Override
    public HttpURLConnection getConnection ( String stringURL ) throws IOException {
        URL url = new URL(stringURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        return connection;
    }

    @Override
    public JsonObject getResponse ( HttpURLConnection connection ) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            return (JsonObject) JsonParser.parseString(content.toString());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return new JsonObject();
        }
    }

    @Override
    public ObservableList<PhoneInforamtion> parseJsonResponse ( JsonObject jsonResponse ) {
        ObservableList<PhoneInforamtion> informations = FXCollections.observableArrayList();

        JsonArray array = jsonResponse.get("numbers").getAsJsonArray();
        array.forEach(number -> {
            JsonObject numberJson = number.getAsJsonObject();
            PhoneInforamtion information = new PhoneInforamtion(countryInformation);

            information.getPhoneNumbers().add(numberJson.get("number").getAsString());

            informations.add(information);
        });

        return informations;
    }
}
