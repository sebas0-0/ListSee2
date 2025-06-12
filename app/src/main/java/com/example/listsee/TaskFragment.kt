package com.example.listsee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.listsee.databinding.FragmentTaskBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        setupView()
        return binding.root

    }

    private fun setupView() {
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            binding.addTaskButton.setOnClickListener {
                val nombre = binding.taskName.text.toString()
                val descripcion = binding.taskDescription.text.toString()
                val fecha = binding.taskDate.text.toString()
                viewModel.addTask(nombre, descripcion, fecha)
            }

            // Observa cambios
            viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
                // aquí actualizas tu RecyclerView o UI con la lista de tareas
            }

            viewModel.loadTasks()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}