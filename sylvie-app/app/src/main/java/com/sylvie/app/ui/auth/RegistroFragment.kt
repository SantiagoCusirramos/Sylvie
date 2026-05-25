package com.sylvie.app.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sylvie.app.R
import com.sylvie.app.data.repository.SylvieRepository
import com.sylvie.app.databinding.FragmentRegistroBinding
import kotlinx.coroutines.launch

class RegistroFragment : Fragment() {

    private var _binding: FragmentRegistroBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: SylvieRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = SylvieRepository()

        binding.btnRegistro.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            realizarRegistro(nombre, email, password)
        }

        binding.tvLogin.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun realizarRegistro(nombre: String, email: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnRegistro.isEnabled = false

        lifecycleScope.launch {
            val result = repository.registrarUsuario(nombre, email, password)
            binding.progressBar.visibility = View.GONE
            binding.btnRegistro.isEnabled = true

            result.onSuccess { usuario ->
                Toast.makeText(requireContext(), "Registro exitoso. Ahora inicia sesión.", Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
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