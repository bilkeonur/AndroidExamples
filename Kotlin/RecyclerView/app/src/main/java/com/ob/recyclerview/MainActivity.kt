package com.ob.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ob.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding

    private var animals: ArrayList<Animal> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val rcvAnimals = binding.rcvAnimal

        val bear = Animal(1, "Bear", R.drawable.bear)
        val dolphin = Animal(2, "Dolphin", R.drawable.dophin)
        val fox = Animal(3, "Fox", R.drawable.fox)
        val tiger = Animal(4, "Tiger", R.drawable.tiger)
        val turtle = Animal(5, "Turtle", R.drawable.turtle)

        animals.add(bear)
        animals.add(dolphin)
        animals.add(fox)
        animals.add(tiger)
        animals.add(turtle)

        rcvAnimals.layoutManager = LinearLayoutManager(this)
        val animalAdapter = AnimalAdapter(animals)
        rcvAnimals.adapter = animalAdapter
    }
}