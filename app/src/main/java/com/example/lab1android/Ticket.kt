package com.example.lab1android

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets")
data class Ticket (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "point_of_departure")
    var departure : String,

    @ColumnInfo(name = "point_of_arrival")
    var arrival : String,

    @ColumnInfo(name = "time")
    var time: String,

    )