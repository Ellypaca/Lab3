package org.translation;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // Task: pick appropriate instance variables for this class
    private final List<ArrayList<String[]>> jsonObjects = new ArrayList<>();
    private final String alpha3 = "alpha3";

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            // Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            // language codes,country codes, translate

            for (int i = 0; i < jsonArray.length(); i++) {
                ArrayList<String[]> temp = new ArrayList<>();

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String[] first = {alpha3, jsonObject.getString(alpha3)};
                temp.add(first);

                for (String key : jsonObject.keySet()) {
                    if (!"alpha2".equals(key) && !alpha3.equals(key) && !"id".equals(key)) {
                        String[] addedValue = {key, jsonObject.getString(key)};
                        temp.add(addedValue);

                    }
                }
                jsonObjects.add(temp);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        //  Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> languages = new ArrayList<>();
        for (int i = 0; i < jsonObjects.size(); i++) {

            if (jsonObjects.get(i).get(0)[1].equals(country)) {
                for (int j = 1; j < jsonObjects.get(i).size(); j++) {

                    languages.add(jsonObjects.get(i).get(j)[0]);
                }
            }
        }
        return languages;
    }

    @Override
    public List<String> getCountries() {
        // Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> countryCodes = new ArrayList<>();

        for (int i = 0; i < jsonObjects.size(); i++) {
            countryCodes.add(jsonObjects.get(i).get(0)[1]);
        }
        return countryCodes;
    }

    @Override
    public String translate(String country, String language) {
        // Task: complete this method using your instance variables as needed
        String translated = "";
        for (int i = 0; i < jsonObjects.size(); i++) {
            if (jsonObjects.get(i).get(0)[1].equals(country)) {
                for (int j = 1; j < jsonObjects.get(i).size(); j++) {
                    if (jsonObjects.get(i).get(j)[0].equals(language)) {
                        translated = jsonObjects.get(i).get(j)[1];
                    }
                }
            }
        }
        if (translated.isEmpty()) {
            return null;
        }

        return translated;
    }
}
