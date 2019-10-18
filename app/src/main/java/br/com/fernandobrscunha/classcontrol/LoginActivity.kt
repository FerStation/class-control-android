package br.com.fernandobrscunha.classcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: TextView

    private lateinit var buttonSingUp: Button
    private lateinit var buttonSingIn: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)

        buttonSingIn = findViewById(R.id.buttonSingIn)
        buttonSingUp = findViewById(R.id.buttonSingUp)

        auth = FirebaseAuth.getInstance()

        buttonSingIn.setOnClickListener{ singIn()}
        buttonSingUp.setOnClickListener{ singUp()}

    }

    private fun singUp() {
        auth.createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString())
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("login", "createUserWithEmail:success")
                    val user = auth.currentUser

                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("login", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun singIn(){
        auth.signInWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("login", "signInWithEmail:success")
                    val user = auth.currentUser

                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    //editTextEmail.error = task.exception.toString()
                    Log.w("login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }

            }
    }
}
