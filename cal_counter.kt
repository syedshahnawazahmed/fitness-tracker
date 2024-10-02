package com.example.fitness_tracker

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nutri.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.roundToInt


class cal_counter : AppCompatActivity() {

    fun calculateCalories(weight: Double, height: Double, age: Int, gender: String): Double {
        return if (gender.lowercase() == "male") {
            (10 * weight + 6.25 * height - 5 * age + 5) * 1.4
        } else {
           ( 10 * weight + 6.25 * height - 5 * age - 16) * 1.3
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cal_counter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<FloatingActionButton>(R.id.cal_back)
        backButton.setOnClickListener {
            finish()
        }

        val calc = findViewById<Button>(R.id.calc)

        calc.setOnClickListener{
            val genderRadio = findViewById<RadioGroup>(R.id.rg)
            val genderID = genderRadio.checkedRadioButtonId
            val bmrtext = findViewById<TextView>(R.id.bmr_text)
            if(genderID == -1) {
                bmrtext.setText("Invalid Choices")
                return@setOnClickListener
            }
            val gender = findViewById<Button>(genderID).text.toString().lowercase()
            val weight = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.weight).text.toString().toDouble()
            val height = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.height).text.toString().toDouble()
            val age = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.age).text.toString().toInt()
            var res = calculateCalories(weight, height , age, gender)
            if(res <= 1000){
                res=1000.0
            }
            res = res.roundToInt().toDouble()
            bmrtext.setText("Your maintenance calories are $res")
        }

    }
}