package com.example.practicamodulo7.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicamodulo7.ui.adapters.AlimentosAdapter
import com.example.practicamodulo7.utils.Constants
import com.example.practicamodulo7.R
import com.example.practicamodulo7.application.PracticaModulo7app
import com.example.practicamodulo7.data.AlimentoRepository
import com.example.practicamodulo7.databinding.FragmentAlimentoListBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import okio.IOException


class AlimentosListFragment : Fragment() {

    private var _binding: FragmentAlimentoListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: AlimentoRepository
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAlimentoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        repository = (requireActivity().application as PracticaModulo7app).repository

        setupToolbar()

        lifecycleScope.launch {
            try {
                val alimentos = repository.getAlimentos()

                binding.rvGames.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = AlimentosAdapter(alimentos){ selectedAlimento ->
                        selectedAlimento.id?.let { id ->
                            requireActivity().supportFragmentManager.beginTransaction().replace(
                                R.id.fcvMain,
                                AlimentoDetailFragment.newInstance(id.toString())
                            ).addToBackStack(null)
                                .commit()
                        }
                    }
                }
            } catch (_: IOException) {

                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_hay_conexion),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } catch (e: Exception) {
                Log.d(Constants.LOGTAG, "Error: ${e.message}")
                e.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_inesperado),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }finally {
                binding.pbLoading.visibility = View.INVISIBLE
            }

        }

    }

    private fun setupToolbar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_logout -> {
                    auth.signOut()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fcvMain, LoginFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}