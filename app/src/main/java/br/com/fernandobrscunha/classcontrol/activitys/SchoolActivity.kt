package br.com.fernandobrscunha.classcontrol.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.fernandobrscunha.classcontrol.R
import br.com.fernandobrscunha.classcontrol.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*




class SchoolActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school)

        //2
    //    auth = FirebaseAuth.getInstance()
    //    database = FirebaseDatabase.getInstance().reference


    //    val uid = auth.currentUser!!.uid

    //    val user = User("Fernando", "ferstation@gmail.com")
    //    database.child("users").child(uid).setValue(user)

    }
}
