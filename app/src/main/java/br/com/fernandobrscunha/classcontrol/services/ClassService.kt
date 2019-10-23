package br.com.fernandobrscunha.classcontrol.services

import br.com.fernandobrscunha.classcontrol.models.Class
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ClassService(shcoolId: String) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: DatabaseReference

    private val uid: String?

    init {
        uid = auth.currentUser!!.uid
        database = FirebaseDatabase.getInstance().reference.child("users/$uid/schools/$shcoolId")
    }

    fun store(classObject: Class){
        database.push().setValue(classObject)
    }
}