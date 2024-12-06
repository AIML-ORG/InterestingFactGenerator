package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


// Data classes for request and response
data class RequestData(val contents: List<Content>, val tools: List<Tool>)
data class Content(val parts: List<Part>)
data class Part(val text: String)
data class Tool(val google_search_retrieval: GoogleSearchRetrieval?)
data class GoogleSearchRetrieval(val dynamic_retrieval_config: DynamicRetrievalConfig)
data class DynamicRetrievalConfig(val mode: String, val dynamic_threshold: Int)

data class ResponseData(val candidates: List<Candidate>)
data class Candidate(val content: String, val finish_reason: String)

private const val GOOGLE_API_KEY = "AIzaSyDQ-lB9oa920yqpJJCRVYiJbU_opbJ9Z0U"  // Replace with your actual API key

interface GeminiApi {
	@POST("v1beta/models/gemini-1.5-flash-8b:generateContent?key=$GOOGLE_API_KEY")
	suspend fun generateContent(@Body request: RequestData): ResponseData
}

class MainActivity : AppCompatActivity() {

	private val geminiUrl = "https://generativelanguage.googleapis.com/"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val editText = findViewById<EditText>(R.id.editText)
		val button = findViewById<Button>(R.id.button)
		val textViewResult = findViewById<TextView>(R.id.textViewResult)

		button.setOnClickListener {
			val prompt = editText.text.toString()

			CoroutineScope(Dispatchers.IO).launch {
				val response = queryGemini(prompt)
				withContext(Dispatchers.Main) {
					textViewResult.text = response
				}
			}
		}
	}


	private suspend fun queryGemini(prompt: String): String {

		val gson = GsonBuilder().create()

		val loggingInterceptor = HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY // Log request and response body
		}

		val okHttpClient = OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.build()

		val retrofit = Retrofit.Builder()
			.baseUrl(geminiUrl)
			.addConverterFactory(GsonConverterFactory.create(gson))
			.client(okHttpClient)  // Add the OkHttpClient with the interceptor
			.build()

		val geminiApi = retrofit.create(GeminiApi::class.java)


		val requestData = RequestData(
			contents = listOf(Content(parts = listOf(Part(text = prompt)))), tools = listOf(
				Tool(
					google_search_retrieval = GoogleSearchRetrieval(
						DynamicRetrievalConfig(
							"MODE_DYNAMIC", 1
						)
					)
				)
			)
		)

		return try {
			val response = geminiApi.generateContent(requestData)


			// Process the response to extract the text content
			val generatedText = response.candidates.firstOrNull()?.content ?: "No content generated"


			generatedText

		} catch (e: Exception) {
			"Error: ${e.message}"
		}
	}
}
