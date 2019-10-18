package br.com.fernandobrscunha.classcontrol.activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.fernandobrscunha.classcontrol.R
import br.com.fernandobrscunha.classcontrol.models.School
import br.com.fernandobrscunha.classcontrol.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_school_list.*

import com.google.firebase.database.DataSnapshot




class SchoolListActivity : AppCompatActivity() {

    lateinit var list:ListView

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var postKey: String
    private lateinit var schoolsReference: DatabaseReference

    val listitem: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_list)
        setSupportActionBar(toolbar)

        list = findViewById(R.id.listViewSchools)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference



        val uid = auth.currentUser!!.uid
        val user = User("Fernando", "ferstation@gmail.com")
        //database.child("users").child(uid).setValue(user)

        schoolsReference = FirebaseDatabase.getInstance().reference
            .child("users").child(uid).child("schools")



        listitem.add("Fernando")
        listitem.add("Luis")
        listitem.add("Maria")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listitem)
        list.adapter = adapter



        fab.setOnClickListener { view ->
           // val intent = Intent(this, SchoolActivity::class.java)
           // startActivity(intent)
            //Show Dialog here to add new Item
            addNewItemDialog()
        }

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(School::class.java)

                for (snapshot in dataSnapshot.children) {
                    val user = snapshot.getValue(School::class.java)
                    //aqui está repetindo a lista
                    //Toast.makeText(applicationContext,"oi"+user!!.name,Toast.LENGTH_LONG).show()
                    list.adapter = null
                    listitem.add(user!!.name.toString())
                    list.adapter = adapter
                }



            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("error", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        schoolsReference.addValueEventListener(postListener)


        listitem.add("Fernando")
        listitem.add("Luis")
        listitem.add("Maria")

    }

    private fun addNewItemDialog() {
        val alert = AlertDialog.Builder(this)
        val itemEditText = EditText(this)
        alert.setMessage("Add New Item")
        alert.setTitle("Enter To Do Item Text")
        alert.setView(itemEditText)
        alert.setPositiveButton("Submit") { dialog, positiveButton ->

            val uid = auth.currentUser!!.uid
            val shcool = School(uid,"nicolau","mu",12.50)

            database.child("users").child(uid).child("schools").push().setValue(shcool)
        }
        alert.show()
    }

}
