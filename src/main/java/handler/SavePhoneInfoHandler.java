package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controller.GetPhoneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import model.CountryInformation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.List;

public class SavePhoneInfoHandler implements EventHandler<ActionEvent> {

    List<CountryInformation> countries;
    GetPhoneController phoneController;

    public SavePhoneInfoHandler ( List<CountryInformation> countries ) {
        this.countries = countries;
        phoneController = new GetPhoneController();
    }

    @Override
    public void handle ( ActionEvent event ) {
        JsonArray jsonArray = new JsonArray();
        countries.forEach(country -> {
            phoneController.setCountryInformation(country);
            JsonObject json = new JsonObject();
            json.addProperty("country_name", country.getName());
            json.addProperty("country_number", country.getNumber());

            JsonArray numbers = phoneController.getPhoneListJson();
            json.add("numbers", numbers);
            jsonArray.add(json);
        });

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String stringJson = gson.toJson(jsonArray);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file");
        fileChooser.setInitialFileName("phone_list");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File selectedFile = fileChooser.showSaveDialog(((Node) (event.getSource())).getScene().getWindow());
        if (selectedFile != null) {
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(selectedFile.getAbsolutePath()))) {
                writer.write(stringJson);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
