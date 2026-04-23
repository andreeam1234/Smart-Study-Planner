package model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int id;
    private String title;
    private List<Lesson> lessons = new ArrayList<>();
    private List<Quiz> quizzes = new ArrayList<>();

    public Course(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {              //
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }
}