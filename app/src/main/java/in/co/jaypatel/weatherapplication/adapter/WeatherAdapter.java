package in.co.jaypatel.weatherapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.co.jaypatel.weatherapplication.R;
import in.co.jaypatel.weatherapplication.model.Main;
import in.co.jaypatel.weatherapplication.model.ThreeHourWeather;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {

    private List<ThreeHourWeather> data;
    static Context mContext;


    public WeatherAdapter(List<ThreeHourWeather> data, Context context) {
        this.data = data;
        this.mContext = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView temp, day;
        public ImageView image;

        public MyViewHolder(View v) {
            super(v);

            temp = v.findViewById(R.id.rl_temp);
            day = v.findViewById(R.id.rl_day);
            image = v.findViewById(R.id.rl_image);
        }
    }

    @Override
    public WeatherAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String dateTxt = data.get(position).getDtTxt();
        String split[] = dateTxt.split(" ");

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputDateStr = split[0];
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        String todayDt = df.format(today);

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        String tomorrowDt = df.format(tomorrow);

        String day = "";
        if(outputDateStr.equals(todayDt)) {
            day = "Today";
        }else if (outputDateStr.equals(tomorrowDt)) {
            day = "Tomorrow";
        }else {
            day = outputDateStr;
        }

        holder.day.setText(day + "\nTime: " + split[1].substring(0,5));
        Main main = data.get(position).getMain();
        String line = main.getTempMax() + " / " + main.getTempMin();
        holder.temp.setText(line + (char) 0x00B0 +"C");

        Picasso.get().load("http://openweathermap.org/img/w/" + data.get(position).getWeatherArray().get(0).getIcon() + ".png").placeholder(R.mipmap.ic_launcher).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ChangeData {
        void setData(String temp, String weatherText);
    }
}

