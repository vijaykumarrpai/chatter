package com.example.chatter.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.chatter.R
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

import android.view.MotionEvent as MotionEvent


class LoginActivity : AppCompatActivity() {

    private val firebaseAuth=FirebaseAuth.getInstance()
    private val firebaseAuthListener=FirebaseAuth.AuthStateListener {
        val user=firebaseAuth.currentUser?.uid
        user?.let {
            startActivity(HomeActivity.newIntent(this))
            finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setTextChangeListener(emailET, emailTIL)
        setTextChangeListener(passwordET, passwordTIL)
        loginProgressLayout.setOnTouchListener {v: View ,event: MotionEvent -> true }

    }
     fun setTextChangeListener(et: EditText, til: TextInputLayout) {
        et.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                til.isErrorEnabled=false
            }

        })
    }
    fun onLogin(v: View) {
        var proceed=true
        if(emailET.text.isNullOrEmpty()) {
            emailTIL.error="Email is required"
            emailTIL.isErrorEnabled=true
            proceed=false
        }
        if(passwordET.text.isNullOrEmpty()) {
            passwordTIL.error="password is required"
            passwordTIL.isErrorEnabled=true
            proceed=false
        }
        if(proceed) {
            loginProgressLayout.visibility=View.VISIBLE
            firebaseAuth.signInWithEmailAndPassword(emailET.text.toString(),passwordET.text.toString())
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if(!task.isSuccessful) {
                        loginProgressLayout.visibility=View.GONE
                        Toast.makeText(this@LoginActivity,"Login error: ${task.exception?.localizedMessage}",Toast.LENGTH_SHORT).show()
                    }

                }
                .addOnFailureListener {e:Exception ->
                    e.printStackTrace()
                    loginProgressLayout.visibility=View.GONE
                }
        }
    }
    fun goToSignup(v: View) {
        startActivity(SignupActivity.newIntent(this))
        finish()
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }
    companion object {
        fun newIntent(context: Context)= Intent(context,
            LoginActivity::class.java )
    }

}

