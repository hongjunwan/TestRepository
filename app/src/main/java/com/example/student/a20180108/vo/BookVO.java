package com.example.student.a20180108.vo;

/**
 * Created by student on 2018-01-08.
 */

public class BookVO {
    private String bookTitle;
    private String imgUrl;
    private String price;
    private String publisher;
    private String author;

    public BookVO(String bookTitle, String imgUrl, String price, String publisher, String author) {
        this.bookTitle = bookTitle;
        this.imgUrl = imgUrl;
        this.price = price;
        this.publisher = publisher;
        this.author = author;
    }
    public BookVO(){}
    //////////////////////////////////////////////////////////////////////////////////////////////

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
