package com.example.listsee.viewList


import AddTaskViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.listsee.R
import com.example.listsee.databinding.FragmentTaskBinding
import com.example.listsee.utils.FragmentCommunicator
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.UUID

@AndroidEntryPoint

class AddTaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<AddTaskViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        setupView()
        return binding.root

    }

    private fun setupView() {
        binding.btnAddTask.setOnClickListener {
            val id = UUID.randomUUID().toString()

            viewModel.createTaskInfo(
                id,
                binding.etNombre.text.toString(),
                binding.etDescripcion.text.toString(),
                Date()
            )
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
