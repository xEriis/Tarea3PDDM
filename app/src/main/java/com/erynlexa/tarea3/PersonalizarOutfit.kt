package com.erynlexa.tarea3

import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject
import java.net.URL
import kotlin.math.ceil

class PersonalizarOutfit : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalizar_outfit)

        // Configura la Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configura el menú de hamburguesa
        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.Abrir_drawer,
            R.string.Cerrar_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Maneja clics en el menú lateral
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_perfil -> { /* Navegar a Inicio */ }
                R.id.nav_armario -> { /* Navegar a Perfil */ }
                R.id.nav_add_ropa -> { /* Navegar a Configuración */ }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val solicitaClima : Button = findViewById(R.id.actualizaClima)
        solicitaClima.setOnClickListener {
            Toast.makeText(this, "Actualizando...", Toast.LENGTH_SHORT).show()
            obtenClima()
        }

    }

    // Configura el menú de opciones (tres puntos)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.opciones_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> { /* Acción de búsqueda */ true }
            R.id.action_help -> { /* Mostrar ayuda */ true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun obtenClima(){
        obtenClimaCiudad().execute()
    }

    inner class obtenClimaCiudad : AsyncTask<String, Void, String>(){

        override fun doInBackground(vararg p0: String?): String? {
            var response: String?
            try {
                response = URL("https://api.openweathermap.org/data/2.5/weather?lat=19.3215473&lon=-99.1849188&appid=f6adade6884dd6823a33809867ec6cb9&units=metric&lang=es")
                    .readText(Charsets.UTF_8)
            }catch (e : Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val objetoJSON = JSONObject(result)
                val main = objetoJSON.getJSONObject("main")
                val clima = objetoJSON.getJSONArray("weather").getJSONObject(0)

                val descripcion = clima.getString("description")
                val temperaturaString = main.getString("temp")
                val temperaturaNum = temperaturaString.toFloat()
                val temperatura = ceil(temperaturaNum).toInt()
                val temperaturaRedondeada = temperatura.toString()
                val temperaturaActual = temperaturaRedondeada + "°C"

                findViewById<TextView>(R.id.temperaturaClima).text = temperaturaActual
                findViewById<TextView>(R.id.descripcionclima).text = descripcion

            }catch (e: Exception){
                findViewById<TextView>(R.id.temperaturaClima).text = null
                findViewById<TextView>(R.id.descripcionclima).text = null
            }
        }
    }
}