package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

	private val viewModel: GeminiViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val editText = findViewById<EditText>(R.id.editText)
		val button = findViewById<Button>(R.id.button)
		val textViewResult = findViewById<TextView>(R.id.textViewResult)

		button.setOnClickListener {
			val prompt = editText.text.toString()
			viewModel.getInterestingReads() {
				textViewResult.text = it
			}
		}
	}
}
