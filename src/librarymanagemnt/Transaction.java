/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagemnt;

import java.time.LocalDate;

/**
 *
 * @author Jr. Gedik
 */

public class Transaction {

    private User user;
    private Book book;
    private String type; // Borrow or Return
    private LocalDate date;

    public Transaction(User user, Book book, String type, LocalDate date) {
        this.user = user;
        this.book = book;
        this.type = type;
        this.date = (date == null) ? LocalDate.now() : date;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public String getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void displayTransaction() {
        System.out.printf("Transaction: %s | User: %s | Book: %s | Date: %s\n",
                type, user.getName(), book.getTitle(), date);
    }
}