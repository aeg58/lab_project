/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagemnt;

// Book Class
public class Book {

    private String title;
    private String author;
    private int avaibleCopies;
    private int totalCopies;

    //constructor
    public Book(String title, String author, int avaibleCopies, int totalCopies) {
        this.title = title;
        this.author = author;
        this.avaibleCopies = avaibleCopies;
        this.totalCopies = totalCopies;//for begenning totalCopies = avaibleCopies
    }

    public void displayBook(int order) {
        System.out.println("Order: " +order+ " Title: " + title + ", Author: " + author
                + ", Available Copies: " + avaibleCopies
                + ", Total Copies: " + totalCopies);
    }

//getter abd setter methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAvaibleCopies() {
        return avaibleCopies;
    }

    public void setAvaibleCopies(int avaibleCopies) {
        this.avaibleCopies = avaibleCopies;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public void borrowBook() {
        if (avaibleCopies > 0) {
            avaibleCopies--;
            System.out.println("Book borrowed from library successfully.");
        } else {
            System.out.println("No copies avaible for now to borrow.");
        }
    }

    public void returnBook() {
        if (avaibleCopies < totalCopies) {
            avaibleCopies++;
            System.out.println("Book returned the home succesfully.");
        } else {
            System.out.println("All copies are already in the home.");
        }
    }

}

