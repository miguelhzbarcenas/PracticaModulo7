package com.example.practicamodulo7.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicamodulo7.data.remote.model.AlimentoDto

class AlimentosAdapter(
    private val alimentos: List<AlimentoDto>,
    private val onAlimentoClick: (AlimentoDto) -> Unit
): RecyclerView.Adapter<AlimentoViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlimentoViewHolder = AlimentoViewHolder.create(parent, onAlimentoClick)

    override fun onBindViewHolder(
        holder: AlimentoViewHolder,
        position: Int
    ) {
        holder.bind(alimentos[position])
    }

    override fun getItemCount(): Int = alimentos.size

}