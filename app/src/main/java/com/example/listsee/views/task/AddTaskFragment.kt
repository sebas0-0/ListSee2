package com.example.listsee.views.task

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.listsee.R
import java.text.SimpleDateFormat
import java.util.Calendar
import com.example.listsee.databinding.FragmentTaskBinding
import com.example.listsee.utils.FragmentCommunicator
import com.example.listsee.viewModelList.AddTaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.UUID
import java.util.Locale

@AndroidEntryPoint

class AddTaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<AddTaskViewModel>()
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        communicator = requireActivity() as ListActivity
        setupView()
        return binding.root

    }

    private fun setupView() {
        binding.etFecha.apply {
            isFocusable = false
            isClickable = true
        }

        binding.btnAddTask.setOnClickListener {
            val id = UUID.randomUUID().toString()

            viewModel.createTaskInfo(
                id,
                binding.etNombre.text.toString(),
                binding.etDescripcion.text.toString(),
                Date())
            format.parse(binding.etFecha.text.toString()) ?: Date()
        }

        binding.etFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(),{ _, year, month, dayOfMonth ->
                val fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                binding.etFecha.setText(fechaSeleccionada)
            }, year, month, day)

            datePicker.show()
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_AddTaskFragment_to_TasksFragment)
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }

        viewModel.operationSuccess.observe(viewLifecycleOwner) { operationSuccess ->
            if (operationSuccess) {
                findNavController().navigate(R.id.action_AddTaskFragment_to_TasksFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
