package model;

public class ReadingLesson extends Lesson {
    private int numberOfPages;

    public ReadingLesson(int id, String title, int numberOfPages) {
        super(id, title);
        this.numberOfPages = numberOfPages;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    @Override
    public String getLessonType() {
        return "Reading Lesson";
    }
}