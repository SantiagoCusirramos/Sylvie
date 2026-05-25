package com.sylvie.app.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sylvie.app.data.models.RestriccionResponse
import com.sylvie.app.databinding.ItemRestriccionBinding

class RestriccionAdapter(
    private val onDeleteClick: (RestriccionResponse) -> Unit
) : RecyclerView.Adapter<RestriccionAdapter.ViewHolder>() {

    private var items = listOf<RestriccionResponse>()

    fun submitList(list: List<RestriccionResponse>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRestriccionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemRestriccionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restriccion: RestriccionResponse) {
            binding.tvIngrediente.text = restriccion.ingrediente
            binding.tvTipo.text = restriccion.tipo

            binding.btnEliminar.setOnClickListener {
                onDeleteClick(restriccion)
            }
        }
    }
}