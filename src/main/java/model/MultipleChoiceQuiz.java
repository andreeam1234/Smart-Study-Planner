package model;

public class MultipleChoiceQuiz extends Quiz {
    private int numberOfQuestions;

    public MultipleChoiceQuiz(String title, int maxScore, int numberOfQuestions) {
        super(title, maxScore);
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    @Override
    public String getQuizType() {
        return "Multiple Choice Quiz";
    }
}