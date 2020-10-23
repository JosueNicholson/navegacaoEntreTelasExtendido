package com.example.navegacaoentretelas.models;

public class Book {
    String authorName;
    String bookName;
    int releaseYear;
    String id;

    public Book(String authorName, String bookName, int releaseYear) {
        this.authorName = authorName;
        this.bookName = bookName;
        this.releaseYear = releaseYear;
    }

    public Book(String authorName, String bookName, int releaseYear, String id){
        this.authorName = authorName;
        this.bookName = bookName;
        this.releaseYear = releaseYear;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getBookName() {
        return bookName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
