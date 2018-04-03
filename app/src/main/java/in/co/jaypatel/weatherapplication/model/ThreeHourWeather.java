package in.co.jaypatel.weatherapplication.model;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jay on 2/4/18.
 */

public class ThreeHourWeather {

    @SerializedName("dt")
    private Long dt;

    @SerializedName("main")
    private Main mMain;

    @SerializedName("weather")
    private List<Weather> mWeatherArray;

    @SerializedName("clouds")
    private Clouds mClouds;

    @SerializedName("wind")
    private Wind mWind;

    @SerializedName("sys")
    private Sys mSys;

    @SerializedName("dt_txt")
    private String dtTxt;

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return mMain;
    }

    public void setMain(Main main) {
        mMain = main;
    }

    public List<Weather> getWeatherArray() {
        return mWeatherArray;
    }

    public void setWeatherArray(List<Weather> weatherArray) {
        mWeatherArray = weatherArray;
    }

    public Clouds getClouds() {
        return mClouds;
    }

    public void setClouds(Clouds clouds) {
        mClouds = clouds;
    }

    public Wind getWind() {
        return mWind;
    }

    public void setWind(Wind wind) {
        mWind = wind;
    }

    public Sys getSys() {
        return mSys;
    }

    public void setSys(Sys sys) {
        mSys = sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }
}
