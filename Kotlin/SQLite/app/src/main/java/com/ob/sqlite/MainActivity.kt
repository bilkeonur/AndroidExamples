package com.ob.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ob.sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbHelper = DBHelper(this)
    }

    fun onAdd(view:View) {
        val person1 = Person(1,"Onur","Bilke")
        val person2 = Person(2,"Cansu","Bilke")
        val person3 = Person(3,"Ada","Bilke")
        val person4 = Person(4,"Deniz","Bilke")

        dbHelper.addPerson(person1)
        dbHelper.addPerson(person2)
        dbHelper.addPerson(person3)
        dbHelper.addPerson(person4)
    }

    fun onFetch(view:View) {
        val persons = dbHelper.getPersons()

        for (person in persons) {
            println("Id : ${person.id} Name : ${person.name} Surname : ${person.surname}")
        }
    }

    fun onUpdate(view:View) {
        val person = Person(1,"On","Bi")
        dbHelper.updatePerson(person)
    }

    fun onDelete(view:View) {
        dbHelper.deletePerson(1)
    }
}