package com.anna.dailyroute;

import android.content.Context;
import android.content.SharedPreferences;

public class AwardsManager {
    private static final String PREF_NAME = "AwardsPrefs";
    private static final String KEY_STREAK = "streak";
    private SharedPreferences prefs;

    public AwardsManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void incrementStreak() {
        int currentStreak = prefs.getInt(KEY_STREAK, 0);
        prefs.edit().putInt(KEY_STREAK, currentStreak + 1).apply();
    }

    public void resetStreak() {
        prefs.edit().putInt(KEY_STREAK, 0).apply();
    }

    public int getStreak() {
        return prefs.getInt(KEY_STREAK, 0);
    }

    public String getAward() {
        int streak = getStreak();
        if (streak >= 30) return "Gold Medal";
        if (streak >= 20) return "Silver Medal";
        if (streak >= 10) return "Bronze Medal";
        return "No award yet";
    }
}