package com.example.nutri

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.*


class Diet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val res = findViewById<TextView>(R.id.res)
        val generate = findViewById<Button>(R.id.generate)
        suspend fun generateMagicBackpackStory(cals : Int = 2000)= withContext(Dispatchers.IO) {
            val generativeModel = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = "AIzaSyCiQuLyBrfuVUHEzAOOG5l0OHeA09UkpgA"
            )

            val prompt = "You are a dietician. You are given the maintenance calories of a person. You will make a diet(list the dishes which are to be consumed) in the format\n" +
                    "\n" +
                    "In one line  list the items for \n" +
                    "Breakfast\n" +
                    "Lunch\n" +
                    "Dinner\n" +
                    "\n" +
                    "Finally List the macros of all the food consumed (Do not list macros individually)\n" +
                    "Dont say one liners say Diet plan instead\n" +
                    "The maintenance calories are $cals "

            try {
                val response = generativeModel.generateContent(prompt)
                return@withContext response.text
            } catch (e: Exception) {
                println("Error generating story: ${e.message}")
                return@withContext "An error occurred while generating the story."
            }
        }

        generate.setOnClickListener{
            lifecycleScope.launch{
                res.text = "Generating"
                val cals  = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.num).text.toString().toInt()
                val story = generateMagicBackpackStory(cals)
                res.text = story
            }
        }
    }
}