package model;

public class QuizTask extends Task {
    private int quizId;

    public QuizTask(int id, String title, int quizId) {
        super(id, title);
        this.quizId = quizId;
    }

    public int getQuizId() {
        return quizId;
    }

    @Override
    public String getTaskType() {
        return "Quiz Task";
    }
}