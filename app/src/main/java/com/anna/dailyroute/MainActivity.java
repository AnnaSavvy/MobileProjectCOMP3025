package com.anna.dailyroute;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText routineItemInput;
    private Button addButton;
    private ListView routineListView;
    private RoutineListAdapter adapter;
    private ArrayList<RoutineItem> routineList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        routineItemInput = findViewById(R.id.routineItemInput);
        addButton = findViewById(R.id.addButton);
        routineListView = findViewById(R.id.routineListView);
        Button viewStatisticsButton = findViewById(R.id.viewStatisticsButton);

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
    }
}
