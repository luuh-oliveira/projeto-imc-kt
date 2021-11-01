package com.example.projetoimc.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.projetoimc.R
import com.example.projetoimc.model.Usuario
import com.example.projetoimc.utils.convertStringToLocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

const val CODE_IMAGE = 100

class CadastroUsuarioActivity : AppCompatActivity() {

    lateinit var etEmail : EditText
    lateinit var etSenha : EditText
    lateinit var etNome : EditText
    lateinit var etProfissao : EditText
    lateinit var etAltura : EditText
    lateinit var etDataNascimento : EditText
    lateinit var rbMasculino : RadioButton
    lateinit var rbFeminino : RadioButton
    lateinit var tvTrocarFoto: TextView
    lateinit var fotoPerfil: ImageView
    var imageBitMap: Bitmap? = null


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
        tvTrocarFoto = findViewById(R.id.tv_trocar_foto)
        fotoPerfil = findViewById(R.id.foto_perfil)

        supportActionBar!!.title = "Novo Usuário"

        //Abrir a galeria de fotos para escolher uma foto
        tvTrocarFoto.setOnClickListener {
            abrirGaleria()
        }

        val calendario = Calendar.getInstance()

        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        etDataNascimento = findViewById(R.id.et_data_nascimento)

        etDataNascimento.setOnClickListener {
            val dp = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, _ano, _mes, _dia ->


                        var diaFinal = _dia
                        var mesFinal = _mes + 1

                        var mesString = "$mesFinal"
                        var diaString = "$diaFinal"

                        if (mesFinal < 10) {
                            mesString = "0$mesFinal"
                        }

                        if (diaFinal < 10) {
                            diaString = "0$diaFinal"
                        }


                        etDataNascimento.setText("$diaString/$mesString/$_ano")
                    }, ano, mes, dia)

            dp.show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imagem: Intent?) {
        super.onActivityResult(requestCode, resultCode, imagem)

        //verificar o código do resultado
//        Log.i("xpto", resultCode.toString())

        if (requestCode == CODE_IMAGE && resultCode == -1){
            //recuperar a imagem do stream
            val fluxoImagem = contentResolver.openInputStream(imagem!!.data!!)

            //converter os bits em um bit map
            imageBitMap =  BitmapFactory.decodeStream(fluxoImagem)

            //colocar o gitmap no image view
            fotoPerfil.setImageBitmap(imageBitMap)

        }

    }

    private fun abrirGaleria() {

        //abrir a galeria de imagens do dispositivo
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        //abrir a activity responsável por exibir as imagens
        //esta activity retornará o conteúdo selecionado para nosso app
        startActivityForResult(Intent.createChooser(intent, "Escolha uma foto"), CODE_IMAGE)

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
                    },
                    ""
//                    converterBitmapParaBase64(imageBitMap!!)
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
            editor.putString("fotoPerfil", usuario.fotoPerfil)
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