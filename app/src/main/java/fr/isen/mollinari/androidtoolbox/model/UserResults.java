package fr.isen.mollinari.androidtoolbox.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * http://www.jsonschema2pojo.org/
 */

public class UserResults {
    @SerializedName("results")
    @Expose
    private List<User> results = null;

    public List<User> getResults() {
        return results;
    }

    public void setResults(List<User> results) {
        this.results = results;
    }
}
