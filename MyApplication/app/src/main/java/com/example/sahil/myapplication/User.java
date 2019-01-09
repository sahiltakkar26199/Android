package com.example.sahil.myapplication;

public class User {

    private static String name;
    private static int age;


    public User(String name , int age){
        this.name=name;
        this.age=age;
    }

    public  String getName(){
        return name;
    }
    public  int getAge(){
        return age;
    }
}
