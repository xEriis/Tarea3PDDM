package com.erynlexa.tarea3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_usuario)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Cuando se aprete el botón de inicio esta actividad dejará de estar en el stack (no podemos loguearnos de nuevo)
        val botonVistaUno : Button = findViewById(R.id.botonInicio)
        botonVistaUno.setOnClickListener {
            val aperturaPrincipal = Intent(this, PantallaPrincipal::class.java)
            //Empieza la actividad
            startActivity(aperturaPrincipal)
            //Cierra el login del usuario
            //Para comprender mejor, pueden borrar finish y darle en el botón y luego darle atrás,
            //podrán loguearse de nuevo. Pero si la ponen esto se evita (buena práctica)
            finish()
        }
    }
}