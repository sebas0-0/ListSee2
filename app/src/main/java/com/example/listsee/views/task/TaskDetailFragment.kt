package com.example.listsee.views.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.app.DatePickerDialog
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.listsee.R
import com.example.listsee.databinding.FragmentTaskDetailBinding
import com.example.listsee.model.Task
import com.example.listsee.utils.FragmentCommunicator
import com.example.listsee.viewModelList.TaskDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<TaskDetailViewModel>()
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        communicator = requireActivity() as ListActivity
        setupView()
        return binding.root
    }

    private fun setupView() {
        val taskId = arguments?.let {
            TaskDetailFragmentArgs.fromBundle(it).taskId
        }
        viewModel.getTaskInfo(taskId?: "")
        setupObservers()

        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_TaskDetailFragment_to_TasksFragment)
        }
        binding.taskD.apply {
            isFocusable = false
            isClickable = true
        }
        binding.saveBtn.setOnClickListener {
            val dateString = binding.taskD.text.toString()
            val parsedDate: Date = try {
                format.parse(dateString) ?: Date()
            } catch (e: ParseException) {
                Log.e("TaskDetailFragment", "Error al parsear la fecha: ${e.message}")
                Date()
            }
            viewModel.updateTask(
                taskId?: "",
                binding.taskN.text.toString(),
                binding.taskDes.text.toString(),
                parsedDate)
        }

        binding.taskD.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                // Ajusta el mes (+1 porque empieza en 0)
                val fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                binding.taskD.setText(fechaSeleccionada)
            }, year, month, day)

            datePicker.show()
        }

        binding.deleteBtn.setOnClickListener {
            viewModel.deleteTask(taskId?: "")

        }

    }

    private fun setupObservers() {
        viewModel.taskInfo.observe(viewLifecycleOwner) { task ->
            updateUI(task)
        }

        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }

        viewModel.operationSuccess.observe(viewLifecycleOwner) { operationSuccess ->
            if (operationSuccess) {
                findNavController().navigate(R.id.action_TaskDetailFragment_to_TasksFragment)
            }
        }
    }

    private fun updateUI(task: Task) {
        binding.apply {
            taskN.setText(task.name)
            taskDes.setText(task.description)
            taskD.setText(format.format(task.date))
        }
    }
}