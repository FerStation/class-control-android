package br.com.fernandobrscunha.classcontrol.services

import br.com.fernandobrscunha.classcontrol.models.School
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SchoolService {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: DatabaseReference

    private val uid: String?

    init {
        uid = auth.currentUser!!.uid
        database = FirebaseDatabase.getInstance().reference.child("users/$uid/schools")
    }

    fun store(school: School){
        if(uid !=null){
            database.push().setValue(school)
        }
    }//end store

    fun showAll(): DatabaseReference {
        return database
    }//end showAll

    fun show(schoolId: String): DatabaseReference {
        return database.child(schoolId)
    }//end show

    fun update(schoolId: String, school: School){
        if(uid !=null){
            database.child(schoolId).setValue(school)
        }
    }//end update

    fun delete(schoolId: String){
        if(uid !=null){
            database.child(schoolId).removeValue()
        }
    }//end update

}//end class