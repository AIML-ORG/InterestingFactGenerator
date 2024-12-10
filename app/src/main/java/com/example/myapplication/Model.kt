package com.example.myapplication

data class RequestData(
	val contents: List<Content>,
	val generationConfig: GenerationConfig? = null,
	val safetySettings: List<SafetySetting>? = null,
	val tools: List<Tool>? = null
)

data class Content(val role: String = "user", val parts: List<Part>)
data class Part(val text: String)

data class SafetySetting(
	val category: String, val threshold: String
)

data class Tool(
	val functionDeclarations: List<FunctionDeclaration>
)

data class FunctionDeclaration(
	val name: String, val description: String, val parameters: ParameterSchema
)

data class ParameterSchema(
	val type: String,
	val format: String?,
	val description: String?,
	val enum: List<String>?,
	val properties: Map<String, ParameterSchema>?,
	val required: List<String>?
)

data class GenerationConfig(
	val temperature: Double = 2.0,
	val topK: Int = 40,
	val topP: Double = 0.95,
	val maxOutputTokens: Int = 8192,
	val responseMimeType: String = "text/plain",
	val responseSchema: ResponseSchema? = null
)

data class ResponseSchema(
	val type: String = "object",
	val properties: Properties = Properties(),
	val required: List<String> = arrayListOf("very_detailed_top_5_topics")
)

data class Properties(
	val very_detailed_top_5_topics: VeryDetailedTop5Topics = VeryDetailedTop5Topics()
)

data class VeryDetailedTop5Topics(
	val type: String = "array", val items: Items = Items()
)

data class Items(
	val type: String = "string"
)

data class ResponseData(val candidates: List<Candidate>)
data class Candidate(val content: Content, val finishReason: String, val safetyRatings: List<SafetyRating>)
data class SafetyRating(val category: String, val probability: String)

data class TopTopics(
	var very_detailed_top_5_topics: List<String>? = null
)