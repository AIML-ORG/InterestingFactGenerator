package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GeminiViewModel : ViewModel() {
	private val TAG = "GeminiViewModel"
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
				val generatedKeywords = repository.queryGemini(generateKeywordsPrompt, true)
				val topTopics = Gson().fromJson(generatedKeywords, TopTopics::class.java)

				"$generateUser\n\n\n$generatedHistory\n\n\n$generatedKeywords"

				val facts = ArrayList<Fact>()
				topTopics.very_detailed_top_5_topics?.forEach {
					Log.d(TAG, "getInterestingReads: $it")
					//GenerateKeywords
					val generateInterestingFactPrompt = "${PROMPTS.GENERATE_INTERESTING_FACT} $it"
					var generatedFact = repository.queryGemini(generateInterestingFactPrompt/* model = GEMINI_EXP*/)
					generatedFact = generatedFact.removePrefix("Did you know? ")
					facts.add(
						Fact(
							generatedFact,
							"https://www.google.com/?search=${java.net.URLEncoder.encode(it, "UTF-8")}"
						)
					)
				}

				"$generateUser\n\n\n$generatedHistory\n\n\n$generatedKeywords\n\n\n${facts.joinToString("\n")}"

			}
			onResult(response)
		}
	}
}

data class Fact(
	val fact: String, val url: String
)