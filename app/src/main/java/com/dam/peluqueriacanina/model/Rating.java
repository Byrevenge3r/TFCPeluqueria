package com.dam.peluqueriacanina.model;

public class Rating {

    private float rating;
    private int contUser;

    public Rating() {
    }

    public Rating(float rating, int contUser) {
        this.rating = rating;
        this.contUser = contUser;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getContUser() {
        return contUser;
    }

    public void setContUser(int contUser) {
        this.contUser = contUser;
    }
}
