package model;

public class Lesson {
    private int id;
    private String title;

    public Lesson(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}