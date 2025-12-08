package com.example.practicamodulo7.ui.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.practicamodulo7.R
import com.example.practicamodulo7.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateInput(email, password)) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            Toast.makeText(
                                requireContext(), "Error al iniciar sesión, verifica tus datos o registrate .",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUI(null)
                        }
                    }
            }
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateInput(email, password)) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            updateUI(user)
                            Toast.makeText(
                                requireContext(),
                                "Usuario registrado exitosamente",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(), "Registro fallido.",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUI(null)
                        }
                    }
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            val email = binding.etEmail.text.toString()
            if (isValidEmail(email)) {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Se ha enviado un correo para recuperar tu contraseña.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Error al enviar correo de recuperación.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Por favor ingresa un correo electrónico válido para recuperar la contraseña",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidEmail(email)) {
            Toast.makeText(requireContext(), "Por favor ingresa un correo electrónico válido", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(requireContext(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fcvMain, AlimentosListFragment())
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}