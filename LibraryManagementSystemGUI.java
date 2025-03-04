import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class LibraryManagementSystemGUI {
    private JTextArea displayArea;
    private JTextField titleField, authorField;

  
    private static final String URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12765727";
    private static final String USER = "sql12765727";
    private static final String PASSWORD = "IV45aptfQW";

    public LibraryManagementSystemGUI() {
        createGUI();
    }

    private void createGUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        JFrame frame = new JFrame("Library Management System");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Library Management System", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(headerLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        inputPanel.add(new JLabel("Book Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        inputPanel.add(authorField);

        frame.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        JButton addButton = new JButton("Add Book");
        JButton removeButton = new JButton("Remove Book");
        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");
        JButton displayButton = new JButton("Show Books");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(borrowButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        displayArea.setBorder(BorderFactory.createTitledBorder("Book List"));
        displayArea.setBackground(new Color(245, 245, 245));

        frame.add(displayArea, BorderLayout.EAST);
        displayArea.setPreferredSize(new Dimension(500, 500));

        // Button Listeners
        addButton.addActionListener(e -> addBook());
        removeButton.addActionListener(e -> removeBook());
        borrowButton.addActionListener(e -> borrowBook());
        returnButton.addActionListener(e -> returnBook());
        displayButton.addActionListener(e -> displayBooks());
        exitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void addBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();

        if (!title.isEmpty() && !author.isEmpty()) {
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO books (title, author) VALUES (?, ?)")) {
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.executeUpdate();
                displayMessage("Book added successfully!");
                titleField.setText("");
                authorField.setText("");
            } catch (SQLException e) {
                displayMessage("Error adding book: " + e.getMessage());
            }
        } else {
            displayMessage("Please enter both title and author!");
        }
    }

    private void removeBook() {
        String title = titleField.getText().trim();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE title = ?")) {
            stmt.setString(1, title);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                displayMessage("Book removed successfully!");
            } else {
                displayMessage("Book not found!");
            }
        } catch (SQLException e) {
            displayMessage("Error removing book: " + e.getMessage());
        }
    }

    private void borrowBook() {
        String title = titleField.getText().trim();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE books SET isAvailable = FALSE WHERE title = ? AND isAvailable = TRUE")) {
            stmt.setString(1, title);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                displayMessage("Book borrowed successfully!");
            } else {
                displayMessage("Book not found or already borrowed!");
            }
        } catch (SQLException e) {
            displayMessage("Error borrowing book: " + e.getMessage());
        }
    }

    private void returnBook() {
        String title = titleField.getText().trim();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE books SET isAvailable = TRUE WHERE title = ? AND isAvailable = FALSE")) {
            stmt.setString(1, title);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                displayMessage("Book returned successfully!");
            } else {
                displayMessage("Book not found or not borrowed!");
            }
        } catch (SQLException e) {
            displayMessage("Error returning book: " + e.getMessage());
        }
    }

    private void displayBooks() {
        StringBuilder bookList = new StringBuilder("Books in Library:\n");
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                bookList.append("Title: ").append(rs.getString("title"))
                        .append("\nAuthor: ").append(rs.getString("author"))
                        .append("\nAvailable: ").append(rs.getBoolean("isAvailable") ? "Yes" : "No")
                        .append("\n----------------------\n");
            }
            if (bookList.toString().equals("Books in Library:\n")) {
                bookList.append("No books available.");
            }
            displayArea.setText(bookList.toString());
        } catch (SQLException e) {
            displayMessage("Error fetching books: " + e.getMessage());
        }
    }

    private void displayMessage(String message) {
        displayArea.setText(message);
    }

    public static void main(String[] args) {
        new LibraryManagementSystemGUI();
    }
}
