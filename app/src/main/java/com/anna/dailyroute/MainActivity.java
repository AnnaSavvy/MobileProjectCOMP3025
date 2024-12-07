package com.anna.dailyroute;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText routineItemInput;
    private Button addButton;
    private ListView routineListView;
    private RoutineListAdapter adapter;
    private ArrayList<RoutineItem> routineList;
    private DatabaseHelper dbHelper;

    private TextView weatherTextView;


    private static final String API_KEY = "3962f42c213bf46b63fec96154325afa";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        routineItemInput = findViewById(R.id.routineItemInput);
        addButton = findViewById(R.id.addButton);
        routineListView = findViewById(R.id.routineListView);
        Button viewStatisticsButton = findViewById(R.id.viewStatisticsButton);
        weatherTextView = findViewById(R.id.weatherTextView);

        routineList = dbHelper.getAllRoutineItems();

        adapter = new RoutineListAdapter(this, routineList, dbHelper);
        routineListView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            String newItemName = routineItemInput.getText().toString();
            if (!newItemName.isEmpty()) {
                RoutineItem newItem = new RoutineItem(newItemName, false);
                dbHelper.addRoutineItem(newItem);
                routineList.add(newItem);
                adapter.notifyDataSetChanged();
                routineItemInput.setText("");
            }
        });

        viewStatisticsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            startActivity(intent);
        });

        fetchWeatherData("Toronto");
    }

    // Função para obter os dados de clima
    private void fetchWeatherData(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherApi api = retrofit.create(OpenWeatherApi.class);


        Call<WeatherResponse> call = api.getWeather(cityName, API_KEY, "metric");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        String city = weatherResponse.getName();
                        float temperature = weatherResponse.getMain().getTemp();
                        weatherTextView.setText("City: " + city + "\nTemperature: " + temperature + "°C");
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                weatherTextView.setText("Failed to retrieve weather data.");
            }
        });
    }
}