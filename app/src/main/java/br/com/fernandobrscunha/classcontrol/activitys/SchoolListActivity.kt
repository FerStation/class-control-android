package br.com.fernandobrscunha.classcontrol.activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.fernandobrscunha.classcontrol.MyListAdpter
import br.com.fernandobrscunha.classcontrol.R
import br.com.fernandobrscunha.classcontrol.models.School
import br.com.fernandobrscunha.classcontrol.models.User
import br.com.fernandobrscunha.classcontrol.services.SchoolService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_school_list.*

import com.google.firebase.database.DataSnapshot

class SchoolListActivity : AppCompatActivity() {

    private val schoolService = SchoolService()
    private val schoolsIds = ArrayList<String>()
    private var schoolsList = mutableListOf<School>()

    private lateinit var listViewSchools: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_list)
        setSupportActionBar(toolbar)

        listViewSchools = findViewById(R.id.listViewSchools)


        //Set adapter
        val adapterSchools = MyListAdpter(this, R.layout.school_item, schoolsList)
        listViewSchools.adapter = adapterSchools


        // Event List
        fab.setOnClickListener { view ->
           val intent = Intent(this, SchoolActivity::class.java)
            startActivity(intent)
        }

        listViewSchools.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, SchoolActivity::class.java)
            intent.putExtra("SCHOOL_ID",schoolsIds[position])
            startActivity(intent)

        }


        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {

                // A new school has been added, add it to the displayed list
                val school = dataSnapshot.getValue(School::class.java)

                schoolsIds.add(dataSnapshot.key!!)
                schoolsList.add(school!!)


                adapterSchools.notifyDataSetChanged()

            }//end onChildAdded

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {

                val newSchool = dataSnapshot.getValue(School::class.java)
                val schoolKey = dataSnapshot.key
                val schoolIndex = schoolsIds.indexOf(schoolKey)

                schoolsList[schoolIndex] = newSchool!!

                // Update the RecyclerView
                adapterSchools.notifyDataSetChanged()

            }//end onChildChanged

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

                val schoolKey = dataSnapshot.key
                val schoolIndex = schoolsIds.indexOf(schoolKey)

                if (schoolIndex > -1) {

                    // Remove school from list
                    schoolsIds.removeAt(schoolIndex)
                    schoolsList.removeAt(schoolIndex)

                    // Update the RecyclerView
                    adapterSchools.notifyDataSetChanged()
                }

            }//end onChildRemoved

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("dd", "postComments:onCancelled", databaseError.toException())
                Toast.makeText(applicationContext, "Failed to load schools.",
                    Toast.LENGTH_SHORT).show()
            }
        }
        schoolService.showAll().addChildEventListener(childEventListener)

    }

}
