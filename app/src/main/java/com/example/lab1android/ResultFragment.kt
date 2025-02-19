package com.example.lab1android

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ResultFragment : Fragment(R.layout.result_fragment) {

    private lateinit var textView: TextView
    private lateinit var btnCancel: Button

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView = view.findViewById(R.id.textViewResult)
        btnCancel = view.findViewById(R.id.btnCancel)

        val departure = arguments?.getString("departure")
        val arrival = arguments?.getString("arrival")
        val time = arguments?.getString("time")

        textView.text = "Ви обрали:\n🚉 Відправлення: $departure\n🏁 Прибуття: $arrival\n⏰ Час: $time"

        btnCancel.setOnClickListener {
            // Очистить поля ввода в InputFragment и покажет его снова
            val inputFragment = InputFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, inputFragment)
                .commit()
        }
    }

    companion object {
        fun newInstance(departure: String, arrival: String, time: String): ResultFragment {
            val fragment = ResultFragment()
            val bundle = Bundle().apply {
                putString("departure", departure)
                putString("arrival", arrival)
                putString("time", time)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}
