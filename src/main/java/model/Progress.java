package model;

public class Progress implements Trackable {
    private Student student;
    private int score;

    public Progress(Student student) {
        this.student = student;
        this.score = 0;
    }

    public void addScore(int points) { this.score += points; }

    @Override
    public void showProgress() {
        System.out.println("Student progress for " + student.getName() + ": " + score + " points.");
    }
}