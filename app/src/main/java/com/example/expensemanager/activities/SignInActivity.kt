package com.example.expensemanager.activities

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.example.expensemanager.R
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {
    private val TAG = "SignInActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        btn_login.setOnClickListener{
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
        if (validateForm(
                et_sign_in_email.text.toString().trim(),
                et_sign_in_pwd.text.toString().trim()
            )
        ) {
            Log.e(TAG, "createAccount: " + "Form is valid")
        }
    }
}
