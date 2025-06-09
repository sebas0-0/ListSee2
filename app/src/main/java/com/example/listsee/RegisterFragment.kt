package com.example.listsee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.listsee.databinding.FragmentRegisterBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setview()
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
                // Aquí va la lógica para continuar el proceso de registro
                // Por ejemplo: guardar en base de datos, navegar, etc.
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}