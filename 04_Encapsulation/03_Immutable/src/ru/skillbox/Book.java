package ru.skillbox;

public class Book {
    private final String bookName;
    private final String autorName;
    private final int pageQuantity;
    private final int isbnNumber;

    public Book(String bookName,String autorName,int pageQuantity,int isbnNumber){
        this.bookName = bookName;
        this.autorName = autorName;
        this.pageQuantity = pageQuantity;
        this.isbnNumber = isbnNumber;

    }
}
