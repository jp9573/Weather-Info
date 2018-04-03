package in.co.jaypatel.weatherapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView cityListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityListView = findViewById(R.id.city_list_view);

        final String[] cityList = getResources().getStringArray(R.array.city_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, cityList);

        cityListView.setAdapter(arrayAdapter);

        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(WeatherInfoActivity.isNetworkAvailable(getApplicationContext())) {
                    Intent intent = new Intent(MainActivity.this, WeatherInfoActivity.class);
                    intent.putExtra("city", cityList[position]);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Please connect to internet!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
