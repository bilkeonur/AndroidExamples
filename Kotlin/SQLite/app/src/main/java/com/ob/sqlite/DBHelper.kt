package com.ob.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context:Context): SQLiteOpenHelper(context, databaseName, null, databaseVersion)
{
    companion object {
        const val databaseName = "test"
        const val databaseVersion = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE person (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, surname TEXT)"

        if(db!=null) {
            db.execSQL(query)
        }
    }

    fun addPerson(person: Person) {
        val db = this.writableDatabase
        val values =ContentValues()
        values.put("name",person.name)
        values.put("surname",person.surname)
        db.insert("person", null, values)
        db.close()
    }

    fun getPersons():ArrayList<Person> {

        val persons = ArrayList<Person>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM person", null)

        val idIndex = cursor.getColumnIndex("id")
        val nameIndex = cursor.getColumnIndex("name")
        val surnameIndex = cursor.getColumnIndex("surname")

        while (cursor.moveToNext()) {
            val id = cursor.getInt(idIndex)
            val name = cursor.getString(nameIndex)
            val surname = cursor.getString(surnameIndex)

            val person = Person(id,name,surname)
            persons.add(person)
        }

        cursor.close()
        db.close()

        return persons
    }

    fun updatePerson(person: Person)
    {
        val db = this.readableDatabase
        val values =ContentValues()

        values.put("name",person.name)
        values.put("surname",person.surname)

        db.update("person", values, "id = ?",arrayOf(person.id.toString()))
        db.close()
    }

    fun deletePerson(id:Int)
    {
        val db = this.readableDatabase
        db.delete("person","id = ?", arrayOf(id.toString()))
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS person"

        if(db!=null) {
            db.execSQL(query)
        }
    }
}