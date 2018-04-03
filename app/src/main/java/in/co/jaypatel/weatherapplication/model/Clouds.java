package in.co.jaypatel.weatherapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jay on 2/4/18.
 */

public class Clouds {
    @SerializedName("all")
    private double all;

    public double getAll() {
        return all;
    }

    public void setAll(double all) {
        this.all = all;
    }
}


