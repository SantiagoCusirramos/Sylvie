package com.sylvie.app.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sylvie.app.data.api.RetrofitInstance
import com.sylvie.app.data.models.AnalisisResponse
import com.sylvie.app.databinding.FragmentResultBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import androidx.core.view.isVisible
import com.sylvie.app.data.models.AnalisisRequest

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val codigoBarras = arguments?.getString("codigoBarras") ?: ""
        if (codigoBarras.isNotEmpty()) {
            analizarProducto(codigoBarras)
        }
    }

    private fun analizarProducto(codigoBarras: String) {
        lifecycleScope.launch {
            binding.progressBar.isVisible = true
            try {
                val request = AnalisisRequest(codigoBarras)
                val response = RetrofitInstance.api.analizarProducto(request)
                if (response.isSuccessful) {
                    response.body()?.let { mostrarResultado(it) }
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                Toast.makeText(requireContext(), "Error de red", Toast.LENGTH_SHORT).show()
            } catch (e: HttpException) {
                Toast.makeText(requireContext(), "Error del servidor", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.isVisible = false
            }
        }
    }

    private fun mostrarResultado(analisis: AnalisisResponse) {
        binding.tvPuntuacion.text = "${analisis.puntuacion} / 100"
        binding.tvClasificacion.text = analisis.clasificacion
        binding.tvMensaje.text = analisis.mensaje

        when (analisis.clasificacion) {
            "BUENO" -> binding.cardResult.setCardBackgroundColor(0xFF4CAF50.toInt())
            "RIESGO_BAJO" -> binding.cardResult.setCardBackgroundColor(0xFF8BC34A.toInt())
            "REGULAR" -> binding.cardResult.setCardBackgroundColor(0xFFFFC107.toInt())
            "RIESGO_ALTO" -> binding.cardResult.setCardBackgroundColor(0xFFFF9800.toInt())
            "MALO" -> binding.cardResult.setCardBackgroundColor(0xFFF44336.toInt())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}