import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystemV3 {
    private static final String URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12765727";
    private static final String USER = "sql12765727";
    private static final String PASSWORD = "IV45aptfQW";


    private Connection connection;

    public LibraryManagementSystemV3() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to MySQL Database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBook(String title, String author) {
        String query = "INSERT INTO Books (title, author) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.executeUpdate();
            System.out.println("Book added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBook(String title) {
        String query = "DELETE FROM Books WHERE title = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, title);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Book removed successfully!");
            } else {
                System.out.println("Book not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayBooks() {
        String query = "SELECT * FROM Books";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Title: " + rs.getString("title") +
                        ", Author: " + rs.getString("author") +
                        ", Availability: " + (rs.getBoolean("isAvailable") ? "Yes" : "No"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrowBook(String title) {
        String query = "UPDATE Books SET isAvailable = FALSE WHERE title = ? AND isAvailable = TRUE";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, title);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Book borrowed successfully!");
            } else {
                System.out.println("Book not found or already borrowed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(String title) {
        String query = "UPDATE Books SET isAvailable = TRUE WHERE title = ? AND isAvailable = FALSE";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, title);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Book returned successfully!");
            } else {
                System.out.println("Book not found or not borrowed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LibraryManagementSystemV3 library = new LibraryManagementSystemV3();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add book");
            System.out.println("2. Remove book");
            System.out.println("3. Display books");
            System.out.println("4. Borrow book");
            System.out.println("5. Return book");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    library.addBook(title, author);
                    break;
                case 2:
                    System.out.print("Enter book title: ");
                    title = scanner.nextLine();
                    library.removeBook(title);
                    break;
                case 3:
                    library.displayBooks();
                    break;
                case 4:
                    System.out.print("Enter book title: ");
                    title = scanner.nextLine();
                    library.borrowBook(title);
                    break;
                case 5:
                    System.out.print("Enter book title: ");
                    title = scanner.nextLine();
                    library.returnBook(title);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
