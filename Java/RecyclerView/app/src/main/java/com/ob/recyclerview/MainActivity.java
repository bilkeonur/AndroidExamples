package com.ob.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;

import com.ob.recyclerview.databinding.ActivityMainBinding;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ActivityMainBinding binding;
    ArrayList<Animal> animals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        RecyclerView rcvAnimals = binding.rcvAnimal;

        Animal bear = new Animal(1,"Bear", R.drawable.bear);
        Animal dolphin = new Animal(2,"Dolphin",R.drawable.dophin);
        Animal fox = new Animal(3,"Fox",R.drawable.fox);
        Animal tiger = new Animal(4,"Tiger",R.drawable.tiger);
        Animal turtle = new Animal(5,"Turtle",R.drawable.turtle);

        animals.add(bear);
        animals.add(dolphin);
        animals.add(fox);
        animals.add(tiger);
        animals.add(turtle);

        rcvAnimals.setLayoutManager(new LinearLayoutManager(this));
        //rcvAnimals.setLayoutManager(new GridLayoutManager(this,2));
        AnimalAdapter animalAdapter = new AnimalAdapter(animals);
        rcvAnimals.setAdapter(animalAdapter);
    }
}