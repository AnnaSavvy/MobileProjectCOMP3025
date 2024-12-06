package com.anna.dailyroute;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

        // Verificar se o item foi concluído no dia atual
        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        boolean isTodayCompleted = todayDate.equals(currentItem.getCompletionDate());
        itemCheckBox.setChecked(isTodayCompleted);

        // Atualizar estado ao clicar no CheckBox
        itemCheckBox.setOnClickListener(v -> {
            boolean isChecked = itemCheckBox.isChecked();
            currentItem.setCompleted(isChecked);
            currentItem.setCompletionDate(isChecked ? todayDate : null); // Atualiza a data
            dbHelper.updateRoutineItem(currentItem);
        });

        return convertView;
    }
}
