package com.ob.roomrxjava.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ob.roomrxjava.model.User;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface UserDao
{
    @Query("SELECT * from user")
    Flowable<List<User>> getAll();

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
    Flowable<User> findByName(String first, String last);

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    Flowable<List<User>> loadAllByIds(int[] userIds); ////Dönüş Türü Void olmayanlarda Flowable kullandık Single'da kullanabilirdik

    @Insert
    Completable insert(User user); //Dönüş Türü Void olanlarda Completable Kullandık

    @Insert
    Completable insertAll(User... users);

    @Delete
    Completable delete(User user);

    @Update
    Completable update(User user);
}
