package ru.elenandreyuk.hw12.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ru.elenandreyuk.hw12.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

        private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        if(binding.editText.text?.length ?:0 < 3){
            binding.buttonSearch.isEnabled = false
        }

        viewModel.isSearching.observe(viewLifecycleOwner, { isSearching ->
            binding.progress.visibility = if (isSearching) View.VISIBLE else View.GONE
        })

        viewModel.searchResult.observe(viewLifecycleOwner, { result ->
            binding.textView.text = result
        })

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            // После изменения текста
            override fun afterTextChanged(s: Editable) {
                binding.buttonSearch.isEnabled = s.length >= 3 && !viewModel.isSearchInProgress
            }
        })

        binding.buttonSearch.setOnClickListener {
            val request = binding.editText.text.toString()
            viewModel.onButtonClick(request)
        }
        return binding.root


    }



}