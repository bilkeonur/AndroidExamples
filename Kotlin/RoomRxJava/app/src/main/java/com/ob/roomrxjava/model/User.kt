package com.ob.roomrxjava.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(

    @ColumnInfo(name = "first_name")
    var firstName:String,

    @ColumnInfo(name = "last_name")
    var lastName: String) {

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}