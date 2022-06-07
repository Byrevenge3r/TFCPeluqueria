package com.dam.peluqueriacanina.model;

public class RatingUser {

    private boolean hecho;
    private float rating;

    public RatingUser() {
    }

    public RatingUser(boolean hecho, float rating) {
        this.hecho = hecho;
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isHecho() {
        return hecho;
    }

    public void setHecho(boolean hecho) {
        this.hecho = hecho;
    }
}
