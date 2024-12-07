package com.anna.dailyroute;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class StatisticsActivity extends AppCompatActivity {
    private TextView streakTextView;
    private TextView awardTextView;
    private AwardsManager awardsManager;

    private ProgressBar dailyProgressBar;
    private TextView dailyCompletionRateTextView;
    private CalendarView completionCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        streakTextView = findViewById(R.id.streakTextView);
        awardTextView = findViewById(R.id.awardTextView);
        awardsManager = new AwardsManager(this);
        Button backToMainButton = findViewById(R.id.backToMainButton);

        dailyProgressBar = findViewById(R.id.dailyProgressBar);
        dailyCompletionRateTextView = findViewById(R.id.dailyCompletionRateTextView);
        completionCalendarView = findViewById(R.id.completionCalendarView);

        String currentDate = getCurrentDate();
        updateDailyStatistics(currentDate);

        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        completionCalendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);

            Toast.makeText(StatisticsActivity.this, "Date selected: " + selectedDate, Toast.LENGTH_SHORT).show();

            updateDailyStatistics(selectedDate);
        });

    }
    private String getCurrentDate() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH) + 1; // Janeiro = 0, precisa somar 1
        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        return String.format("%04d-%02d-%02d", year, month, day);
    }
    private void updateStatistics(int rate) {
        int streak = 0;
        if (rate ==10)
        {
            streak = 1;
        }

        String award = awardsManager.getAward();

        streakTextView.setText("Current Streak: " + streak + " days");


        String motivationalMessage = streak > 1
                ? "Keep up the amazing work!"
                : "You're doing great, keep going!";

        awardTextView.setText(motivationalMessage);
    }

    private void updateDailyStatistics(String selectedDate) {
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();

        Cursor completedCursor = db.rawQuery(
                "SELECT COUNT(*) FROM routine WHERE date_completed = ? AND completed = 1",
                new String[]{selectedDate}
        );

        Cursor totalCursor = db.rawQuery(
                "SELECT COUNT(*) FROM routine WHERE date_completed = ?",
                new String[]{selectedDate}
        );

        int completedCount = 0;
        int totalCount = 10;

        if (completedCursor.moveToFirst()) {
            completedCount = completedCursor.getInt(0);
            Log.d("StatisticsActivity", "Completed count: " + completedCount);
        }


        completedCursor.close();
        totalCursor.close();
        db.close();

        int completionRate = totalCount > 0 ? (completedCount * 100) / totalCount : 0;
        Log.d("StatisticsActivity", "Completion rate for " + selectedDate + ": " + completionRate + "%");

        Toast.makeText(StatisticsActivity.this, "Date selected: " + selectedDate, Toast.LENGTH_SHORT).show();
        dailyProgressBar.setProgress(completionRate);
        dailyCompletionRateTextView.setText(completionRate + "%");

        updateStatistics(completedCount);

    }


}