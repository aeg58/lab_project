/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagemnt;

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
    private int shelfCount;   // shelves number
    private static final int shelfCapacity = 4;
//(Book[] books, int bookCount, User[] users, int userCount, Transaction[] transactions, int transactionCount -->parametreler

    public Library() {
        books = new Book[40]; //total capacity of library 
        bookCount = 0;
        users = new User[10]; //for begenning
        userCount = 0;
        transactions = new Transaction[50]; //for begenning
        transactionCount = 0;
        shelves = new Book[10][shelfCapacity];
        shelfCount = 1;
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

    public Book findBookByTitle(String title) {
        for (int i = 0; i < shelfCount; i++) {
            for (int j = 0; j < shelfCapacity; j++) {
                if (shelves[i][j] != null
                        && shelves[i][j].getTitle().equalsIgnoreCase(title)) {
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

    private boolean isCapacityExceed(int totalBooks, int bookCount) {

        return false;

    }

    private int getCurrentSize() {
        int size = 0;
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null) {
                size++;
            }
        }
        return size;
    }

    // adding book
    public void addBook(Book book) {
        Book existedBook = getExistedBook(book);
        //write private function that get current size of the library according to total copies
        int size = getCurrentSize();
        if (existedBook != null) {//know we have to check books with size integer its easy 
            existedBook.setTotalCopies(existedBook.getTotalCopies()+book.getTotalCopies());
            existedBook.setAvaibleCopies(existedBook.getAvaibleCopies()+book.getTotalCopies());
        }else{
            if (book.getTotalCopies()+size <= 40) {
                
            }else{
                System.out.println("Library is full! ");
            }
        }

        // adding new book
        for (int i = 0; i < shelfCount; i++) {
            for (int j = 0; j < shelfCapacity; j++) {
                if (shelves[i][j] == null) {
                    shelves[i][j] = book;
                    System.out.println("Book added to shelf " + (i + 1) + ": " + book.getTitle());
                    return;
                }
            }
        }

        // Tüm raflar doluysa yeni raf ekle
        if (shelfCount == shelves.length) {
            expandShelves();
        }
        shelves[shelfCount][0] = book; // Yeni rafa kitabı ekle
        shelfCount++;
        bookCount += book.;
        System.out.println("New shelf created. Book added: " + book.getTitle());
    }

    public void expandShelves() {
        Book[][] newShelves = new Book[shelves.length * 2][shelfCapacity];
        for (int i = 0; i < shelves.length; i++) {
            newShelves[i] = shelves[i];
        }
        shelves = newShelves;
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
        }
    }

    public void returnBook(User user, Book book, LocalDate date) {
        if (user.returnBook(book)) { // Kullanıcının iade işlemi başarılıysa
            if (transactionCount >= transactions.length) { // Transaction dizisi doluysa genişlet
                expandTransactionArray();
            }
            transactions[transactionCount++] = new Transaction(user, book, "Return", date);
            System.out.println("Transaction recorded: Return on " + date);
        }
    }

    public void displayBooks() {
        System.out.println("\nBooks in the Library:");
        for (int i = 0; i < shelfCount; i++) {
            System.out.println("Shelf " + (i + 1) + ":");
            for (int j = 0; j < shelfCapacity; j++) {
                if (shelves[i][j] != null) {
                    shelves[i][j].displayBook();
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
        Book[] newBooks = new Book[books.length * 2];
        for (int i = 0; i < books.length; i++) {
            newBooks[i] = books[i];
        }
        books = newBooks;
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
}
