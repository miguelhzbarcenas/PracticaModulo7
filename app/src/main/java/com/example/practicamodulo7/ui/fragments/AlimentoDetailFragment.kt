package com.example.practicamodulo7.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.practicamodulo7.utils.Constants
import com.bumptech.glide.Glide
import com.example.practicamodulo7.R
import com.example.practicamodulo7.application.PracticaModulo7app
import com.example.practicamodulo7.data.AlimentoRepository
import com.example.practicamodulo7.databinding.FragmentAlimentoDetailBinding
import kotlinx.coroutines.launch
import java.io.IOException

private const val ALIMENTO_ID = "alimento_id"

class AlimentoDetailFragment : Fragment() {

    private var _binding: FragmentAlimentoDetailBinding? = null
    private val binding get() = _binding!!

    private var alimentoId: String? = null

    private lateinit var repository: AlimentoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            alimentoId = args.getString(ALIMENTO_ID)
            Log.d(Constants.LOGTAG, getString(R.string.id_recibido, alimentoId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlimentoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = (requireActivity().application as PracticaModulo7app).repository

        lifecycleScope.launch {
            try {
                alimentoId?.let{
                    val alimentoDetail = repository.getAlimentoDetail(it)

                    binding.tvTitle.text = alimentoDetail.nombre
                    val texto = getString(
                        R.string.calorias_proteinasgr_carbohidratosgr_grasasgr_porcion,
                        alimentoDetail.calorias,
                        alimentoDetail.proteinas.toString(),
                        alimentoDetail.carbohidratos.toString(),
                        alimentoDetail.grasas.toString(),
                        alimentoDetail.porcion
                    )
                    binding.tvLongDesc.text = texto

                    Glide.with(requireActivity())
                        .load(alimentoDetail.imagenUrl)
                        .into(binding.ivImage)
                }

            } catch (_: IOException) {
                //Manejamos la excepción
            } catch (_: Exception) {
                //Manejamos la excepción
            } finally {
                binding.pbLoading.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(alimentoId: String) =
            AlimentoDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ALIMENTO_ID, alimentoId)
                }
            }
    }
}