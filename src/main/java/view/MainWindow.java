package view;

import controller.GetCountryController;
import controller.GetPhoneController;
import handler.SavePhoneInfoHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.CountryInformation;
import model.PhoneInforamtion;

public class MainWindow extends BorderPane {

    private static final double BOTTOM_MENU_SPACING = 5;

    private ComboBox<CountryInformation> countryComboBox;
    private PhoneInformationTable phoneInfoTable;

    public MainWindow() {
        countryComboBox = createCountryComboBox();
        Button phoneInfoButton = createGetPhoneInformationButton();
        phoneInfoTable = new PhoneInformationTable(FXCollections.emptyObservableList());
        Button saveButton = createSaveAllPhoneButton();
        Button getAllPhone = createGetAllPhoneButton();

        setCenter(phoneInfoTable);
        HBox bottomMenu = new HBox(getAllPhone, phoneInfoButton, countryComboBox, saveButton);
        bottomMenu.setSpacing(BOTTOM_MENU_SPACING);
        setBottom(bottomMenu);
    }

    private ComboBox<CountryInformation> createCountryComboBox() {
        GetCountryController countryController = new GetCountryController();
        ObservableList<CountryInformation> countryItems = countryController.getCountryList();

        ComboBox<CountryInformation> countryComboBox = new ComboBox<>(countryItems);
        countryComboBox.setValue(countryItems.get(0));

        return countryComboBox;
    }

    private Button createGetPhoneInformationButton(){
        Button phoneInfoButton = new Button("Get phone");

        phoneInfoButton.setOnAction(e -> {
            GetPhoneController phoneController = new GetPhoneController(countryComboBox.getValue());
            phoneInfoTable.setItems(phoneController.getPhoneList());
        });

        return phoneInfoButton;
    }

    private Button createGetAllPhoneButton(){
        Button getAllPhoneButton = new Button("Get all phone");

        getAllPhoneButton.setOnAction(e -> {
            ObservableList<PhoneInforamtion> allPhones = FXCollections.observableArrayList();
            GetPhoneController phoneController = new GetPhoneController();

            countryComboBox.getItems().forEach(country -> {
                phoneController.setCountryInformation(country);
                allPhones.addAll(phoneController.getPhoneList());
            });

            phoneInfoTable.setItems(allPhones);
        });

        return getAllPhoneButton;
    }

    private Button createSaveAllPhoneButton(){
        Button saveButton = new Button("Save all phone information to file");
        saveButton.setOnAction(new SavePhoneInfoHandler(countryComboBox.getItems()));
        return saveButton;
    }
}
