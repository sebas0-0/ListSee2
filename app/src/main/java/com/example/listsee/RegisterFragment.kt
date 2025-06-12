package com.example.listsee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.listsee.databinding.FragmentRegisterBinding
import com.example.listsee.utils.FragmentCommunicator
import com.example.listsee.viewModel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by viewModels<RegisterViewModel>()
    private lateinit var communicator: FragmentCommunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        communicator = requireActivity() as ActivityOnboarding
        setview()
        setupObservers()
        return binding.root

    }

    private fun setview() {
        binding.icon.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
        }

        binding.LButton.setOnClickListener {
            val nombre = binding.inputNombre.text.toString().trim()
            val correo = binding.inputCorreo.text.toString().trim()
            val contrasena = binding.inputContrasena.text.toString().trim()

            if (nombre.isEmpty()) {
                binding.etNombre.error = "El nombre es obligatorio"
            } else {
                binding.etNombre.error = null
            }

            if (correo.isEmpty()) {
                binding.etCorreoRegister.error = "El correo es obligatorio"
            } else {
                binding.etCorreoRegister.error = null
            }

            if (contrasena.isEmpty()) {
                binding.etContrasenaRegister.error = "La contraseña es obligatoria"
            } else {
                binding.etContrasenaRegister.error = null
            }

            if (nombre.isNotEmpty() && correo.isNotEmpty() && contrasena.isNotEmpty()) {
                viewModel.requestRegister(binding.inputCorreo.text.toString(),
                    binding.inputContrasena.text.toString()
                )
            }
        }
    }

    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }
        viewModel.createdUser.observe(viewLifecycleOwner) { createdUser ->
            if (createdUser) {
                findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}