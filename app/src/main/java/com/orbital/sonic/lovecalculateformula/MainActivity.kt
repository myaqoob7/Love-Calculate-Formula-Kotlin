package com.orbital.sonic.lovecalculateformula

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class MainActivity : AppCompatActivity() {

    val MAX_CHAR = 256
    var lovePercentage = 0
    var recursionList: ArrayList<Int>? = null

    private lateinit var tvLoveResult: TextView
    private lateinit var yourName: TextInputLayout
    private lateinit var loveName:TextInputLayout
    private lateinit var btnLoveCalculate:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvLoveResult = findViewById(R.id.tvLoveResult)
        yourName = findViewById(R.id.your_name_input_layout)
        loveName = findViewById(R.id.love_name_input_layout)
        btnLoveCalculate = findViewById(R.id.btnLoveCalculate)


        btnLoveCalculate.setOnClickListener {
            if (validateText()){
                val textYName: String =
                    yourName.editText?.text.toString()
                val textLName: String =
                    loveName.editText?.text.toString()

                loveCalculating(getOccurringChar("$textYName loves $textLName".lowercase(Locale.getDefault())))
                tvLoveResult.text = "$lovePercentage%"
            }
        }

    }

    private fun getOccurringChar(str: String): ArrayList<Int> {
        val arrayCounter = ArrayList<Int>()
        var n = 0
        val len = str.length
        val count = IntArray(MAX_CHAR)
        // Initialize count array index
        for (i in 0 until len) count[str[i].toInt()]++
        // Create an array of given String size
        val ch = CharArray(str.length)
        for (i in 0 until len) {
            ch[i] = str[i]
            var find = 0
            for (j in 0..i) {
                // If any matches found
                if (str[i] == ch[j]) find++
            }
            if (find == 1) {
                Log.i("NumberOfOccurrence", "" + str[i] + " is:" + count[str[i].toInt()])
                arrayCounter.add(count[str[i].toInt()])
                n++
            }
        }
        return arrayCounter
    }

    private fun loveCalculating(arrayList: ArrayList<Int>) {
        var a = 0
        var calculate: Int
        val percentageText: String
        val integers = ArrayList<Int>()
        var recursiveList: ArrayList<Int>
        for (i in 0 until arrayList.size / 2) {
            a += 1
            calculate = arrayList[i].toString().toInt() + arrayList[arrayList.size - a].toString().toInt()
            if (calculate < 10) {
                integers.add(calculate)
            } else {
                recursionList = ArrayList()
                recursiveList = separateNumber(calculate)
                for (j in recursiveList.indices) {
                    integers.add(recursiveList[j])
                }
            }
        }
        if (arrayList.size % 2 == 1) {
            integers.add(arrayList[arrayList.size / 2])
        }
        if (integers.size == 2) {
            if (integers[0] < 10 && integers[1] < 10) {
                percentageText = "" + integers[0] + integers[1]
              lovePercentage = percentageText.toInt()
            } else {
                loveCalculating(integers)
            }
        } else {
            loveCalculating(integers)
        }
    }

    private fun separateNumber(number: Int): ArrayList<Int> {
        if (number > 0) {
            separateNumber(number / 10)
            recursionList!!.add(number % 10)
            Log.i("recursionNumber", "Number: " + number % 10)
        }
        return recursionList!!
    }

    private fun validateText(): Boolean {
        val textYName: String =
            yourName.editText?.text.toString()
        val textLName: String =
            loveName.editText?.text.toString()


        if (textYName.isEmpty()) {
            yourName.error = "Field can't be empty"

        } else {
            yourName.error = null
        }

        if (textLName.isEmpty()) {
            loveName.error = "Field can't be empty"
        } else {
            loveName.error = null
        }


        return !(textYName.isEmpty() || textLName.isEmpty())
    }
}