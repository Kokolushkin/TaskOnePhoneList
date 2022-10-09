package view;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.PhoneInforamtion;

public class PhoneInformationTable extends TableView<PhoneInforamtion> {

    public PhoneInformationTable( ObservableList<PhoneInforamtion> items){
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);

        getColumns().addAll(createCountryNameColumn(),
                            createCountryNumberColumn(),
                            createPhoneNumberColumn());

        setItems(items);
    }

    private TableColumn<PhoneInforamtion, String> createCountryNameColumn(){
        TableColumn<PhoneInforamtion, String> countryNameColumn = new TableColumn<>("Country name");
        countryNameColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        return countryNameColumn;
    }

    private TableColumn<PhoneInforamtion, Integer> createCountryNumberColumn(){
        TableColumn<PhoneInforamtion, Integer> countryNumberColumn = new TableColumn<>("Country number");
        countryNumberColumn.setCellValueFactory(new PropertyValueFactory<>("countryNumber"));
        return countryNumberColumn;
    }

    private TableColumn<PhoneInforamtion, String> createPhoneNumberColumn(){
        TableColumn<PhoneInforamtion, String> phoneNumberColumn = new TableColumn<>("Phone number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumbers"));
        return phoneNumberColumn;
    }
}
