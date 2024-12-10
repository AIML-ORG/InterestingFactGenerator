package com.example.myapplication

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GeminiRepository {

	private val geminiUrl = "https://generativelanguage.googleapis.com/"

	private val geminiApi: GeminiApi by lazy {
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

		Retrofit.Builder()
			.baseUrl(geminiUrl)
			.addConverterFactory(GsonConverterFactory.create(gson))
			.client(okHttpClient)
			.build()
			.create(GeminiApi::class.java)
	}

	suspend fun queryGemini(prompt: String): String {

		val requestData = RequestData(
			contents = listOf(
				Content(role = "user", parts = listOf(Part(text = prompt))),
			),
			generationConfig = GenerationConfig()
		)

		return try {
			val response = geminiApi.generateContent(requestData)
			val generatedText = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
				?: "No content generated"
			generatedText
		} catch (e: Exception) {
			"Error: ${e.message}"
		}
	}
}
