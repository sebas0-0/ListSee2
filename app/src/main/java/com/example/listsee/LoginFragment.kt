package com.example.listsee

import com.example.listsee.ListActivity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.listsee.databinding.FragmentLoginBinding
import com.example.listsee.utils.FragmentCommunicator
import com.example.listsee.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var communicator: FragmentCommunicator
    var isValid: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        communicator = requireActivity() as ActivityOnboarding
        setupView()
        setupObservers()
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
                requestLogin()
            }
        }
    }

    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }
        viewModel.sessionValid.observe(viewLifecycleOwner) { sessionValid ->
            if (sessionValid) {
                val intent = Intent(activity, ListActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                Toast.makeText(activity, "Ingreso invalido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestLogin() {

            viewModel.requestLogin(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
