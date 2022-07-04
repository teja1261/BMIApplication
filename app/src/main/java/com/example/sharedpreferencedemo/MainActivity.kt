package com.example.sharedpreferencedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var resultIndex: TextView
    private lateinit var resultDescription: TextView
    private lateinit var info: TextView
    private lateinit var weightText: EditText
    private lateinit var heightText: EditText
    private lateinit var calcButton: Button
    private lateinit var cardResultView: CardView
    private lateinit var repeatBmi: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weightText = findViewById(R.id.et_weight)
        heightText = findViewById(R.id.et_height)
        calcButton = findViewById(R.id.bt_calculate)
        cardResultView = findViewById(R.id.cd_resultView)
        repeatBmi = findViewById(R.id.fabReplay)


        cardResultView.visibility = INVISIBLE
        repeatBmi.visibility = INVISIBLE

        calcButton.setOnClickListener {
            val weight = weightText.text.toString()
            val height = heightText.text.toString()

            if (validateInput(weight, height)) {
                val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                // get result with two decimal places
                val bmi2Digits = String.format("%.2f", bmi).toFloat()
                cardResultView.visibility = VISIBLE
                repeatBmi.visibility = VISIBLE
                displayResult(bmi2Digits)
            }

            repeatBmi.setOnClickListener {
                weightText.text.clear()
                heightText.text.clear()
                cardResultView.visibility = INVISIBLE
                repeatBmi.visibility = INVISIBLE
            }
        }
    }

    private fun validateInput(weight: String?, height: String?): Boolean {

        return when {
            weight.isNullOrEmpty() -> {
                Toast.makeText(this, "Weight is empty", Toast.LENGTH_LONG).show()
                false
            }
            height.isNullOrEmpty() -> {
                Toast.makeText(this, "Height is empty", Toast.LENGTH_LONG).show()
                false
            }
            else -> {
                true
            }

        }
    }

    private fun displayResult(bmi: Float) {
        resultIndex = findViewById(R.id.tvIndex)
        resultDescription = findViewById(R.id.tvResult)
        info = findViewById(R.id.tvInfo)

        resultIndex.text = bmi.toString()
        info.text = "(Normal range is 18.5 - 24.9 )"

        var resultText = ""
        var color = 0

        when {
            bmi < 18.50 -> {
                resultText = "Underweight"
                color = R.color.under_weight
            }
            bmi in 18.50..24.99 -> {
                resultText = "Healthy"
                color = R.color.normal
            }
            bmi in 25.00..29.99 -> {
                resultText = "Overweight"
                color = R.color.over_weight
            }
            bmi > 29.99 -> {
                resultText = "Obese"
                color = R.color.obese
            }

        }
        resultDescription.setTextColor(ContextCompat.getColor(this, color))
        resultDescription.text = resultText
    }
}