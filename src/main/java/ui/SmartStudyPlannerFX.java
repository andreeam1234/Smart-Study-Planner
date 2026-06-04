package ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Course;
import service.ELearningService;
import db.DatabaseInitializer;
import repository.CourseRepository;

public class SmartStudyPlannerFX extends Application {
    private final ELearningService service = new ELearningService();

    private final ObservableList<String> courseItems = FXCollections.observableArrayList();
    private final ListView<String> courseListView = new ListView<>(courseItems);

    private final TextField idField = new TextField();
    private final TextField titleField = new TextField();
    private final ComboBox<String> categoryField = new ComboBox<>();

    @Override
    public void start(Stage stage) {
        DatabaseInitializer.initialize();
        BorderPane root = new BorderPane();

        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        courseListView.setPlaceholder(new Label("No courses added yet."));
        root.setCenter(courseListView);

        GridPane form = createForm();
        root.setBottom(form);

        Scene scene = new Scene(root, 700, 450);

        stage.setTitle("Smart Study Planner");
        stage.setScene(scene);
        stage.show();
        refreshCourses();
    }

    private MenuBar createMenuBar() {
        Menu menu = new Menu("Menu");

        MenuItem refreshItem = new MenuItem("Refresh");
        MenuItem exitItem = new MenuItem("Exit");

        refreshItem.setOnAction(_ -> refreshCourses());
        exitItem.setOnAction(_ -> System.exit(0));

        menu.getItems().addAll(refreshItem, exitItem);

        return new MenuBar(menu);
    }

    private GridPane createForm() {
        GridPane form = new GridPane();
        form.setPadding(new Insets(15));
        form.setHgap(10);
        form.setVgap(10);

        idField.setPromptText("Course ID");
        titleField.setPromptText("Course title");

        categoryField.getItems().addAll(
                "Mathematics",
                "Programming",
                "Science",
                "Language",
                "History",
                "General"
        );
        categoryField.setPromptText("Select category");

        Button addButton = new Button("Add Course");
        Button clearButton = new Button("Clear");

        addButton.setOnAction(_ -> addCourse());
        clearButton.setOnAction(_ -> clearForm());

        form.add(new Label("Course ID:"), 0, 0);
        form.add(idField, 1, 0);

        form.add(new Label("Title:"), 0, 1);
        form.add(titleField, 1, 1);

        form.add(new Label("Category:"), 0, 2);
        form.add(categoryField, 1, 2);

        form.add(clearButton, 0, 3);
        form.add(addButton, 1, 3);

        return form;
    }

    private void addCourse() {
        try {
            int id = Integer.parseInt(idField.getText());
            String title = titleField.getText();
            String category = categoryField.getValue();

            if (title.isBlank() || category == null) {
                showValidationError("Title and category cannot be empty.");
                return;
            }

            Course course = new Course.Builder()
                    .id(id)
                    .title(title)
                    .category(category)
                    .build();

            service.addCourse(course, category);
            CourseRepository.getInstance().create(course);

            refreshCourses();
            clearForm();

            showSuccessMessage("Course added successfully.");

        } catch (NumberFormatException e) {
            showValidationError("Course ID must be a number.");
        }
    }

    private void refreshCourses() {
        courseItems.clear();

        for (Course course : CourseRepository.getInstance().readAll()) {
            courseItems.add(course.getId() + " - " + course.getTitle() + " [" + course.getCategory() + "]");
        }
    }

    private void clearForm() {
        idField.clear();
        titleField.clear();
        categoryField.setValue(null);
    }

    private void showValidationError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}