package com.anna.dailyroute;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;

public class RoutineListAdapter extends ArrayAdapter<RoutineItem> {
    private final ArrayList<RoutineItem> items;
    private final DatabaseHelper dbHelper;

    public RoutineListAdapter(Context context, ArrayList<RoutineItem> items, DatabaseHelper dbHelper) {
        super(context, 0, items);
        this.items = items;
        this.dbHelper = dbHelper;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        RoutineItem currentItem = items.get(position);

        TextView itemName = convertView.findViewById(R.id.itemName);
        CheckBox itemCheckBox = convertView.findViewById(R.id.itemCheckBox);

        itemName.setText(currentItem.getName());
        itemCheckBox.setChecked(currentItem.isCompleted());

        // Atualizar estado do item ao clicar no CheckBox
        itemCheckBox.setOnClickListener(v -> {
            currentItem.setCompleted(itemCheckBox.isChecked());
            dbHelper.updateRoutineItem(currentItem);
        });

        return convertView;
    }
}
