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
import com.sylvie.app.databinding.FragmentLoginBinding
import com.sylvie.app.utils.SharedPrefsManager
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: SylvieRepository
    private lateinit var prefsManager: SharedPrefsManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = SylvieRepository()
        prefsManager = SharedPrefsManager(requireContext())

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            realizarLogin(email, password)
        }

        binding.tvRegistro.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_registro)
        }
    }

    private fun realizarLogin(email: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnLogin.isEnabled = false

        lifecycleScope.launch {
            val result = repository.loginUsuario(email, password)
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.isEnabled = true

            result.onSuccess { loginResponse ->
                prefsManager.guardarToken(loginResponse.token)
                prefsManager.guardarEmail(loginResponse.email)
                prefsManager.guardarNombre(loginResponse.nombre)

                Toast.makeText(requireContext(), "Bienvenido ${loginResponse.nombre}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_login_to_main)
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