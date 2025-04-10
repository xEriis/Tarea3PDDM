package com.erynlexa.tarea3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.PatternsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.erynlexa.tarea3.databinding.ActivityLoginUsuarioBinding
import java.util.regex.Pattern

class LoginUsuario : AppCompatActivity() {
    private lateinit var binding : ActivityLoginUsuarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Botón para validar e ir a la pantalla principal si
        binding.botonInicio.setOnClickListener {
            if (validate()) {
                val aperturaPrincipal = Intent(this, PantallaPrincipal::class.java)
                startActivity(aperturaPrincipal)
                finish()
            }
        }
        //Cuando se aprete el botón de inicio esta actividad dejará de estar en el stack (no podemos loguearnos de nuevo)
        //val botonVistaUno : Button = findViewById(R.id.botonInicio)
        //botonVistaUno.setOnClickListener {
            //val aperturaPrincipal = Intent(this, PantallaPrincipal::class.java)
            //Empieza la actividad
            //startActivity(aperturaPrincipal)
            //Cierra el login del usuario
            //Para comprender mejor, pueden borrar finish y darle en el botón y luego darle atrás,
            //podrán loguearse de nuevo. Pero si la ponen esto se evita (buena práctica)
            //finish()
        //}
    }

    private fun validate(): Boolean {
        val result = arrayOf(validateCorreo(), validateContraseña())

        return if (false in result) {
            false
        } else {
            Toast.makeText(this, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun validateCorreo() : Boolean {
        val correo = binding.correo.editText?.text.toString()
        return if (correo.isEmpty()) {
            binding.correo.error = "Este campo no puede ser vacío"
            false
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(correo).matches()) {
            binding.correo.error = "Ingresar un correo válido"
            false
        } else {
            binding.correo.error = null
            true
        }
    }

    private fun validateContraseña() : Boolean {
        val contraseña = binding.contraseA.editText?.text.toString()
        val contraseñaRegex = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         // al menos un número
                    "(?=.*[a-z])" +         // al menos una minúscula
                    "(?=.*[A-Z])" +         // al menos una mayúscula
                    "(?=.*[@#$%^&+=])" +    // al menos un carácter especial
                    "(?=\\S+$)" +           // sin espacios en blanco
                    ".{4,}" +               // mínimo 4 caracteres
                    "$"
        )
        return if (contraseña.isEmpty()){
            binding.contraseA.error = "Campo vacío"
            false
        } else if (!contraseñaRegex.matcher(contraseña).matches()){
            binding.contraseA.error = "Contraseña muy corta"
            false
        } else {
            binding.contraseA.error = null
            true
        }
    }
}