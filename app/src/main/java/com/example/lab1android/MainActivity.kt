package com.example.lab1android

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editDeparture = findViewById<TextInputEditText>(R.id.editDeparture)
        val editArrival = findViewById<TextInputEditText>(R.id.editArrival)
        val editTime = findViewById<TextInputEditText>(R.id.editTime)
        val btnOk = findViewById<Button>(R.id.btnOk)
        val textView = findViewById<TextView>(R.id.textView)

        editTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    editTime.setText(formattedTime)
                },
                hour, minute, true
            )
            timePickerDialog.show()
        }

        btnOk.setOnClickListener {
            val departure = editDeparture.text.toString().trim()
            val arrival = editArrival.text.toString().trim()
            val time = editTime.text.toString().trim()

            if (departure.isEmpty() || arrival.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Будь ласка, заповніть всі поля!", Toast.LENGTH_SHORT).show()
            }
            else if(departure.equals(arrival, ignoreCase = true)){
                Toast.makeText(this, "Пункт відправлення і прибуття не можуть бути однаковими!", Toast.LENGTH_SHORT).show()
            }
            else {
                textView.text = "Ви обрали:\n🚉 Відправлення: $departure\n🏁 Прибуття: $arrival\n⏰ Час: $time"
            }
        }
    }
}
