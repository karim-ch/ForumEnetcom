package com.example.fatma.forumenetcom;




public class UsersValues {
    public String name;
    public String position;
    public String workshop;


    public UsersValues(String name, String position, String workshop) {
        this.position = position;
        this.name = name;


    }


    public UsersValues(){
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }




}