package com.example.lab1android

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert
    suspend fun insertTicket(ticket : Ticket)

    @Query("SELECT * FROM tickets")
    fun getAllTickets() : Flow<List<Ticket>>

    @Delete
    suspend fun  deleteTicket(ticket: Ticket)

    @Update
    suspend fun updateTicket(ticket: Ticket)
}