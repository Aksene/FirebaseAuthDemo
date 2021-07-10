package com.abdou.firebaseauthdem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    // Create local variables to store components from activity_main.xml
    lateinit var string: String
    lateinit var flHeaderImage: FrameLayout
    lateinit var tvTitle: TextView
    lateinit var etRegisterEmail: EditText
    lateinit var tilRegisterEmail: TextInputLayout
    lateinit var tilRegisterPassword: TextInputLayout
    lateinit var etRegisterPassword: EditText
    lateinit var btnRegister: Button
    lateinit var tvAlreadyHaveAnAccount: TextView
    lateinit var tvLogin: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Set each component to a local variable
        flHeaderImage = findViewById(R.id.flHeaderImage)
        tvTitle = findViewById(R.id.tvTitle)
        etRegisterEmail = findViewById(R.id.etRegisterEmail)
        tilRegisterEmail = findViewById(R.id.tilRegisterEmail)
        tilRegisterPassword = findViewById(R.id.tilRegisterPassword)
        etRegisterPassword = findViewById(R.id.etRegisterPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvAlreadyHaveAnAccount = findViewById(R.id.tvAlreadyHaveAnAccount)
        tvLogin = findViewById(R.id.tvLogin)

        btnRegister.setOnClickListener {
            //Check if email and password fields are empty
            when {
                TextUtils.isEmpty(etRegisterEmail.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etRegisterPassword.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else ->{
                    // User has entered info! Store them in a variable
                    val email: String = etRegisterEmail.text.toString().trim{ it <= ' ' }
                    val password: String = etRegisterPassword.text.toString().trim{ it <= ' ' }

                    // Create an instance and register a user withe the email and password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                                // If the registration is successfully done
                                if(task.isSuccessful){

                                    //Firebase registered user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "You were registered successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    /**
                                     * Here the new user registered is automatically signed-in so we just sign-out the user....
                                     * and send him tothe Main screen with user id and enail that user used for registration ....
                                     */
                                    // Sends user back to the MainActivity
                                    val intent = Intent( this@RegisterActivity, MainActivity::class.java)

                                    // Clears values from stack
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                    // Sends over corresponding information
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    // If the registering is not successful then show error message.
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                        )
                }


            }
        }

        // Switch to login screen when "Login" button is pressed
        tvLogin.setOnClickListener{
            //startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            onBackPressed()
        }

    }
}