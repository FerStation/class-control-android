package br.com.fernandobrscunha.classcontrol.activitys

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import java.util.*
import android.widget.Button
import android.widget.TextView
import br.com.fernandobrscunha.classcontrol.R
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    private lateinit var textViewMonth: TextView
    private lateinit var textViewNextMonth1: TextView
    private lateinit var textViewNextMonth2: TextView
    private lateinit var textViewNextMonth3: TextView

    private lateinit var buttonManageClassRoom: Button
    private lateinit var buttonViewClasses: Button
    private lateinit var buttonAddClasses: Button

    //1
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //2
        auth = FirebaseAuth.getInstance()


        //link xml
        progressBar =  findViewById(R.id.progressBarMonth)

        textViewMonth = findViewById(R.id.textViewMonth)
        textViewNextMonth1 = findViewById(R.id.textViewNextMonth1)
        textViewNextMonth2 = findViewById(R.id.textViewNextMonth2)
        textViewNextMonth3 = findViewById(R.id.textViewNextMonth3)

        buttonManageClassRoom = findViewById(R.id.buttonManageClassRoom)
        buttonViewClasses = findViewById(R.id.buttonViewClasses)
        buttonAddClasses = findViewById(R.id.buttonAddClasses)

        //initial values
        textViewMonth.text = getMonthName()+"/"+Calendar.getInstance().get(Calendar.YEAR)
        textViewNextMonth1.text = addMonth(1).substring(0,3).toUpperCase()
        textViewNextMonth2.text = addMonth(2).substring(0,3).toUpperCase()
        textViewNextMonth3.text = addMonth(3).substring(0,3).toUpperCase()

        //animations
        progressBarAnimation()

        //events
        buttonManageClassRoom.setOnClickListener{
            val intent = Intent(this, SchoolListActivity::class.java)
            startActivity(intent)
        }

        buttonViewClasses.setOnClickListener{
            //val intent = Intent(this, ClassListActivity::class.java)
            //startActivity(intent)
        }

        buttonAddClasses.setOnClickListener {
            //val intent = Intent(this, ClassActivity::class.java)
            //startActivity(intent)
        }

    }

    //3
    override fun onStart() {
        super.onStart()
        //FirebaseAuth.getInstance().signOut()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        Log.d("logou",""+auth.currentUser)

        if(currentUser === null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
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
