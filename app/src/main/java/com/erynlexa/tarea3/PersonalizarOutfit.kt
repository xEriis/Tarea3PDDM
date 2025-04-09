package com.erynlexa.tarea3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import kotlin.math.ceil

class PersonalizarOutfit : Fragment() {
    //Text de vistas que necesitamos, las actualizaremos más tarde
    private lateinit var temperaturaClima: TextView
    private lateinit var descripcionClima: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personalizar_outfit, container, false)
        //Ubicamos el id de las vistas que vamos a actualizar
        temperaturaClima = view.findViewById(R.id.temperaturaClima)
        descripcionClima = view.findViewById(R.id.descripcionclima)

        val solicitaClima: Button = view.findViewById(R.id.actualizaClima)
        solicitaClima.setOnClickListener {
            //No podemos presionar 200,000 veces por segundo la consulta, pues nos banearían de la api jajaja
            if (solicitaClima.isEnabled) {
                solicitaClima.isEnabled = false
                //Comenté estas lineas para que no saliera el mensajito (Se me empezó a hacer molesto que me dijera actualizando cuando fallaba jajaja)
                //Toast.makeText(requireContext(), "¡Actualizando!", Toast.LENGTH_SHORT).show()
                obtenClima()
                Handler(Looper.getMainLooper()).postDelayed({
                    solicitaClima.isEnabled = true
                }, 5000)
            } else {
                Toast.makeText(requireContext(), "Espera 5 segundos antes de volver a consultar ):", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
    //Función actualizada con corrutinas como fue sugerido
    private fun obtenClima() {
        if (!isAdded) return
        lifecycleScope.launch {
            try {
                //Intentamos obtener el el JSON del clima
                val result = withContext(Dispatchers.IO) {
                    URL("https://api.openweathermap.org/data/2.5/weather?lat=19.3215473&lon=-99.1849188&appid=f6adade6884dd6823a33809867ec6cb9&units=metric&lang=es").readText(Charsets.UTF_8)
                }
                //Obtenemos el objeto JSON
                val objetoJSON = JSONObject(result)
                val main = objetoJSON.getJSONObject("main")
                val clima = objetoJSON.getJSONArray("weather").getJSONObject(0)

                //Obtenemos la descripción y le aplicamos ceil a la temperatura
                val descripcion = clima.getString("description")
                val temperatura = ceil(main.getString("temp").toFloat()).toInt()

                //Actualizamos los textView
                temperaturaClima.text = "$temperatura°C"
                descripcionClima.text = descripcion

            } catch (e: Exception) {
                //En cuyo caso dejaremos vacías las vistas
                temperaturaClima.text = ""
                descripcionClima.text = ""
            }
        }
    }
}