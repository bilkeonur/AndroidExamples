package com.ob.roomrxjava.view

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.ob.roomrxjava.databinding.ActivityMainBinding
import com.ob.roomrxjava.model.User
import com.ob.roomrxjava.roomdb.UserDao
import com.ob.roomrxjava.roomdb.UserDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: UserDatabase
    private lateinit var userDao: UserDao
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val btnGetAll: Button = binding.btnGetAll
        val btnFindByName: Button = binding.btnFindByName
        val btnLoadAllByIds: Button = binding.btnLoadAllByIds
        val btnInsert: Button = binding.btnInsert
        val btnInsertAll: Button = binding.btnInsertAll
        val btnDelete: Button = binding.btnDelete
        val btnUpdate: Button = binding.btnUpdate

        db = Room.databaseBuilder(applicationContext, UserDatabase::class.java,"Users").build()
        userDao = db.userDao()

        btnGetAll.setOnClickListener {
            compositeDisposable.add(
                userDao.getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleGetAllResponse)
            )
        }

        btnFindByName.setOnClickListener {
            compositeDisposable.add(
                userDao.findByName("Onur","Bilke")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleFindByNameResponse)
            )
        }

        btnLoadAllByIds.setOnClickListener {
            compositeDisposable.add(
                userDao.loadAllByIds(intArrayOf(3,4))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleLoadAllByIdsResponse)
            )
        }

        btnInsert.setOnClickListener {
            val user = User("Cansu","Bilke")

            compositeDisposable.add(
                userDao.insert(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleInsertResponse)
            )
        }

        btnInsertAll.setOnClickListener {
            val user1 = User("Onur","Bilke")
            val user2 = User("Cansu","Bilke")
            val user3 = User("Ada","Bilke")
            val user4 = User("Deniz","Bilke")

            compositeDisposable.add(
                userDao.insertAll(user1,user2,user3,user4)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleInsertAllResponse)
            )
        }

        btnDelete.setOnClickListener {
            val user = User("Onur","Bilke")
            user.uid = 1

            compositeDisposable.add(
                userDao.delete(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleDeleteResponse)
            )
        }

        btnUpdate.setOnClickListener {
            val user = User("On","Bi")
            user.uid = 3

            compositeDisposable.add(
                userDao.update(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleUpdateResponse)
            )
        }
    }

    private fun handleInsertAllResponse() {
        Toast.makeText(applicationContext, "Inserted All", Toast.LENGTH_LONG).show()
    }

    private fun handleLoadAllByIdsResponse(users: List<User>) {
        for (user in users) {
            println("Id : ${user.uid} Name : ${user.firstName} Surname : ${user.lastName}")
        }
    }

    private fun handleFindByNameResponse(user: User) {
        //DB de birden fazla aynı isimde kayıt varsa ilk kayıt geliyor
        println("Id : ${user.uid} Name : ${user.firstName} Surname : ${user.lastName}")
    }

    private fun handleGetAllResponse(users: List<User>) {
        for (user in users) {
            println("Id : ${user.uid} Name : ${user.firstName} Surname : ${user.lastName}")
        }
    }

    private fun handleInsertResponse() {
        Toast.makeText(applicationContext, "Inserted", Toast.LENGTH_LONG).show()
    }

    private fun handleDeleteResponse() {
        Toast.makeText(applicationContext, "Deleted", Toast.LENGTH_LONG).show()
    }

    private fun handleUpdateResponse() {
        Toast.makeText(applicationContext, "Updated", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}