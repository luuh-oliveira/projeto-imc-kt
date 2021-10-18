package com.example.projetoimc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import java.util.*

class CadastroUsuarioActivity : AppCompatActivity() {

    lateinit var etEmail : EditText
    lateinit var etSenha : EditText
    lateinit var etNome : EditText
    lateinit var etProfissao : EditText
    lateinit var etAltura : EditText
    lateinit var etDataNascimento : EditText
    lateinit var rbMasculino : RadioButton
    lateinit var rbFeminino : RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        supportActionBar!!.title = "Perfil"

        val calendario = Calendar.getInstance()

        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        etDataNascimento = findViewById(R.id.et_data_nascimento)

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

        if (validarCampos()){
            //salvar
        }

        return true
    }

    fun validarCampos() : Boolean {

        var valido = true

        etEmail = findViewById(R.id.et_email)
        etSenha = findViewById(R.id.et_senha)
        etNome = findViewById(R.id.et_nome)
        etProfissao = findViewById(R.id.et_profissao)
        etAltura = findViewById(R.id.et_altura)
        etDataNascimento = findViewById(R.id.et_data_nascimento)
        rbMasculino = findViewById(R.id.rb_masculino)
        rbFeminino = findViewById(R.id.rb_feminino)

        if (etEmail.text.isEmpty()){
            etEmail.error = "O e-mail é obrigatório!"
            valido = false

        } else if (etSenha.text.isEmpty() || etSenha.text.length < 8){
            etSenha.error = "A senha é obrigatória e deve ter no mínimo 8 caracteres!"
            valido = false

        } else if(etNome.text.isEmpty()){
            etNome.error = "O nome é obrigatório!"
            valido = false

        } else if (etProfissao.text.isEmpty()){
            etProfissao.error = "A profissão é obrigatória!"
            valido = false

        } else if (etAltura.text.isEmpty()){
            etAltura.error = "A altura é obrigatória!"
            valido = false

        } else if (etDataNascimento.text.isEmpty()){
            etDataNascimento.error = "A data de nascimento é obrigatória!"
            valido = false

        }


//        else if (!rbMasculino.isChecked || !rbFeminino.isChecked){
//            Toast.makeText(this, "Selecione uma opção",
//                    Toast.LENGTH_LONG).show();
//        }


        return valido

    }


}