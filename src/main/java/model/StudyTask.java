package model;

public class StudyTask extends Task {
    public StudyTask(int id, String title) {
        super(id, title);
    }

    @Override
    public String getTaskType() {
        return "Study Task";
    }
}