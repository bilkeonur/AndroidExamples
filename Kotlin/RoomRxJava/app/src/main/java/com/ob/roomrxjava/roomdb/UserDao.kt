package com.ob.roomrxjava.roomdb

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Insert
import androidx.room.Delete
import com.ob.roomrxjava.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface UserDao {

    @Query("SELECT * from user")
    fun getAll(): Flowable<List<User>>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Flowable<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): Flowable<List<User>> ////Dönüş Türü Void olmayanlarda Flowable kullandık Single'da kullanabilirdik

    @Insert
    fun insert(user: User): Completable //Dönüş Türü Void olanlarda Completable Kullandık

    @Insert
    fun insertAll(vararg users: User): Completable

    @Delete
    fun delete(user: User): Completable

    @Update
    fun update(user: User): Completable
}