package com.sylvie.app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sylvie.app.data.repository.SylvieRepository
import com.sylvie.app.databinding.FragmentProfileBinding
import com.sylvie.app.utils.SharedPrefsManager
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: SylvieRepository
    private lateinit var prefsManager: SharedPrefsManager
    private lateinit var adapter: RestriccionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = SylvieRepository()
        prefsManager = SharedPrefsManager(requireContext())

        val tipos = listOf("ALERGIA", "INTOLERANCIA", "PREFERENCIA")
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipos)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipo.adapter = adapterSpinner

        adapter = RestriccionAdapter { restriccion ->
            eliminarRestriccion(restriccion)
        }
        binding.rvRestricciones.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRestricciones.adapter = adapter

        cargarDatosUsuario()
        cargarRestricciones()

        binding.btnAgregarRestriccion.setOnClickListener {
            val ingrediente = binding.etIngrediente.text.toString().trim()
            val tipo = binding.spinnerTipo.selectedItem.toString()

            if (ingrediente.isEmpty()) {
                Toast.makeText(requireContext(), "Ingrese un ingrediente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            agregarRestriccion(ingrediente, tipo)
        }

        binding.btnCerrarSesion.setOnClickListener {
            prefsManager.limpiarSesion()
            findNavController().navigateUp()
        }
    }

    private fun cargarDatosUsuario() {
        val nombre = prefsManager.obtenerNombre()
        val email = prefsManager.obtenerEmail()
        binding.tvNombre.text = nombre ?: "--"
        binding.tvEmail.text = email ?: "--"
    }

    private fun cargarRestricciones() {
        val usuarioId = prefsManager.obtenerUsuarioId()
        if (usuarioId == -1L) return

        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            val result = repository.obtenerRestricciones(usuarioId)
            binding.progressBar.visibility = View.GONE

            result.onSuccess { restricciones ->
                adapter.submitList(restricciones)
            }.onFailure { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun agregarRestriccion(ingrediente: String, tipo: String) {
        val usuarioId = prefsManager.obtenerUsuarioId()
        if (usuarioId == -1L) return

        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            val result = repository.agregarRestriccion(usuarioId, ingrediente, tipo)
            binding.progressBar.visibility = View.GONE

            result.onSuccess {
                Toast.makeText(requireContext(), "Restricción agregada", Toast.LENGTH_SHORT).show()
                binding.etIngrediente.text?.clear()
                cargarRestricciones()
            }.onFailure { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun eliminarRestriccion(restriccion: com.sylvie.app.data.models.RestriccionResponse) {
        val usuarioId = prefsManager.obtenerUsuarioId()
        if (usuarioId == -1L) return

        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            val result = repository.eliminarRestriccion(usuarioId, restriccion.ingrediente)
            binding.progressBar.visibility = View.GONE

            result.onSuccess {
                Toast.makeText(requireContext(), "Restricción eliminada", Toast.LENGTH_SHORT).show()
                cargarRestricciones()
            }.onFailure { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}