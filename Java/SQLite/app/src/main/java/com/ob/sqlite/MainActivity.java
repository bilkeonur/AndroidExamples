package com.ob.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ob.sqlite.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ActivityMainBinding binding;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Button btnAdd = binding.btnAdd;
        Button btnFetch = binding.btnFetch;
        Button btnUpdate = binding.btnUpdate;
        Button btnDelete = binding.btnDelete;

        btnAdd.setOnClickListener(view1 -> {
            Person person1 = new Person(1,"Onur","Bilke");
            Person person2 = new Person(2,"Cansu","Bilke");
            Person person3 = new Person(3,"Ada","Bilke");
            Person person4 = new Person(4,"Deniz","Bilke");

            dbHelper.addPerson(person1);
            dbHelper.addPerson(person2);
            dbHelper.addPerson(person3);
            dbHelper.addPerson(person4);
        });

        btnFetch.setOnClickListener(view1 -> {
            ArrayList<Person> persons = dbHelper.getPersons();

            for (Person person : persons) {
                System.out.println("Id : " + person.id + " Name : " + person.name +" Surname : " + person.surname);
            }
        });

        btnUpdate.setOnClickListener(view1 -> {
            Person person = new Person(1,"On","Bi");
            dbHelper.updatePerson(person);
        });

        btnDelete.setOnClickListener(view1 -> dbHelper.deletePerson(1));

        dbHelper = new DBHelper(this);
    }
}