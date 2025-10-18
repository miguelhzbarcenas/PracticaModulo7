package com.example.practicamodulo7.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practicamodulo7.data.remote.model.AlimentoDto
import com.example.practicamodulo7.databinding.AlimentoElementBinding

class AlimentoViewHolder(
    private val binding: AlimentoElementBinding,
    private val onAlimentoClick: (AlimentoDto) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    private var currentItem: AlimentoDto? = null

    init{
        binding.root.setOnClickListener {
            currentItem?.let(onAlimentoClick)
        }
    }

    fun bind(alimento: AlimentoDto){
        currentItem = alimento

        binding.tvNombreAlimento.text = alimento.nombre

        Glide.with(binding.root.context)
            .load(alimento.imagenUrl)
            .into(binding.ivImagenAlimento)
    }

    companion object{
        fun create(
            parent: ViewGroup,
            onAlimentoClick: (AlimentoDto) -> Unit
        ): AlimentoViewHolder {
            val binding = AlimentoElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return AlimentoViewHolder(binding, onAlimentoClick)
        }
    }

}