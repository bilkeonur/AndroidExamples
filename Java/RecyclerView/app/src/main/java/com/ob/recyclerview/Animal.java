package com.ob.recyclerview;

public class Animal
{
    int id;
    String name;
    int image;

    public Animal(int id, String name, int image)
    {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getImage()
    {
        return image;
    }
}
