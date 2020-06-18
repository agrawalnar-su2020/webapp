package com.csye6225.webapps.model;

import com.amazonaws.services.s3.model.ObjectMetadata;

import javax.persistence.*;

@Entity
public class BookImages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long imageID;

    private String imageName;

    private String imageURL;

    @ManyToOne
    @JoinColumn
    private Book book;

    public long getImageID() {
        return imageID;
    }

    public void setImageID(long imageID) {
        this.imageID = imageID;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
