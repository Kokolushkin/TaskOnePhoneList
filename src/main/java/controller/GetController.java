package controller;

import com.google.gson.JsonObject;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface GetController {
    HttpURLConnection getConnection(String stringURL) throws IOException;
    JsonObject getResponse( HttpURLConnection connection);
    ObservableList parseJsonResponse( JsonObject jsonResponse);
}
