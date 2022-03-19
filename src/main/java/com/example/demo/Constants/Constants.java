package com.example.demo.Constants;

public class Constants {

    /**
     * Define all global constants here
     */

    public static final String METAR_EXTERNAL_SERVICE_ENDPOINT = "https://tgftp.nws.noaa.gov/data/observations/metar/stations/";
    public static final String TEXT_EXTENSION = ".TXT";


    /**
     * All exceptions messages here
     */

    public enum CoreError {
        MISSING_AIRPORT_CODE("No airportCode Provided"),
        MISSING_SUBSCRIPTION_NAME("No subscriptionName Provided"),
        NO_AIRPORT_FOUND("No airport found"),

        MISSING_AIRPORT_CODE_OR_METAR_DATA("No airportCode Provided or Metar Data");

        CoreError(String s) {
        }
    }
}
