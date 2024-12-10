package com.example.myapplication

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
