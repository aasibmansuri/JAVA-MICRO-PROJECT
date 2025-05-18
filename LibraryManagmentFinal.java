import java.io.*;
import java.util.*;

class Book {
    private String title, author;
    private boolean isIssued;

    Book(String title, String author, boolean isIssued) {
        this.title = title;
        this.author = author;
        this.isIssued = isIssued;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return isIssued;
    }

    void issueBook() {
        if (!isIssued) {
            isIssued = true;
            System.out.println("Book issued successfully!, \n U can bring it at your home for 7 days.");
        } else {
            System.out.println("Book is already issued! , Sorry for that.");
        }
    }

    void returnBook() {
        if (isIssued) {
            isIssued = false;
            System.out.println("Book returned successfully! , \nThank you for bringing it back.");
        } else {
            System.out.println("This book was not issued. ");
        }
    }

    void displayInfo() {

        System.out.println("Title: " + title + " | Author: " + author + " | " + (isIssued ? "Issued" : "Available"));
    }

    public String toFileString() {
        return title + "," + author + "," + isIssued;
    }

    public static Book fromFileString(String line) {
        String[] parts = line.split(",");
        return new Book(parts[0], parts[1], Boolean.parseBoolean(parts[2]));
    }
}

class Library {
    private ArrayList<Book> books = new ArrayList<>();
    private final String FILE_NAME = "books.txt";

    Library() {
        loadBooks();
    }

    void addBook(String title, String author) {
        books.add(new Book(title, author, false));
        System.out.println("Book added successfully! ");
        saveBooks();
    }

    void issueBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title.trim())) {
                book.issueBook();
                saveBooks();
                return;
            }
        }
        System.out.println("Book not found!");
    }

    void returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title.trim())) {
                book.returnBook();
                saveBooks();
                return;
            }
        }
        System.out.println("Book not found! ");
    }

    void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : books) {
                book.displayInfo();
            }
        }
    }

    void searchBook(String keyword) {
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                book.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found matching: " + keyword);
        }
    }

    private void saveBooks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Book book : books) {
                writer.println(book.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    private void loadBooks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                books.add(Book.fromFileString(scanner.nextLine()));
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }
}

public class LibraryManagmentFinal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();
         System.out.println("--------------------------------------------");
            System.out.println("Hello! Welcome to the Library Sir/Madam!");
            System.out.println("--------------------------------------------");

        while (true) {
           
            System.out.println("\n1 -> Add New Books In Library");
            System.out.println("2 -> Issue Book From Library");
            System.out.println("3 -> Return Book To Library");
            System.out.println("4 -> View Collection Of Our Books");
            System.out.println("5 -> Search That,Book What u Want");
            System.out.println("6 -> Exit");
            System.out.print("\nEnter your choice : ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid choice number.");
                sc.next();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = sc.nextLine().trim();
                    System.out.print("Enter book author: ");
                    String author = sc.nextLine().trim();
                    library.addBook(title, author);
                    break;
                case 2:
                    System.out.print("Enter Book Name to Issue: ");
                    String issueTitle = sc.nextLine().trim();
                    library.issueBook(issueTitle);
                    break;
                case 3:
                    System.out.print("Enter Book Name to Return: ");
                    String returnTitle = sc.nextLine().trim();
                    library.returnBook(returnTitle);
                    break;
                case 4:
                    System.out.println("\n------Here is the List of Books------\n");
                    library.displayBooks();
                    break;
                case 5:
                    System.out.print("Enter book title or author to search: ");
                    String keyword = sc.nextLine().trim();
                    library.searchBook(keyword);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
