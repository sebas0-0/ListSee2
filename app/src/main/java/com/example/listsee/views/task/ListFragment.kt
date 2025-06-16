package com.example.listsee.views.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listsee.R
import com.example.listsee.adapters.TaskAdapter
import com.example.listsee.databinding.FragmentListBinding
import com.example.listsee.model.Task
import com.example.listsee.utils.FragmentCommunicator
import com.example.listsee.viewModelList.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ListViewModel>()
    private lateinit var communicator: FragmentCommunicator
    private lateinit var tasksAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        communicator = requireActivity() as ListActivity
        setupView()
        return binding.root

    }

    private fun setupView() {
        setupObservers()

        tasksAdapter = TaskAdapter(
            mutableListOf()
        ) { taskId ->
            val action = ListFragmentDirections.actionTasksFragmentToTaskDetailFragment(taskId)
            findNavController().navigate(action)
        }

        binding.taskRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tasksAdapter
        }

        viewModel.fetchAllTasks()

        binding.addTaskButton.setOnClickListener {
            findNavController().navigate(R.id.action_TasksFragment_to_AddTaskFragment)
        }
    }

    fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }

        viewModel.taskInfo.observe(viewLifecycleOwner) { tasks ->
            updateUI(tasks)
        }
    }

    fun updateUI(tasks: List<Task>) {
        tasksAdapter.add(tasks)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}