package com.anna.dailyroute;

public class RoutineItem {
    private int id;
    private String name;
    private boolean isCompleted;
    private String dateCompleted; // Novo campo
    private String completionDate;

    public RoutineItem() {
    }

    public RoutineItem(String name, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }
}
