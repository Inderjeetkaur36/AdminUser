package com.example.adminuser.model;

import java.io.Serializable;

public class Shoes implements Serializable {

    public int image;
    public String imageUrl;
    public String docId;
    public String id;
    public String size;
    public String name;
    public String price;
    public String color;

    public Shoes(){

    }

    public Shoes(int image,String imageUrl,String name,String id, String price, String color, String size) {
        this.image = image;
        this.imageUrl = imageUrl;
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;
        this.size = size;
    }

    @Override
    public String toString() {

        String str = "Shoe Id: "+id+"\nShoe Type: "+name+"\nShoe Price: "+price+"\nShoe Size:"+size+"\nShoe Color:"+color;
        return str;
    }
}
