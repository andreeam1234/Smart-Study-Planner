package model;

public class VideoLesson extends Lesson {
    private int durationMinutes;

    public VideoLesson(int id, String title, int durationMinutes) {
        super(id, title);
        this.durationMinutes = durationMinutes;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    @Override
    public String getLessonType() {
        return "Video Lesson";
    }
}