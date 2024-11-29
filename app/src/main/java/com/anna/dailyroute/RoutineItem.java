package com.anna.dailyroute;

public class RoutineItem {
    private int id;
    private String name;
    private boolean completed;

    public RoutineItem() {}

    public RoutineItem(String name, boolean completed) {
        this.name = name;
        this.completed = completed;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}