package br.com.fernandobrscunha.classcontrol.activitys

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.fernandobrscunha.classcontrol.R
import br.com.fernandobrscunha.classcontrol.models.School
import br.com.fernandobrscunha.classcontrol.services.SchoolService
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.widget.*
import br.com.fernandobrscunha.classcontrol.models.Class
import br.com.fernandobrscunha.classcontrol.services.ClassService
import kotlinx.android.synthetic.main.activity_class.*
import java.util.*
import kotlin.collections.ArrayList
import android.widget.ArrayAdapter





class ClassActivity : AppCompatActivity() {

    private val schoolService = SchoolService()
    private val schoolsIds = ArrayList<String>()
    private var schoolsList = mutableListOf<School>()

    private lateinit var spinnerSchools: Spinner
    private lateinit var classValue: EditText
    private lateinit var classDate: Button
    private lateinit var paymentDate: Button
    private lateinit var comments: EditText

    private lateinit var buttonClassSave: Button

    private lateinit var classService: ClassService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)

        spinnerSchools = findViewById(R.id.spinnerSchools)

        classValue = findViewById(R.id.editTextClassValue)
        classValue .setText("15.00")


        classDate = findViewById(R.id.buttonClassDate)
        classDate.setText("24/10/2019")
        classDate.setOnClickListener { showDatePickerDialog() }

        paymentDate = findViewById(R.id.buttonPaymentDate)
        paymentDate.setText("NOV/2019")

        comments = findViewById(R.id.editTextComments)
        comments.setText("Aula de matematica, substitucao da prof Fulana")

        buttonClassSave = findViewById(R.id.buttonClassSave)
        buttonClassSave.setOnClickListener { saveClass() }


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

    private fun saveClass(){

        val classObject = Class(
            editTextClassValue.text.toString().toDouble(),
            5,
            Date(),
            Date(),
            editTextComments.text.toString()
        )
        showDatePickerDialog()
        //classService = ClassService(schoolsIds[spinnerSchools.selectedItemPosition])
        //classService.store(classObject)
    }

    private fun showDatePickerDialog(){
        val date = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
            buttonClassDate.setText("$dayOfMonth/$monthOfYear/$year")

            },
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH),
            date.get(Calendar.DAY_OF_MONTH))

        datePickerDialog.show()

        datePickerDialog.show()
    }
}


