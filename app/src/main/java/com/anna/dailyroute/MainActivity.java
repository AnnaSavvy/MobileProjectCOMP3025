package com.anna.dailyroute;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText routineItemInput;
    private Button addButton;
    private ListView routineListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> routineList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        routineItemInput = findViewById(R.id.routineItemInput);
        addButton = findViewById(R.id.addButton);
        routineListView = findViewById(R.id.routineListView);

        routineList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, routineList);
        routineListView.setAdapter(adapter);

        loadRoutineItems();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = routineItemInput.getText().toString();
                if (!newItem.isEmpty()) {
                    dbHelper.addRoutineItem(new RoutineItem(newItem, false));
                    loadRoutineItems();
                    routineItemInput.setText("");
                }
            }
        });
    }

    private void loadRoutineItems() {
        routineList.clear();
        ArrayList<RoutineItem> items = dbHelper.getAllRoutineItems();
        for (RoutineItem item : items) {
            routineList.add(item.getName());
        }
        adapter.notifyDataSetChanged();
    }
}