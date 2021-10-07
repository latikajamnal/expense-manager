package com.example.expensemanager.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.expensemanager.R
import com.example.expensemanager.firebase.FireStore
import com.example.expensemanager.models.Users
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {

    private val TAG = "SignUpActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btn_create_account.setOnClickListener { createAccount() }
    }


    private fun validateForm(name: String, email: String, password: String): Boolean {
        if (TextUtils.isEmpty(name)) {
            showErrorSnackBar("Please enter the name")
            return false
        }
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

    private fun createAccount() {
        val name: String = et_sign_up_name.text.toString().trim { it <= ' ' }
        val email: String = et_sign_up_email.text.toString().trim { it <= ' ' }
        val password: String = et_sign_up_pwd.text.toString().trim { it <= ' ' }
        if (validateForm(name, email, password)
        ) {
            Log.e(TAG, "createAccount: " + "Form is valid")
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        Log.e(TAG, "createAccount: Account is created with email $email")
                        val user = Users(
                            firebaseUser.uid, name, email
                        )
                        FireStore().registerUser(this, user)
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        hideProgressDialog()
                        Toast.makeText(this, "Failed to create account", Toast.LENGTH_LONG)
                            .show()
                    }
                }
        }
    }


    /**
     * A function to be called the user is registered successfully and entry is made in the firestore database.
     */
    fun userRegisteredSuccess() {

        Toast.makeText(
            this@SignUpActivity,
            "You have successfully registered.",
            Toast.LENGTH_SHORT
        ).show()

        // Hide the progress dialog
        hideProgressDialog()
    }
}
