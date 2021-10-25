package com.example.projetoimc.ui

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.projetoimc.R
import com.example.projetoimc.model.Usuario
import com.example.projetoimc.utils.convertStringToLocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

        etEmail = findViewById(R.id.et_email)
        etSenha = findViewById(R.id.et_senha)
        etNome = findViewById(R.id.et_nome)
        etProfissao = findViewById(R.id.et_profissao)
        etAltura = findViewById(R.id.et_altura)
        etDataNascimento = findViewById(R.id.et_data_nascimento)
        rbMasculino = findViewById(R.id.rb_masculino)
        rbFeminino = findViewById(R.id.rb_feminino)

        supportActionBar!!.title = "Novo Usuário"

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
            //criar o objeto usuario
            val nascimento = convertStringToLocalDate(etDataNascimento.text.toString())

            val usuario = Usuario(
                    1,
                    etNome.text.toString(),
                    etEmail.text.toString(),
                    etSenha.text.toString(),
                    0,
                    etAltura.text.toString().toDouble(),
                    LocalDate.of(
                            nascimento.year,
                            nascimento.monthValue,
                            nascimento.dayOfMonth
                    ),
                    etProfissao.text.toString(),
                    if(rbFeminino.isChecked){
                        'F'
                    } else{
                        'M'
                    }
            )

            //salvar registro em um SharedPreferences

            //a instrução abaixo ira criar um arquivo SharedPreferences se não existir
            //se existir, ele será aberto para edição
            val dados = getSharedPreferences("usuario", Context.MODE_PRIVATE)

            //criação de um objeto que permitirá a edição dos dados do arquivo SharedPreferences
            val editor = dados.edit()
            editor.putInt("id", usuario.id)
            editor.putString("nome", usuario.nome)
            editor.putString("email", usuario.email)
            editor.putString("senha", usuario.senha)
            editor.putInt("peso", usuario.peso)
            editor.putFloat("altura", usuario.altura.toFloat())
            editor.putString("dataNascimento", usuario.dataNascimento.toString())
            editor.putString("profissao", usuario.profissao)
            editor.putString("sexo", usuario.sexo.toString())
            editor.apply()
        }

        Toast.makeText(this, "Usuário cadastrado", Toast.LENGTH_SHORT).show()

        return true
    }

    fun validarCampos() : Boolean {

        var valido = true

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
        else if (!rbMasculino.isChecked || !rbFeminino.isChecked){
            Toast.makeText(this, "Selecione Masculino ou Feminino", Toast.LENGTH_LONG).show()
            valido = false
        }

        return valido

    }


}