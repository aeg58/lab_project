/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagemnt;

public class User {

    private String name;
    private String email;
    private Book[] borrowedBooks;//for taken books from user
    private int borrowedCount;

    //constructor
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.borrowedBooks = new Book[3];
        this.borrowedCount = 0;
    }
    //getter and setter 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Book[] getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Book[] borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        this.borrowedCount = borrowedCount;
    }

    public boolean borrowingBook(Book book) {
        if (borrowedCount >= 3) {
            System.out.println("Error: " + name + " has already borrowed 3 books.");
            return false;
        }
        if (book.getAvaibleCopies() <= 0) {
            System.out.println("Error: No available copies for the book '" + book.getTitle() + "'.");
            return false;
        }
        borrowedBooks[borrowedCount] = book;
        borrowedCount++;
        System.out.println(name + " borrowed the book: " + book.getTitle());
        book.borrowBook();
        return true;
    }

    public boolean returnBook(Book book) {
        for (int i = 0; i < borrowedCount; i++) {
            if (borrowedBooks[i].equals(book)) {
                System.out.println(name + " returned the book: " + book.getTitle());
                borrowedBooks[i] = borrowedBooks[borrowedCount - 1];
                borrowedBooks[borrowedCount - 1] = null;
                borrowedCount--;
                book.returnBook();
                return true;
            }
        }
        System.out.println("Error: " + name + " did not borrow the book '" + book.getTitle() + "'.");
        return false;
    }

}
