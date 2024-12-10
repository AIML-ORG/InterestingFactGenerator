package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GeminiViewModel : ViewModel() {

	private val repository = GeminiRepository()

	fun queryGemini(prompt: String, onResult: (String) -> Unit) {
		viewModelScope.launch {
			val response = withContext(Dispatchers.IO) {
				repository.queryGemini(prompt)
			}
			onResult(response)
		}
	}
}
