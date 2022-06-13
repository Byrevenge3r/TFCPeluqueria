package com.dam.peluqueriacanina.model;

public class Rating {

    private float rating;

    public Rating() {
    }

    public Rating(float rating, int contUser) {
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

}
