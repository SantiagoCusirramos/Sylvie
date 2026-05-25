package com.sylvie.app.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sylvie.app.data.repository.SylvieRepository
import com.sylvie.app.databinding.FragmentHistoryBinding
import com.sylvie.app.utils.SharedPrefsManager
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: SylvieRepository
    private lateinit var prefsManager: SharedPrefsManager
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = SylvieRepository()
        prefsManager = SharedPrefsManager(requireContext())

        adapter = HistoryAdapter()
        binding.rvHistorial.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistorial.adapter = adapter

        cargarHistorial()
    }

    private fun cargarHistorial() {
        val usuarioId = prefsManager.obtenerUsuarioId()
        if (usuarioId == -1L) {
            binding.tvEmpty.visibility = View.VISIBLE
            return
        }

        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            val result = repository.obtenerHistorial(usuarioId)
            binding.progressBar.visibility = View.GONE

            result.onSuccess { historial ->
                if (historial.isEmpty()) {
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.rvHistorial.visibility = View.GONE
                } else {
                    binding.tvEmpty.visibility = View.GONE
                    binding.rvHistorial.visibility = View.VISIBLE
                    adapter.submitList(historial)
                }
            }.onFailure { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                binding.tvEmpty.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}