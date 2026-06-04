package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Course {

    private final int id;
    private final String title;
    private final String category;
    private final Instructor instructor;

    private final List<Lesson> lessons;
    private final List<Quiz> quizzes;

    private Course(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.category = builder.category;
        this.instructor = builder.instructor;
        this.lessons = new ArrayList<>(builder.lessons);
        this.quizzes = new ArrayList<>(builder.quizzes);
    }

    public static class Builder {

        private int id;
        private String title;
        private String category = "General";
        private Instructor instructor = null;

        private List<Lesson> lessons = new ArrayList<>();
        private List<Quiz> quizzes = new ArrayList<>();

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }

        public Builder lessons(List<Lesson> lessons) {
            if (lessons != null) {
                this.lessons = new ArrayList<>(lessons);
            }
            return this;
        }

        public Builder quizzes(List<Quiz> quizzes) {
            if (quizzes != null) {
                this.quizzes = new ArrayList<>(quizzes);
            }
            return this;
        }

        public Builder addLesson(Lesson lesson) {
            if (lesson != null) {
                this.lessons.add(lesson);
            }
            return this;
        }

        public Builder addQuiz(Quiz quiz) {
            if (quiz != null) {
                this.quizzes.add(quiz);
            }
            return this;
        }

        public Course build() {
            if (title == null || title.isBlank()) {
                throw new IllegalStateException("Course title cannot be null or empty");
            }

            if (id <= 0) {
                throw new IllegalStateException("Course id must be positive");
            }

            return new Course(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public List<Lesson> getLessons() {
        return Collections.unmodifiableList(lessons);
    }

    public List<Quiz> getQuizzes() {
        return Collections.unmodifiableList(quizzes);
    }

    public void addLesson(Lesson lesson) {
        if (lesson != null) {
            lessons.add(lesson);
        }
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    public void addQuiz(Quiz quiz) {
        if (quiz != null) {
            quizzes.add(quiz);
        }
    }

    public void removeQuiz(Quiz quiz) {
        quizzes.remove(quiz);
    }

    public int getTotalContent() {
        return lessons.size() + quizzes.size();
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", instructor=" + (instructor != null ? instructor.getName() : "none") +
                ", lessons=" + lessons.size() +
                ", quizzes=" + quizzes.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}