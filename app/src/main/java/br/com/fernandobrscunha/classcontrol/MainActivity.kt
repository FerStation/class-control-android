package br.com.fernandobrscunha.classcontrol

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_main.*
import java.time.YearMonth
import java.util.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var textViewMonth: TextView
    private lateinit var textViewNextMonth1: TextView
    private lateinit var textViewNextMonth2: TextView
    private lateinit var textViewNextMonth3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //link xml
        progressBar =  findViewById(R.id.progressBarMonth)
        textViewMonth = findViewById(R.id.textViewMonth)
        textViewNextMonth1 = findViewById(R.id.textViewNextMonth1)
        textViewNextMonth2 = findViewById(R.id.textViewNextMonth2)
        textViewNextMonth3 = findViewById(R.id.textViewNextMonth3)

        //initial values
        textViewMonth.text = getMonthName()+"/"+Calendar.getInstance().get(Calendar.YEAR)
        textViewNextMonth1.text = addMonth(1).substring(0,3).toUpperCase()
        textViewNextMonth2.text = addMonth(2).substring(0,3).toUpperCase()
        textViewNextMonth3.text = addMonth(3).substring(0,3).toUpperCase()

        progressBarAnimation()

    }

    private fun progressBarAnimation() {
        progressBar.max = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)

        var progressValue: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ObjectAnimator.ofInt(progressBar, "progress",progressValue).setDuration(500).start()
    }

    private fun getMonthName(): String{
        return Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()).capitalize()
    }

    private fun addMonth(qtd:Int): String{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH,qtd)

        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
    }
}
