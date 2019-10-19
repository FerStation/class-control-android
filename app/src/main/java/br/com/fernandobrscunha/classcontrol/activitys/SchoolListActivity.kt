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

    private val schoolsIds = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_list)
        setSupportActionBar(toolbar)

        list = findViewById(R.id.listViewSchools)

        var list2 = mutableListOf<School>()
        //list2.add(School("123","teste","Mun",0.0))
        val adapter4 = MyListAdpter(this, R.layout.school_item, list2)
        list.adapter = adapter4
        list.setOnItemClickListener { parent, view, position, id ->

            //Toast.makeText(this, "Clicked item :" + " " + position, Toast.LENGTH_SHORT).show()
            val uid = auth.currentUser!!.uid
            database.child("users").child(uid).child("schools").child(schoolsIds[position]).setValue(null)
        }

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference



        val uid = auth.currentUser!!.uid
        val user = User("Fernando", "ferstation@gmail.com")
        //database.child("users").child(uid).setValue(user)

        schoolsReference = FirebaseDatabase.getInstance().reference
            .child("users").child(uid).child("schools")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listitem)
        //list.adapter = adapter



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
                    //snapshot.ke

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
        //schoolsReference.addValueEventListener(postListener)

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {

                // A new comment has been added, add it to the displayed list
                val comment = dataSnapshot.getValue(School::class.java)

                schoolsIds.add(dataSnapshot.key!!)
                list2.add(comment!!)


                adapter4.notifyDataSetChanged()
                // ...
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {

                // ...
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val schoolKey = dataSnapshot.key
                val schoolIndex = schoolsIds.indexOf(schoolKey)
                if (schoolIndex > -1) {
                    // Remove data from the list
                    schoolsIds.removeAt(schoolIndex)
                    list2.removeAt(schoolIndex)

                    // Update the RecyclerView
                    adapter4.notifyDataSetChanged()
                }
                // ...
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {


                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("dd", "postComments:onCancelled", databaseError.toException())
                Toast.makeText(applicationContext, "Failed to load comments.",
                    Toast.LENGTH_SHORT).show()
            }
        }
        schoolsReference.addChildEventListener(childEventListener)

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

            //adicionar registros
            database.child("users").child(uid).child("schools").push().setValue(shcool)

            //excluir um ou vários registros
            //database.child("users").child(uid).child("schools").setValue(null)
        }
        alert.show()
    }

}
