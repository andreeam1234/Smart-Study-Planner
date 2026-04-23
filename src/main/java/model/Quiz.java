package model;

public class Quiz {
    private String title;
    private int maxScore;

    public Quiz(String title, int maxScore) {
        this.title = title;
        this.maxScore = maxScore;
    }

    public String getTitle() { return title; }
}