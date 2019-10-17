package br.com.fernandobrscunha.classcontrol.activitys

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import br.com.fernandobrscunha.classcontrol.R
import br.com.fernandobrscunha.classcontrol.SchoolActivity

import kotlinx.android.synthetic.main.activity_school_list.*

class SchoolListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val intent = Intent(this, SchoolActivity::class.java)
            startActivity(intent)
        }
    }

}
