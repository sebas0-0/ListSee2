package com.example.listsee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.listsee.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        // Botón para navegar a registro
        binding.registerBt.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_RegisterFragment)
        }

        // Botón para iniciar sesión
        binding.LButton.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            var isValid = true

            if (email.isEmpty()) {
                binding.emailInput.error = "Campo requerido"
                isValid = false
            } else {
                binding.emailInput.error = null
            }

            if (password.isEmpty()) {
                binding.passwordInput.error = "Campo requerido"
                isValid = false
            } else {
                binding.passwordInput.error = null
            }

            if (isValid) {
                // Aquí iría la lógica real de login
                // Por ejemplo, autenticación con Firebase
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
