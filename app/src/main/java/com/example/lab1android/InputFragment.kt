package com.example.lab1android

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.util.*

class InputFragment : Fragment(R.layout.input_fragment) {

    private lateinit var editDeparture: TextInputEditText
    private lateinit var editArrival: TextInputEditText
    private lateinit var editTime: TextInputEditText
    private lateinit var btnOk: Button
    private lateinit var btnHistory: Button
    private lateinit var db: MainDb

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editDeparture = view.findViewById(R.id.editDeparture)
        editArrival = view.findViewById(R.id.editArrival)
        editTime = view.findViewById(R.id.editTime)
        btnOk = view.findViewById(R.id.btnOk)
        btnHistory = view.findViewById(R.id.btnHistory)

        db = MainDb.getDb(requireContext())

        editTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, selectedHour, selectedMinute ->
                    val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    editTime.setText(formattedTime)
                },
                hour,
                minute,
                true
            )
            timePickerDialog.show()
        }

        btnOk.setOnClickListener {
            val departure = editDeparture.text.toString().trim()
            val arrival = editArrival.text.toString().trim()
            val time = editTime.text.toString().trim()

            if (departure.isEmpty() || arrival.isEmpty() || time.isEmpty()) {
                Toast.makeText(context, "Будь ласка, заповніть всі поля!", Toast.LENGTH_SHORT).show()
            } else if (departure.equals(arrival, ignoreCase = true)) {
                Toast.makeText(context, "Пункт відправлення і прибуття не можуть бути однаковими!", Toast.LENGTH_SHORT).show()
            } else {
                val ticket = Ticket(departure = departure, arrival = arrival, time = time)
                lifecycleScope.launch {
                    try {
                        db.getDao().insertTicket(ticket)
                        Toast.makeText(context, "Квиток додано в історію", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Помилка при збережені квитка: ${e.message}", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }

                val resultFragment = ResultFragment.newInstance(departure, arrival, time)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, resultFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        btnHistory.setOnClickListener {
            val intent = Intent(activity, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}
