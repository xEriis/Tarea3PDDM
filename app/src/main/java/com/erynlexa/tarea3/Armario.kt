package com.erynlexa.tarea3

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class Armario : Fragment() {

    private val allItems = listOf(
        ClothingItem(R.drawable.armario1, "AbajoP"),
        ClothingItem(R.drawable.armario2, "AbajoP"),
        ClothingItem(R.drawable.armario3, "AbajoP"),
        ClothingItem(R.drawable.armario4, "AbajoP"),
        ClothingItem(R.drawable.armario5, "AbajoP"),
        ClothingItem(R.drawable.armario6, "ArribaP"),
        ClothingItem(R.drawable.sobrero_amarillo, "ArribaP"),
        ClothingItem(R.drawable.armario7, "ArribaP"),
        ClothingItem(R.drawable.armario8, "ArribaP"),
        ClothingItem(R.drawable.vestido_beige, "ArribaP"),
        ClothingItem(R.drawable.armario9, "Zapatos"),
        ClothingItem(R.drawable.zapatillas_beige, "Zapatos"),
        ClothingItem(R.drawable.armario10, "Zapatos")
    )

    private lateinit var gridLayout: GridLayout
    private lateinit var allButton: Button
    private lateinit var abajoButton: Button
    private lateinit var arribaButton: Button
    private lateinit var zapatosButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_armario, container, false)

        // Inicializar vistas
        gridLayout = view.findViewById(R.id.grid_layout)
        allButton = view.findViewById(R.id.allButton)
        abajoButton = view.findViewById(R.id.abajoButton)
        arribaButton = view.findViewById(R.id.arribaButton)
        zapatosButton = view.findViewById(R.id.zapatosButton)

        // Mostrar todos los items inicialmente (y será boton de all)
        showItems(allItems)

        // Configurar listeners para los botones de categoría jaja es que no sabia como ponerle pero es prenda de Arriba o prenda Abajo
        allButton.setOnClickListener { filterItems("All") }
        abajoButton.setOnClickListener { filterItems("AbajoP") }
        arribaButton.setOnClickListener { filterItems("ArribaP") }
        zapatosButton.setOnClickListener { filterItems("Zapatos") }

        return view
    }

    private fun filterItems(category: String) {
        resetButtonStyles()

        // Resaltar el botón seleccionado
        when (category) {
            "All" -> {
                allButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
                showItems(allItems)
            }
            "AbajoP" -> {
                abajoButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
                showItems(allItems.filter { it.category == "AbajoP" })
            }
            "ArribaP" -> {
                arribaButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
                showItems(allItems.filter { it.category == "ArribaP" })
            }
            "Zapatos" -> {
                zapatosButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
                showItems(allItems.filter { it.category == "Zapatos" })
            }
        }
    }

    private fun showItems(items: List<ClothingItem>) {
        //  Limpiar el GridLayout
        gridLayout.removeAllViews()

        // Esto es para calcular dimensiones
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val margin = (8 * displayMetrics.density).toInt()
        val itemSize = (screenWidth / 4) - (2 * margin)

        //  Crear y agregar CardViews dinámicamente
        items.take(20).forEach { item -> // Máximo 20 items (4 columnas x 5 filas lo definí)
            // Crear CardView
            val cardView = CardView(requireContext()).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = itemSize
                    height = itemSize
                    setMargins(margin, margin, margin, margin)
                }
                radius = 8 * displayMetrics.density // Esquinas redondeadas (8dp)
                cardElevation = 4 * displayMetrics.density // Sombra (4dp)
            }

            // Crear ImageView
            ImageView(requireContext()).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                adjustViewBounds = true
                setImageResource(item.imageResId)

                // Agregar ImageView al CardView
                cardView.addView(this)
            }

            // Agregar CardView al GridLayout
            gridLayout.addView(cardView)
        }
    }

    private fun resetButtonStyles() {
        val defaultColor = Color.BLACK
        allButton.setTextColor(defaultColor)
        abajoButton.setTextColor(defaultColor)
        arribaButton.setTextColor(defaultColor)
        zapatosButton.setTextColor(defaultColor)
    }
    data class ClothingItem(val imageResId: Int, val category: String)
}