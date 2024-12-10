package com.example.myapplication

import retrofit2.http.Body
import retrofit2.http.POST

// Data classes for request and response (moved to a separate file - see below)

private const val GOOGLE_API_KEY = "AIzaSyDQ-lB9oa920yqpJJCRVYiJbU_opbJ9Z0U"  // Replace with your actual API key

const val FLASH_8B = "gemini-1.5-flash-8b"
const val GEMINI_PRO = "gemini-1.5-pro"
const val GEMINI_EXP = "gemini-exp-1206"

interface GeminiApi {
	@POST("v1beta/models/$FLASH_8B:generateContent?key=$GOOGLE_API_KEY")
	suspend fun generateContent(@Body request: RequestData): ResponseData
}
