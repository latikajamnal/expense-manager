package com.example.expensemanager.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.expensemanager.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {
    private val TAG = "SignInActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        btn_login.setOnClickListener {
            login()
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            showErrorSnackBar("Please enter the email")
            return false
        }
        if (TextUtils.isEmpty(password)) {
            showErrorSnackBar("Please enter the password")
            return false
        }
        return true
    }

    private fun login() {
        val email: String = et_sign_in_email.text.toString().trim { it <= ' ' }
        val password: String = et_sign_in_pwd.text.toString().trim { it <= ' ' }
        if (validateForm(email, password)) {
            Log.e(TAG, "login: " + "Form is valid")
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        hideProgressDialog()
                        Toast.makeText(
                            this@SignInActivity,
                            "Login successful with email $email",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e(TAG, "login: User is logged in with email $email")
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        hideProgressDialog()
                        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_LONG)
                            .show()
                    }
                }
        }
    }

}
