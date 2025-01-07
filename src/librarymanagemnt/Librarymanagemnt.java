package librarymanagemnt;

import java.util.Scanner;
import java.time.LocalDate;

public class Librarymanagemnt {

    public static void main(String[] args) {
        Library library = new Library();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\n========== Library Management System ==========");
            System.out.println("1. Add a New Book");
            System.out.println("2. Register a New User");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. Display All Books");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            System.out.println("===============================================");
            System.out.print("Please choose an option: ");
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1://add book
                    System.out.print("Enter the book title: ");
                    String title = input.nextLine();
                    System.out.print("Enter author: ");
                    String author = input.nextLine();
                    System.out.print("Enter total copies: ");
                    int totalCopies = input.nextInt();
                    library.addBook(new Book(title, author, totalCopies, totalCopies));
                    break;

                case 2:
                    System.out.print("Enter the username: ");
                    String userName = input.nextLine();
                    if (library.isUserNameInvalid(userName)) {
                        System.out.println("Username cannot be empty !");
                        break;
                    }

                    System.out.print("Enter user email: ");
                    String email = input.nextLine();
                    if (!library.isValidEmail(email)) {
                        System.out.println("Looks like you don't have a proper mail address! ");
                        break;
                    }
                    library.registerUser(new User(userName, email));
                    break;
                case 3://borrow a book
                    System.out.print("Enter book title: ");
                    String bookTitle = input.nextLine();
                    Book book = library.findBookByTitle(bookTitle);
                    if (book == null) {
                        break;
                    }

                    System.out.print("Enter user email: ");
                    String userEmail = input.nextLine();
                    User user = library.findUserByEmail(userEmail);
                    if (user == null) {
                        break;
                    }

                    library.borrowBook(user, book, LocalDate.now());
                    break;

                case 4://return a book
                    System.out.print("Enter book title: ");
                    bookTitle = input.nextLine();
                    book = library.findBookByTitle(bookTitle);
                    if (book == null) {
                        break;
                    }

                    System.out.print("Enter user email: ");
                    userEmail = input.nextLine();
                    user = library.findUserByEmail(userEmail);
                    if (user == null) {
                        break;
                    }

                    // İade işlemi
                    library.returnBook(user, book, LocalDate.now());
                    break;

                case 5:
                    library.displayBooks();
                    break;

                case 6:
                    library.displayTransactions();
                    break;

                case 7:
                    System.out.println("Exiting the program. Goodbye!");
                    input.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }

    }
}
