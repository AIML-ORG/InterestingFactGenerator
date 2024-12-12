package com.example.myapplication

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

// Data classes for request and response (moved to a separate file - see below)

private const val GOOGLE_API_KEY = "AIzaSyDQ-lB9oa920yqpJJCRVYiJbU_opbJ9Z0U"  // Replace with your actual API key

const val FLASH_8B = "gemini-1.5-flash-8b"
const val FLASH = "gemini-1.5-flash"
const val FLASH_EXP = "gemini-2.0-flash-exp"
const val PRO = "gemini-1.5-pro"
const val PRO_EXP = "gemini-exp-1206"

interface GeminiApi {
	@POST("v1beta/models/{model}:generateContent?key=$GOOGLE_API_KEY")
	suspend fun generateContent(
		@Body request: RequestData,
		@Path("model") model: String = FLASH_EXP
	): ResponseData
}
