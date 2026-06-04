package model;

public class RevisionTask extends Task {
    private String topic;

    public RevisionTask(int id, String title, String topic) {
        super(id, title);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public String getTaskType() {
        return "Revision Task";
    }
}