package model;

import model.CountryInformation;

import java.util.ArrayList;
import java.util.List;

public class PhoneInforamtion {
    private List<String> phoneNumbers;

    private String countryName;
    private int countryNumber;

    public PhoneInforamtion( CountryInformation countryInformation){
        countryName = countryInformation.getName();
        countryNumber = countryInformation.getNumber();
        phoneNumbers = new ArrayList<>();
    }

    public List<String> getPhoneNumbers(){
        return phoneNumbers;
    }

    public String getCountryName () {
        return countryName;
    }

    public int getCountryNumber () {
        return countryNumber;
    }
}
