package com.ob.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String databaseName = "test";
    private static final int databaseVersion = 1;

    public DBHelper(Context context) {
        super(context,databaseName,null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE person (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, surname TEXT)";

        if(db!=null) {
            db.execSQL(query);
        }
    }

    void addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",person.name);
        values.put("surname",person.surname);
        db.insert("person", null, values);
        db.close();
    }

    ArrayList<Person> getPersons() {

        ArrayList<Person> persons = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM person", null);

        int idIndex = cursor.getColumnIndex("id");
        int nameIndex = cursor.getColumnIndex("name");
        int surnameIndex = cursor.getColumnIndex("surname");

        while (cursor.moveToNext()) {
            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String surname = cursor.getString(surnameIndex);

            Person person = new Person(id,name,surname);
            persons.add(person);
        }

        cursor.close();
        db.close();

        return persons;
    }

    void updatePerson(Person person)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",person.name);
        values.put("surname",person.surname);

        String[] whereArgs = {String.valueOf(person.id)};

        db.update("person", values, "id = ?", whereArgs);
        db.close();
    }

    void deletePerson(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] whereArgs = {String.valueOf(id)};
        db.delete("person","id = ?", whereArgs);
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query = "DROP TABLE IF EXISTS person";

        if(db!=null) {
            db.execSQL(query);
        }
    }
}
