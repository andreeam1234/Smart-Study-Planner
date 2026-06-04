package model;

public class Lesson {
    private int id;
    private String title;

    public Lesson(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLessonType() {
        return "General Lesson";
    }

    @Override
    public String toString() {
        return getLessonType() + ": " + title;
    }
}