package com.example.projetoimc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import java.util.*

class CadastroUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        supportActionBar!!.title = "Perfil"

        val calendario = Calendar.getInstance()

        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        val etDataNascimento = findViewById<EditText>(R.id.et_data_nascimento)

        etDataNascimento.setOnClickListener {
            val dp = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, _ano, _mes, _dia ->
                        etDataNascimento.setText("$_dia/${_mes + 1}/$_ano") }, ano, mes, dia)

            dp.show()
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save){
            Toast.makeText(this, "Salvar", Toast.LENGTH_SHORT).show()
        }
        return true
    }


}