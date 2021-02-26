package com.example.messengerapp;

import org.parceler.Parcel;

@Parcel
public class Movie {

    public String title;
    public String description;
    public String imageLink;
    public Double rating;
    public Integer id;

    public Movie(){}
    public Movie(String title, String description, String imageLink, Double rating, Integer id){
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
        this.rating=rating;
        this.id = id;
    }

}
