package com.sylvie.app.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sylvie.app.data.models.RecomendacionHistorial
import com.sylvie.app.databinding.ItemHistoryBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var items = listOf<RecomendacionHistorial>()

    fun submitList(list: List<RecomendacionHistorial>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecomendacionHistorial) {
            binding.tvCodigo.text = "Código: ${item.codigoBarras}"
            binding.tvResultado.text = item.resultado
            binding.tvMotivo.text = item.motivo
            binding.tvFecha.text = item.fecha

            when (item.resultado) {
                "APTO" -> binding.tvResultado.setTextColor(0xFF4CAF50.toInt())
                "NO_APTO" -> binding.tvResultado.setTextColor(0xFFF44336.toInt())
                "USAR_CON_PRECAUCION" -> binding.tvResultado.setTextColor(0xFFFF9800.toInt())
            }
        }
    }
}