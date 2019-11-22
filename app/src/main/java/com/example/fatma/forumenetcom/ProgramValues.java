package com.example.fatma.forumenetcom;


public class ProgramValues {




    public String date;
    public String name;
    public String image;
    public String place;

    public ProgramValues(){}


    public ProgramValues(String date, String name, String image, String place) {

        this.date = date;
        this.place = place;
        this.name = name;
        this.image = image;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }



}
