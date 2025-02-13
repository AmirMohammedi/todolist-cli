import java.util.Scanner;
import java.sql.*;

public class TodoList {
    private static final String DB_URL = "jdbc:sqlite:todolist.db";

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        if(!createTable()){
                System.exit(1);
            }
        System.out.println("\nWelcome! Every small task brings you closer to your goals.\n");
        while (true) {
            System.out.println("1. Add Task\n2. View Tasks\n3. Mark Task as Done\n4. Delete Task\n5. Exit");

            System.out.print("Choose an option: ");
            try {
                int number = Integer.parseInt(s.nextLine()); 

                switch (number) {
                    case 1:
                        addTask(s);
                        System.out.println("Press Enter to go back...");
                        s.nextLine();
                        break;
                    case 2:
                        viewTasks();
                        System.out.println("Press Enter to go back...");
                        s.nextLine();
                        break;
                    case 3:
                        markTaskAsDone(s);
                        System.out.println("Press Enter to go back...");
                        s.nextLine();
                        break;
                    case 4:
                        deleteTask(s);
                        System.out.println("Press Enter to go back...");
                        s.nextLine();
                        break;
                    case 5:
                        System.out.println("Goodbye! Have a productive day.");
                        System.out.println("By Amir Elcharif Mohammedi");
                        s.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option. Try again.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input! Please enter a valid number.\n");
            }
        }
    }
    private static boolean createTable() {
    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {
        
        String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "description TEXT NOT NULL, " +
                     "done INTEGER DEFAULT 0);";
        
        stmt.execute(sql);
        return true;

    } catch (SQLException e) {
        System.out.println("Error creating table: " + e.getMessage());
        return false; 
    }
    }
    private static void addTask(Scanner s){
        System.out.print("Enter task description: ");
        String description = s.nextLine();
        String sql = "INSERT INTO tasks(description) VALUES(?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.executeUpdate();
            System.out.println("Task added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding task: " + e.getMessage());
        }
    }
    private static void viewTasks(){
        String sql = "SELECT * FROM tasks";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nTasks:");
            while (rs.next()) {
                String status = rs.getInt("done") == 1 ? "[Done]" : "[Pending]";
                System.out.println(rs.getInt("id") + ". " + status + " " + rs.getString("description"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching tasks: " + e.getMessage());
        }
    }
    private static void markTaskAsDone(Scanner s) {
        System.out.print("Enter task ID to mark as done: ");
        try {
            int taskId = Integer.parseInt(s.nextLine());

            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement("UPDATE tasks SET done = '1' WHERE id = ?")) {

                pstmt.setInt(1, taskId);
                int updated = pstmt.executeUpdate();

                if (updated > 0) {
                    System.out.println("Task marked as done!");
                } else {
                    System.out.println("Task not found.");
                }

            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid task ID. Please enter a number.");
        }
    }
     private static void deleteTask(Scanner s) {
        System.out.print("Enter task ID to delete: ");
        try {
            int taskId = Integer.parseInt(s.nextLine());

            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM tasks WHERE id = ?")) {

                pstmt.setInt(1, taskId);
                int deleted = pstmt.executeUpdate();

                if (deleted > 0) {
                    System.out.println("Task deleted successfully!");
                } else {
                    System.out.println("Task not found.");
                }

            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid task ID. Please enter a number.");
        }
    }
}