package br.com.fernandobrscunha.classcontrol

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_main.*
import java.time.YearMonth
import java.util.*

class MainActivity : AppCompatActivity() {

   private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        progressBar=  findViewById(R.id.progressBarMonth)

        progressBarAnimation()

    }

    private fun progressBarAnimation() {
        progressBar.max = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)

        var progressValue: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ObjectAnimator.ofInt(progressBar, "progress",progressValue).setDuration(500).start()
    }
}
