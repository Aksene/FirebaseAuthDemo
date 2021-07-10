package com.abdou.firebaseauthdem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    // Create local variables to store components from activity_main.xml
    lateinit var string: String
    lateinit var flHeaderImage: FrameLayout
    lateinit var tvTitle: TextView
    lateinit var etLoginEmail: EditText
    lateinit var tilLoginEmail: TextInputLayout
    lateinit var tilLoginPassword: TextInputLayout
    lateinit var etLoginPassword: EditText
    lateinit var btnLogin: Button
    lateinit var tvDontHaveAnAccount: TextView
    lateinit var tvRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Set each component to a local variable
        flHeaderImage = findViewById(R.id.flHeaderImage)
        tvTitle = findViewById(R.id.tvTitle)
        etLoginEmail = findViewById(R.id.etLoginEmail)
        tilLoginEmail = findViewById(R.id.tilLoginEmail)
        tilLoginPassword = findViewById(R.id.tilLoginPassword)
        etLoginPassword = findViewById(R.id.etLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvDontHaveAnAccount = findViewById(R.id.tvDontHaveAnAccount)
        tvRegister = findViewById(R.id.tvRegister)

        // Switch to register screen when "Register" button is pressed
        tvRegister.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            //Check if email and password fields are empty
            when {
                TextUtils.isEmpty(etLoginEmail.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etLoginPassword.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else ->{
                    // User has entered info! Store them in a variable
                    val email: String = etLoginEmail.text.toString().trim{ it <= ' ' }
                    val password: String = etLoginPassword.text.toString().trim{ it <= ' ' }

                    // Create an instance and register a user withe the email and password
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                                // If the registration is successfully done
                                if(task.isSuccessful){

                                    //Firebase registered user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@LoginActivity,
                                        "You were logged in successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    /**
                                     * Here the new user registered is automatically signed-in so we just sign-out the user....
                                     * and send him tothe Main screen with user id and enail that user used for registration ....
                                     */
                                    /**
                                     * Here the new user registered is automatically signed-in so we just sign-out the user....
                                     * and send him tothe Main screen with user id and enail that user used for registration ....
                                     */

                                    // Sends user back to the MainActivity
                                    val intent = Intent( this@LoginActivity, MainActivity::class.java)

                                    // Clears values from stack
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                    // Sends over corresponding information
                                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    // If the registering is not successful then show error message.
                                    Toast.makeText(
                                        this@LoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                        )
                }


            }
    }
}
}