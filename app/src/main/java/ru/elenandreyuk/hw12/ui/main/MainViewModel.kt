package ru.elenandreyuk.hw12.ui.main

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    internal val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    internal val _searchResult = MutableStateFlow("")
    val searchResult: StateFlow<String> = _searchResult

    internal val _request = MutableStateFlow("")
    val request: StateFlow<String> = _request

    private var searchJob: Job? = null

    fun onTextChanged(view: View, string: String) {
        if (string.length > 3) {
            searchJob?.cancel()

            searchJob = viewModelScope.launch {
                _isSearching.value = true
                _request.value = string
                delay(300)
                Log.d("edit", string)
                delay(3000)

                _isSearching.value = false
                _searchResult.value = "По запросу $string ничего не найдено"
            }
        }else{
            searchJob?.cancel()
            _request.value = ""
            Log.d("stop", string)
            _isSearching.value = false
            _searchResult.value= ""
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("clear", "clear")
        searchJob?.cancel()
    }
}


