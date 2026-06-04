package model;

public class Quiz {
    private int id;
    private String title;
    private int maxScore;

    public Quiz(String title, int maxScore) {
        this.id = 0;
        this.title = title;
        this.maxScore = maxScore;
    }

    public Quiz(int id, String title, int maxScore) {
        this.id = id;
        this.title = title;
        this.maxScore = maxScore;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public String getQuizType() {
        return "General Quiz";
    }
}