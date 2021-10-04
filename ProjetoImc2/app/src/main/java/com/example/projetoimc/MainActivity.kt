package com.example.projetoimc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()

        //val é imutável - var é mutável

        val buttonCalcular = findViewById<Button>(R.id.button_calcular)

        val editTextPeso = findViewById<EditText>(R.id.edit_text_peso)
        val editTextAltura= findViewById<EditText>(R.id.edit_text_altura)
        val textViewResultado = findViewById<TextView>(R.id.text_view_resultado)

        buttonCalcular.setOnClickListener {
            val peso = editTextPeso.text.toString().toInt()
            val altura = editTextAltura.text.toString().toDouble()

            val imc = calcularImc(peso, altura)

            textViewResultado.text = String.format("%.1f", imc)
        }

    }
}