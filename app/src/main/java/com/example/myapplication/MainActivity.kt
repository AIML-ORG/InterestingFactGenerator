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
import java.util.concurrent.TimeUnit

// Data classes for request and response
data class RequestData(
	val contents: List<Content>,
	val generationConfig: GenerationConfig? = null
)

data class Content(val role: String = "user", val parts: List<Part>)
data class Part(val text: String)
data class GenerationConfig(
	val temperature: Double = 2.0,
	val topK: Int = 40,
	val topP: Double = 0.95,
	val maxOutputTokens: Int = 8192,
	val responseMimeType: String = "text/plain"
)

data class ResponseData(val candidates: List<Candidate>)
data class Candidate(val content: Content, val finishReason: String)

private const val GOOGLE_API_KEY = "AIzaSyDQ-lB9oa920yqpJJCRVYiJbU_opbJ9Z0U"  // Replace with your actual API key

const val FLASH_8B = "gemini-1.5-flash-8b"
const val GEMINI_PRO = "gemini-1.5-pro"
const val GEMINI_EXP = "gemini-exp-1206"

interface GeminiApi {

	@POST("v1beta/models/$FLASH_8B:generateContent?key=$GOOGLE_API_KEY")
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
			.connectTimeout(10, TimeUnit.MINUTES)
			.readTimeout(10, TimeUnit.MINUTES)
			.callTimeout(10, TimeUnit.MINUTES)
			.writeTimeout(10, TimeUnit.MINUTES)
			.build()

		val retrofit = Retrofit.Builder()
			.baseUrl(geminiUrl)
			.addConverterFactory(GsonConverterFactory.create(gson))
			.client(okHttpClient)  // Add the OkHttpClient with the interceptor
			.build()

		val geminiApi = retrofit.create(GeminiApi::class.java)
		val initialPrompt =
			"[age, gender, location, occupation, relationship, kids, education, health info, wealth info]\n\ngenerate user persona of india based user keeping all above criteria in mind.\nThen create top 10 deatiled interests of user which are particular to his persona. It should not be generic. consider all info while generating a persona. DO not add any further detail. DO not add and prefix, suffix to answer Just give the info asked"
		val thanksPrompt = "Thanks"

		val requestData = RequestData(
			contents = listOf(
				Content(role = "user", parts = listOf(Part(text = initialPrompt))),
//				Content(role = "model", parts = listOf(Part(text = thanksPrompt))),
//				Content(role = "user", parts = listOf(Part(text = prompt)))
			),
			generationConfig = GenerationConfig()
		)

		return try {
			val response = geminiApi.generateContent(requestData)
			val generatedText =
				response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No content generated"

			generatedText

		} catch (e: Exception) {
			"Error: ${e.message}"
		}
	}
}
