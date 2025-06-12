package com.example.listsee.view.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.listsee.R
import com.example.listsee.databinding.FragmentTaskDetailBinding
import com.example.listsee.model.Task
import com.example.listsee.utils.FragmentCommunicator
import com.example.listsee.viewModel.list.TaskDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<TaskDetailViewModel>()

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
        setupObservers()

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_TaskDetailFragment_to_TasksFragment)
        }


    }

    private fun setupObservers() {
        viewModel.taskInfo.observe(viewLifecycleOwner) { task ->
            updateUI(task)
        }

        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }
    }

    private fun updateUI(task: Task) {
        binding.apply {
            taskNameTIET.setText(task.name)
            taskDescriptionTIET.setText(task.description)
            taskDateTIET.setText(task.date.toString())
        }
    }
}