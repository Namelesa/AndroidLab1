package com.example.lab1android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TicketAdapter(private val onDelete: (Ticket) -> Unit,
    private val onUpdate: (Ticket) -> Unit)
    : ListAdapter<Ticket, TicketAdapter.TicketViewHolder>(TicketDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = getItem(position)
        holder.bind(ticket, onDelete, onUpdate)
    }

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ticketInfo: TextView = itemView.findViewById(R.id.ticketInfo)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        private val editButton: Button = itemView.findViewById(R.id.editButton)

        fun bind(ticket: Ticket, onDelete: (Ticket) -> Unit, onEdit: (Ticket) -> Unit) {
            ticketInfo.text = ticket.toString()
            deleteButton.setOnClickListener { onDelete(ticket) }
            editButton.setOnClickListener { onEdit(ticket) }
        }
    }

    class TicketDiffCallback : DiffUtil.ItemCallback<Ticket>() {
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean = oldItem == newItem
    }
}
