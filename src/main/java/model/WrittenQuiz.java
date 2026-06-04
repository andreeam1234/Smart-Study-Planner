package model;

public class WrittenQuiz extends Quiz {
    private int wordLimit;

    public WrittenQuiz(String title, int maxScore, int wordLimit) {
        super(title, maxScore);
        this.wordLimit = wordLimit;
    }

    public int getWordLimit() {
        return wordLimit;
    }

    @Override
    public String getQuizType() {
        return "Written Quiz";
    }
}