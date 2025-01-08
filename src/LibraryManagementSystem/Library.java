/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LibraryManagementSystem;

import java.time.LocalDate;

public class Library {

    private Book[] books;             // books in library
    private int bookCount;            // Total book 
    private User[] users;             // registered users
    private int userCount;            // total users
    private Transaction[] transactions; // records
    private int transactionCount;
    public static int totalBooks = 0;
    public static int totalUsers = 0;

    private Book[][] shelves; // for shelves
    private static final int shelfCapacity = 4;
    private static int libraryCapacity = 40;
    private static int shelfSize = 10;

//(Book[] books, int bookCount, User[] users, int userCount, Transaction[] transactions, int transactionCount -->parametreler
    public Library() {
        books = new Book[libraryCapacity]; //total capacity of library 
        bookCount = 0;
        users = new User[10]; //for begenning
        userCount = 0;
        transactions = new Transaction[50]; //for begenning
        transactionCount = 0;
        shelves = new Book[10][shelfCapacity];

    }

    public int getUserCount() {
        return userCount;
    }

    public User findUserByEmail(String email) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getEmail().equalsIgnoreCase(email)) {
                return users[i];
            }
        }
        System.out.println("Error: User not found. Please register first.");
        return null;
    }

    public Book findBookByTitleAndAuthor(String title, String author) {
        for (int i = 0; i < shelfSize; i++) {
            for (int j = 0; j < shelfCapacity; j++) {
                if (shelves[i][j] != null
                        && shelves[i][j].getTitle().equalsIgnoreCase(title)
                        && shelves[i][j].getAuthor().equalsIgnoreCase(author)) {
                    return shelves[i][j];
                }
            }
        }
        System.out.println("Error: Book not found.");
        return null;
    }
// Aynı kitap kontrolü

    private Book getExistedBook(Book book) {
        for (int i = 0; i < books.length; i++) {
            Book existedBook = books[i];
            if (existedBook == null) {
                return null;
            }
            if (book.getTitle().equalsIgnoreCase(existedBook.getTitle())
                    && book.getAuthor().equalsIgnoreCase(existedBook.getAuthor())) {
                return existedBook;
            }
        }

        return null;

    }

    // adding book
    public void addBook(Book newBook) {
        Book existedBook = getExistedBook(newBook);
        if (existedBook != null) {
            existedBook.setTotalCopies(existedBook.getTotalCopies() + newBook.getTotalCopies());
            existedBook.setAvaibleCopies(existedBook.getAvaibleCopies() + newBook.getTotalCopies());
            System.out.println("Book is already exist. Copy number updated : " + newBook.getTitle());
        }

        bookCount += newBook.getTotalCopies();

        if (bookCount > libraryCapacity) {
            int requiredSize = bookCount - libraryCapacity;
            expandBookArray();
            int incrementedShelve = expandShelves(requiredSize);
            System.out.println("Built " + incrementedShelve + " new shelves to accomodate new books. ");
            libraryCapacity += bookCount;
        }

        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) {
                if (existedBook == null) {
                    books[i] = newBook;
                }
                System.out.println("New book added: " + newBook.getTitle());
                break;
            }
        }

        // adding new book
        int newBookSize = newBook.getTotalCopies();
        for (int i = 0; i < shelves.length; i++) {
            for (int j = 0; j < shelfCapacity; j++) {
                if (shelves[i][j] == null) {
                    shelves[i][j] = newBook;
                    System.out.println("Book added to shelf " + (i + 1) + " and in order of " + (j + 1) + " : " + newBook.getTitle());
                    if (--newBookSize == 0) {
                        return;
                    }
                }
            }
        }
    }

    public boolean isUserNameInvalid(String username) {
        return username == null || username.equals("");
    }

    public boolean isValidEmail(String email) {
        boolean isValid = true;

        if (email == null || !email.contains("@")) {
            isValid = false;
        }
        if (isValid) {
            String[] mailParts = email.split("@");
            isValid = mailParts.length == 2;
        }

        return isValid;

    }

    // ading user
    public void registerUser(User user) {

        for (int i = 0; i < userCount; i++) {
            if (users[i].getEmail().equalsIgnoreCase(user.getEmail())) {

                System.out.println("Error: User with the same email already exists.");
                return;
            }
        }
        if (userCount >= users.length) { // if full expand the array
            expandUserArray();
        }
        users[userCount++] = user;
        totalUsers++;
        System.out.println("User registered: " + user.getName());
    }

    public void borrowBook(User user, Book book, LocalDate date) {
        if (user.borrowingBook(book)) { // Kullanıcının ödünç alma işlemi başarılıysa
            if (transactionCount >= transactions.length) { // Transaction dizisi doluysa genişlet
                expandTransactionArray();
            }
            transactions[transactionCount++] = new Transaction(user, book, "Borrow", date);
            System.out.println("Transaction recorded: Borrow on " + date);
            // clear the place of the existed book from shelf.
            for (int i = 0; i < shelves.length; i++) {
                for (int j = 0; j < shelfCapacity; j++) {
                    if (shelves[i][j] != null
                            && shelves[i][j].getTitle().equalsIgnoreCase(book.getTitle())
                            && shelves[i][j].getAuthor().equalsIgnoreCase(book.getAuthor())) {
                        shelves[i][j] = null;
                        return;
                    }
                }
            }
        }
    }

    public void returnBook(User user, Book book, LocalDate date) {
        if (user.returnBook(book)) { // Kullanıcının iade işlemi başarılıysa
            if (transactionCount >= transactions.length) { // Transaction dizisi doluysa genişlet
                expandTransactionArray();
            }
            transactions[transactionCount++] = new Transaction(user, book, "Return", date);
            System.out.println("Transaction recorded: Return on " + date);
            
            for (int i = 0; i < shelves.length; i++) {
                for (int j = 0; j < shelfCapacity; j++) {
                    if (shelves[i][j] == null) {
                        shelves[i][j] = book;
                        return;
                    }
                }
            }
        }
    }

    public void displayBooks() {
        System.out.println("\nBooks in the Library:");
        for (int i = 0; i < shelfSize; i++) {
            System.out.println("Shelf " + (i + 1) + ":");
            for (int j = 0; j < shelfCapacity; j++) {
                if (shelves[i][j] != null) {
                    shelves[i][j].displayBook(j+1);
                }
            }
        }
    }

    public void displayTransactions() {
        if (transactionCount == 0) {
            System.out.println("No transactions available.");
            return;
        }
        System.out.println("\nTransaction History:");
        for (int i = 0; i < transactionCount; i++) {
            transactions[i].displayTransaction();
        }
    }

    private void expandBookArray() {
        Book[] expendedBookList = new Book[books.length * 2];
        for (int i = 0; i < books.length; i++) {
            expendedBookList[i] = books[i];
        }
        books = expendedBookList;
    }

    private void expandUserArray() {
        User[] newUsers = new User[users.length * 2];
        for (int i = 0; i < users.length; i++) {
            newUsers[i] = users[i];
        }
        users = newUsers;
    }

    private void expandTransactionArray() {
        Transaction[] newTransactions = new Transaction[transactions.length * 2];
        for (int i = 0; i < transactions.length; i++) {
            newTransactions[i] = transactions[i];
        }
        transactions = newTransactions;
    }

    public int expandShelves(int requiredSize) {
        int requiredShelveSize = requiredSize % 4 == 0 ? requiredSize / 4 : requiredSize / 4 + 1;
        Book[][] newShelves = new Book[shelves.length + requiredShelveSize][shelfCapacity];
        for (int i = 0; i < shelves.length; i++) {
            newShelves[i] = shelves[i];
        }
        shelves = newShelves;

        return requiredShelveSize;
    }
}
