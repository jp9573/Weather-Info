package in.co.jaypatel.weatherapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jay on 2/4/18.
 */

public class Coord {

    @SerializedName("lon")
    private double lon;

    @SerializedName("lat")
    private double lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
