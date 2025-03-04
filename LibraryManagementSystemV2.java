import java.util.Scanner;
import java.util.Vector;

public class LibraryManagementSystemV2 {  
    private Vector<Book> books;

    public LibraryManagementSystemV2() {
        books = new Vector<>();
    }

    private class Book {
        private String title;
        private String author;
        private boolean isAvailable;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
            this.isAvailable = true;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailability(boolean availability) {
            isAvailable = availability;
        }
    }

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        System.out.println("Book added successfully!");
    }

    public void removeBook(String title) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().equals(title)) {
                books.remove(i);
                System.out.println("Book removed successfully!");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void displayBooks() {
        for (Book book : books) {
            System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Availability: " + (book.isAvailable() ? "Yes" : "No"));
        }
    }

    public void borrowBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title) && book.isAvailable()) {
                book.setAvailability(false);
                System.out.println("Book borrowed successfully!");
                return;
            }
        }
        System.out.println("Book not found or already borrowed.");
    }

    public void returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title) && !book.isAvailable()) {
                book.setAvailability(true);
                System.out.println("Book returned successfully!");
                return;
            }
        }
        System.out.println("Book not found or not borrowed.");
    }

    public static void main(String[] args) {
        LibraryManagementSystemV2 library = new LibraryManagementSystemV2();
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

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.next();
                    System.out.print("Enter book author: ");
                    String author = scanner.next();
                    library.addBook(title, author);
                    break;
                case 2:
                    System.out.print("Enter book title: ");
                    title = scanner.next();
                    library.removeBook(title);
                    break;
                case 3:
                    library.displayBooks();
                    break;
                case 4:
                    System.out.print("Enter book title: ");
                    title = scanner.next();
                    library.borrowBook(title);
                    break;
                case 5:
                    System.out.print("Enter book title: ");
                    title = scanner.next();
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
