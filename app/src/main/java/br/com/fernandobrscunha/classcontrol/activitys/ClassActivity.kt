package br.com.fernandobrscunha.classcontrol.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Spinner
import android.widget.Toast
import br.com.fernandobrscunha.classcontrol.R
import br.com.fernandobrscunha.classcontrol.models.School
import br.com.fernandobrscunha.classcontrol.services.SchoolService
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.widget.ArrayAdapter
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.widget.AdapterView


class ClassActivity : AppCompatActivity() {

    private lateinit var spinnerSchools: Spinner

    private val schoolService = SchoolService()
    private val schoolsIds = ArrayList<String>()
    private var schoolsList = mutableListOf<School>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)

        spinnerSchools = findViewById(R.id.spinnerSchools)

        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, schoolsList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSchools.adapter = adapter

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {

                // A new school has been added, add it to the displayed list
                val school = dataSnapshot.getValue(School::class.java)

                schoolsIds.add(dataSnapshot.key!!)
                schoolsList.add(school!!)

                adapter.notifyDataSetChanged()

            }//end onChildAdded

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {

                val newSchool = dataSnapshot.getValue(School::class.java)
                val schoolKey = dataSnapshot.key
                val schoolIndex = schoolsIds.indexOf(schoolKey)

                schoolsList[schoolIndex] = newSchool!!

            }//end onChildChanged

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

                val schoolKey = dataSnapshot.key
                val schoolIndex = schoolsIds.indexOf(schoolKey)

                if (schoolIndex > -1) {
                    // Remove school from list
                    schoolsIds.removeAt(schoolIndex)
                    schoolsList.removeAt(schoolIndex)
                }

            }//end onChildRemoved

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("dd", "postComments:onCancelled", databaseError.toException())
                Toast.makeText(
                    applicationContext, "Failed to load schools.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        schoolService.showAll().addChildEventListener(childEventListener)
    }
}


