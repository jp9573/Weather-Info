package in.co.jaypatel.weatherapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jay on 2/4/18.
 */

public class Main {
    @SerializedName("temp")
    public double temp;

    @SerializedName("pressure")
    private double pressure;

    @SerializedName("humidity")
    private double humidity;

    @SerializedName("temp_min")
    private double tempMin;

    @SerializedName("temp_max")
    private double tempMax;

    @SerializedName("sea_level")
    private double seaLevel;

    @SerializedName("grnd_level")
    private double grndLevel;

    @SerializedName("temp_kf")
    private double tempKf;


    public String getTemp() {
        return getTemperatureInCelsius(temp);
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getTempMin() {
        return getTemperatureInCelsius(tempMin);
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public String getTempMax() {
        return getTemperatureInCelsius(tempMax);
    }

    String getTemperatureInCelsius(double tempF) {
        float temp = (float) (tempF - 273.15f);
        return String.format("%.1f", temp);
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public double getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(double grndLevel) {
        this.grndLevel = grndLevel;
    }

    public double getTempKf() {
        return tempKf;
    }

    public void setTempKf(double tempKf) {
        this.tempKf = tempKf;
    }
}
