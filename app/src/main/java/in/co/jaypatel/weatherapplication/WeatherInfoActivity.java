package in.co.jaypatel.weatherapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.jaypatel.weatherapplication.adapter.WeatherAdapter;
import in.co.jaypatel.weatherapplication.model.ThreeHourForecast;
import in.co.jaypatel.weatherapplication.model.ThreeHourWeather;

public class WeatherInfoActivity extends AppCompatActivity {

    TextView temp, city, weatherText, pressure, windSpeed, humidity;
    RecyclerView recyclerView;
    ImageView icon;
    private static ProgressDialog pDialog;
    WeatherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        temp = findViewById(R.id.textViewTemp);
        city = findViewById(R.id.textViewCity);
        weatherText = findViewById(R.id.textViewSituation);
        pressure = findViewById(R.id.textViewPresssure);
        windSpeed = findViewById(R.id.textViewWind);
        humidity = findViewById(R.id.textViewHumidity);
        icon = findViewById(R.id.imageViewIcon);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        String city = getIntent().getStringExtra("city");
        if(city != null && !city.equals("")) {
            if(isNetworkAvailable(this)) {
                makeWeatherRequest(city);
            }else {
                Toast.makeText(this, "Please connect to internet!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void showProgressDialog(Context context, String message) {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(message);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public static void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    void makeWeatherRequest(String city) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + city.toLowerCase() + ",in&appid=3041c9a8f663350131480632cb0d4cfb";

        showProgressDialog(WeatherInfoActivity.this, "Loading information");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonResponse(response);
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        dismissProgressDialog();
                        Toast.makeText(getApplicationContext(), "err " + error, Toast.LENGTH_LONG).show();
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    void parseJsonResponse(JSONObject response) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String str = response.toString();
        InputStream dataInputStream = new ByteArrayInputStream(str.getBytes());

        Reader dataInput = new InputStreamReader(dataInputStream);

        ThreeHourForecast gsonArrays = gson.fromJson(dataInput, ThreeHourForecast.class);

        city.setText(gsonArrays.getCity().getName());

        for(ThreeHourWeather threeHourWeather : gsonArrays.getThreeHourWeatherArray()) {
            try {
                String dateTxt = threeHourWeather.getDtTxt();
                String split[] = dateTxt.split(" ");

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String strCurrentTime = sdf.format(new Date());
                Date currentTime = sdf.parse(strCurrentTime);
                Date intervalTime = sdf.parse(split[1].substring(0, 5));

                int currHr = Integer.valueOf(strCurrentTime.substring(0,2));
                int intervalHr = intervalTime.getHours();
                int diff = currHr - intervalHr;

                if (currentTime.after(intervalTime) && (diff < 3)) {
                    temp.setText(threeHourWeather.getMain().getTemp() + (char) 0x00B0 +"C");
                    weatherText.setText(threeHourWeather.getWeatherArray().get(0).getMain());
                    pressure.setText(String.valueOf(threeHourWeather.getMain().getPressure()) + " hPa");
                    windSpeed.setText(String.valueOf(threeHourWeather.getWind().getSpeed()) + " m/s");
                    humidity.setText(String.valueOf(threeHourWeather.getMain().getHumidity()) + " %");
                    Picasso.get().load("http://openweathermap.org/img/w/" + threeHourWeather.getWeatherArray().get(0).getIcon() + ".png").placeholder(R.mipmap.ic_launcher).into(icon);
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        adapter = new WeatherAdapter(gsonArrays.getThreeHourWeatherArray(), this);
        recyclerView.setAdapter(adapter);
        dismissProgressDialog();
    }
}

class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private MySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
