package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CountryInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetCountryController implements GetController {

    private static final String GET_COUNTRY_LIST = "https://onlinesim.ru/api/getFreeCountryList";
    HttpURLConnection connection;

    public ObservableList<CountryInformation> getCountryList() {
        try {
            connection = getConnection(GET_COUNTRY_LIST);
            ObservableList<CountryInformation> countryInformation = parseJsonResponse(getResponse(connection));
            connection.disconnect();
            return countryInformation;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return FXCollections.emptyObservableList();
        }
    }

    @Override
    public HttpURLConnection getConnection(String stringURL) throws IOException {
        URL url = new URL(stringURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        return connection;
    }

    @Override
    public JsonObject getResponse(HttpURLConnection connection){
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            return (JsonObject) JsonParser.parseString(content.toString());
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

        return new JsonObject();
    }

    @Override
    public ObservableList<CountryInformation> parseJsonResponse ( JsonObject jsonResponse ){
        ObservableList<CountryInformation> informations = FXCollections.observableArrayList();

        JsonArray array = jsonResponse.get("countries").getAsJsonArray();
        array.forEach(country -> {
            CountryInformation information = new CountryInformation();
            JsonObject countryJson = country.getAsJsonObject();

            information.setNumber(countryJson.get("country").getAsInt());
            information.setName(countryJson.get("country_text").getAsString());

            informations.add(information);
        });

        return informations;
    }
}
