package org.translation;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
// CheckStyle: Wrong lexicographical order for 'java.util.HashMap' import (remove this comment once resolved)

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    // Task: pick appropriate instance variable(s) to store the data necessary for this class
    private final List<String[]> countryCodes = new ArrayList<>();

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            int lazy = 0;
            for (String line : lines) {
                if (lazy != 0) {
                    String[] lineArray = line.split("\t");
                    String[] addString = {lineArray[0], lineArray[2].toLowerCase()};
                    countryCodes.add(addString);
                }
                else {
                    lazy++;
                }
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // Task: update this code to use an instance variable to return the correct value
        for (String[] countryCode : countryCodes) {
            if (countryCode[1].equals(code)) {
                return countryCode[0];
            }
        }
        return code;
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // Task: update this code to use an instance variable to return the correct value
        for (String[] countryCode : countryCodes) {
            if (countryCode[0].equals(country)) {
                return countryCode[1];
            }
        }
        return country;
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // Task: update this code to use an instance variable to return the correct value
        return countryCodes.size();
    }
}
