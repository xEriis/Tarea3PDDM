package com.erynlexa.tarea3

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class PantallaPrincipal : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)

        //Configura la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //Navigation Drawer
        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //Configuración para que el drawer funcione con la navegación
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.personalizarOutfit), // Fragmentos de nivel superior
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        //Control de las vistas a las cuales queremos ir
        navView.setNavigationItemSelectedListener { menuItem ->
            //Cuando presionemos una opción nos dirigirá a la cual debemos de ir
            when(menuItem.itemId){
                R.id.personalizarOutfit ->{
                    navController.navigate(R.id.personalizarOutfit)
                }
                R.id.perfilUsuario ->{
                    navController.navigate(R.id.perfilUsuario)
                }
                R.id.agregarRopa ->{
                    navController.navigate(R.id.agregarRopa)
                }
                R.id.armario ->{
                    navController.navigate(R.id.armario)
                }
            }
            //Cierra el Drawer Layout
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //Menú de opciones (tres puntos)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.opciones_menu, menu)
        return true
    }
    //Funciones para agregar a la lupa y a la opción de los tres puntitos
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> { /* Acción de búsqueda */ true }
            R.id.action_help -> { /* Mostrar ayuda */ true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}