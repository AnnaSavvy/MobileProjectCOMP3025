package com.anna.dailyroute;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.ProgressBar;

public class StatisticsActivity extends AppCompatActivity {
    private TextView streakTextView;
    private TextView awardTextView;
    private AwardsManager awardsManager;

    private ProgressBar monthlyProgressBar;
    private TextView monthlyCompletionRateTextView;
    private CalendarView completionCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        streakTextView = findViewById(R.id.streakTextView);
        awardTextView = findViewById(R.id.awardTextView);
        awardsManager = new AwardsManager(this);
        Button backToMainButton = findViewById(R.id.backToMainButton);

        monthlyProgressBar = findViewById(R.id.monthlyProgressBar);
        monthlyCompletionRateTextView = findViewById(R.id.monthlyCompletionRateTextView);
        completionCalendarView = findViewById(R.id.completionCalendarView);

        updateStatistics();
        updateMonthlyStatistics();

        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateStatistics() {
        int streak = awardsManager.getStreak();
        String award = awardsManager.getAward();

        streakTextView.setText("Current Streak: " + streak + " days");
        awardTextView.setText("Current Award: " + award);
    }

    private void updateMonthlyStatistics() {
        // TODO: Implement logic to calculate monthly completion rate
        int completionRate = 75; // Example value, replace with actual calculation
        monthlyProgressBar.setProgress(completionRate);
        monthlyCompletionRateTextView.setText(completionRate + "%");
    }


}