package factory;

import model.Instructor;
import model.Student;
import model.User;

public class UserFactory {
    public static User createUser(String type, int id, String name) {
        if (type.equalsIgnoreCase("student")) {
            return new Student(id, name);
        }

        if (type.equalsIgnoreCase("instructor")) {
            return new Instructor(id, name);
        }

        throw new IllegalArgumentException("Unknown user type: " + type);
    }
}