package com.example.lab1android

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {

    private lateinit var ticketRecyclerView: RecyclerView
    private lateinit var ticketAdapter: TicketAdapter
    private lateinit var db: MainDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        ticketRecyclerView = findViewById(R.id.ticketRecyclerView)
        ticketRecyclerView.layoutManager = LinearLayoutManager(this)

        db = MainDb.getDb(this)

        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()
        }

        ticketAdapter = TicketAdapter(
            onDelete = { ticket -> confirmDelete(ticket) },
            onUpdate = { ticket -> updateTicket(ticket) }
        )

        ticketRecyclerView.adapter = ticketAdapter

        loadTickets()
    }

    private fun loadTickets() {
        lifecycleScope.launch {
            db.getDao().getAllTickets().collect { tickets ->
                withContext(Dispatchers.Main) {
                    if (tickets.isEmpty()) {
                        Toast.makeText(this@HistoryActivity, "Історія порожня", Toast.LENGTH_SHORT).show()
                    }
                    ticketAdapter.submitList(tickets)
                }
            }
        }
    }

    private fun confirmDelete(ticket: Ticket) {
        AlertDialog.Builder(this)
            .setTitle("Видалити квиток")
            .setMessage("Ви певні, що хочете видалити цей квиток?")
            .setPositiveButton("Да") { _, _ ->
                deleteTicket(ticket)
            }
            .setNegativeButton("Відміна", null)
            .show()
    }

    private fun deleteTicket(ticket: Ticket) {
        lifecycleScope.launch(Dispatchers.IO) {
            db.getDao().deleteTicket(ticket)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@HistoryActivity, "Квиток видалено", Toast.LENGTH_SHORT).show()
            }
            loadTickets()
        }
    }

    private fun updateTicket(ticket: Ticket) {
        lifecycleScope.launch(Dispatchers.IO) {
            db.getDao().updateTicket(ticket)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@HistoryActivity, "Квиток оновлено", Toast.LENGTH_SHORT).show()
            }
            loadTickets()
        }
    }
}
