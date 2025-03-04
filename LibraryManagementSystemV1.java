import java.util.Scanner;

public class LibraryManagementSystemV1 {
    private Book[] books;
    private int bookCount;

    public LibraryManagementSystemV1() {
        books = new Book[100]; 
        bookCount = 0;
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
        if (bookCount < books.length) {
            books[bookCount] = new Book(title, author);
            bookCount++;
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Library is full. Cannot add more books.");
        }
    }

    public void removeBook(String title) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().equals(title)) {
                for (int j = i; j < bookCount - 1; j++) {
                    books[j] = books[j + 1];
                }
                bookCount--;
                System.out.println("Book removed successfully!");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void displayBooks() {
        for (int i = 0; i < bookCount; i++) {
            System.out.println("Title: " + books[i].getTitle() + ", Author: " + books[i].getAuthor() + ", Availability: " + (books[i].isAvailable() ? "Yes" : "No"));
        }
    }

    public void borrowBook(String title) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().equals(title) && books[i].isAvailable()) {
                books[i].setAvailability(false);
                System.out.println("Book borrowed successfully!");
                return;
            }
        }
        System.out.println("Book not found or already borrowed.");
    }

    public void returnBook(String title) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().equals(title) && !books[i].isAvailable()) {
                books[i].setAvailability(true);
                System.out.println("Book returned successfully!");
                return;
            }
        }
        System.out.println("Book not found or not borrowed.");
    }

    public static void main(String[] args) {
        LibraryManagementSystemV1 library = new LibraryManagementSystemV1();
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