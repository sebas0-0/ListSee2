package com.example.listsee.views.task

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.listsee.databinding.ActivityListBinding
import com.example.listsee.utils.FragmentCommunicator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListActivity : AppCompatActivity(), FragmentCommunicator {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun showLoader(value: Boolean) {
        binding.loaderContainerView.visibility = if (value) View.VISIBLE else View.GONE
    }
}