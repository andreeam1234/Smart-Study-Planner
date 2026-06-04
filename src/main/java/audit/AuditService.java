package audit;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AuditService {
    private static AuditService instance;
    private static final String FILE_NAME = "audit.csv";

    private AuditService() {}

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void logAction(String actionName) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(actionName + "," + LocalDateTime.now() + "\n");
        } catch (IOException e) {
            System.out.println("Audit error: " + e.getMessage());
        }
    }
}