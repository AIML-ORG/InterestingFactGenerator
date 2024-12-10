package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.SCHEMA.KEYWORDS_SCHEMA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GeminiViewModel : ViewModel() {

	private val repository = GeminiRepository()
	fun getInterestingReads(onResult: (String) -> Unit) {
		viewModelScope.launch {
			val response = withContext(Dispatchers.IO) {
				// Generate User
				val generateUser = repository.queryGemini(PROMPTS.GENERATE_USER_PERSONA)

				//Generate History
				val generateHistoryPrompt = "${PROMPTS.GENERATE_HISTORY} $generateUser"
				val generatedHistory = repository.queryGemini(generateHistoryPrompt)

				//GenerateKeywords
				val generateKeywordsPrompt = "${PROMPTS.GENERATE_KEYWORDS} $generatedHistory"
				val generatedKeywords = repository.queryGemini(generateKeywordsPrompt, true, KEYWORDS_SCHEMA)

				"$generateUser\n\n\n$generatedHistory\n\n\n$generatedKeywords"
			}
			onResult(response)
		}
	}
}
