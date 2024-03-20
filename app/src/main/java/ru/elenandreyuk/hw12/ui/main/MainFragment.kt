package ru.elenandreyuk.hw12.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.elenandreyuk.hw12.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private lateinit var  binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private  lateinit var searchResult: LiveData<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        savedInstanceState?.let{
            bundle ->  bundle.getString("KEY")
        }
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        lifecycleScope.launch {
            binding.invalidateAll()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("searchResult", viewModel.searchResult.value)
        outState.putString("request", viewModel.request.value)
        outState.putBoolean("isSearching", viewModel.isSearching.value)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        // Восстанавливаем состояние ViewModel
        viewModel._searchResult.value = savedInstanceState?.getString("searchResult") ?: ""
        viewModel._request.value = savedInstanceState?.getString("request") ?: ""
        viewModel._isSearching.value = savedInstanceState?.getBoolean("isSearching") ?: false
    }



}
