package br.com.fernandobrscunha.classcontrol.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import br.com.fernandobrscunha.classcontrol.R
import br.com.fernandobrscunha.classcontrol.models.School
import br.com.fernandobrscunha.classcontrol.services.SchoolService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import android.widget.ArrayAdapter




class SchoolActivity : AppCompatActivity() {

    // UI elements
    private lateinit var editTextSchoolName: EditText
    private lateinit var editTextBaseValue: EditText
    private lateinit var spinnerSchoolType: Spinner

    private val schoolService = SchoolService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school)

        // get school Id
        val schoolId: String? = intent.getStringExtra("SCHOOL_ID")


        editTextSchoolName = findViewById(R.id.editTextSchoolName)
        editTextBaseValue = findViewById(R.id.editTextBaseValue)
        spinnerSchoolType = findViewById(R.id.spinnerSchoolType)

        val buttonSchoolSave: Button = findViewById(R.id.buttonSchoolSave)
        val buttonSchoolDelete: Button = findViewById(R.id.buttonSchoolDelete)


        //Event list
        buttonSchoolSave.setOnClickListener {
            if(schoolId != null){
                updateSchool(schoolId)
            } else{
                saveSchool()
            }
        }

        buttonSchoolDelete.setOnClickListener {
            if(schoolId != null){
                removeSchool(schoolId)
            }
        }

        //hide button
        if(schoolId == null){
            buttonSchoolDelete.visibility = View.GONE
        }

        editTextSchoolName.setOnFocusChangeListener { view, hasFocus ->  editTextFocusChangeListener(view, hasFocus)}
        editTextBaseValue.setOnFocusChangeListener { view, hasFocus ->  editTextFocusChangeListener(view, hasFocus)}

        //set editText
        if(schoolId != null){
            showSchool(schoolId)
        }
    }

    private fun showSchool(schoolId: String){
        schoolService.show(schoolId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val school = dataSnapshot.getValue(School::class.java)
                if(school != null) {
                    editTextSchoolName.setText(school.name)
                    editTextBaseValue.setText(school.baseValue.toString())
                    spinnerSchoolType.setSelection(
                        (spinnerSchoolType.getAdapter() as ArrayAdapter<String>).getPosition(
                            school.type
                        )
                    )
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("err","err")
            }
        })
    }

    private fun saveSchool() {

        if(validateFields()){
            schoolService.store(schoolObject())
            Toast.makeText(applicationContext,getString(R.string.message_school_add_success),Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun updateSchool(schoolId: String) {

        if(validateFields()){
            schoolService.update(schoolId,schoolObject())
            Toast.makeText(applicationContext,getString(R.string.message_school_update_success),Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun removeSchool(schoolId: String) {

        schoolService.delete(schoolId)
        Toast.makeText(applicationContext,getString(R.string.message_school_delete_success),Toast.LENGTH_SHORT).show()
        finish()
    }


    private fun schoolObject(): School{

        return School(
            editTextSchoolName.text.toString(),
            spinnerSchoolType.selectedItem.toString(),
            editTextBaseValue.text.toString().toDouble()
        )
    }

    private fun validateFields(): Boolean{

        if(editTextSchoolName.text.isEmpty() || editTextSchoolName.text.isBlank()){
            addErrorEditText(editTextSchoolName)
            editTextSchoolName.requestFocus()
            return false
        }

        if(editTextBaseValue.text.isEmpty() || editTextBaseValue.text.isBlank()){
            editTextBaseValue.requestFocus()
            return false
        }

        return true
    }

    private fun addErrorEditText(editText: EditText){
        if (editText.text.isBlank() || editText.text.isEmpty()) {
            editText.error ="Esse campo é obrigatório"
        }
    }

    private fun editTextFocusChangeListener(view: View, hasFocus:Boolean) {
        val editText: EditText = findViewById(view.id)
        if (!hasFocus) {
            addErrorEditText(editText)
        }
    }

}//end class



//2
//    auth = FirebaseAuth.getInstance()
//    database = FirebaseDatabase.getInstance().reference


//    val uid = auth.currentUser!!.uid

//    val user = User("Fernando", "ferstation@gmail.com")
//    database.child("users").child(uid).setValue(user)
