package com.example.expensemanager.activities

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.example.expensemanager.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {

    private val TAG = "SignUpActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btn_create_account.setOnClickListener{createAccount()}
    }


private fun validateForm(name: String,email: String, password: String): Boolean {
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
        if (validateForm(
                et_sign_up_name.text.toString().trim(),
                et_sign_up_email.text.toString().trim(),
                et_sign_up_pwd.text.toString().trim()
            )
        ) {
            Log.e(TAG, "createAccount: " + "Form is valid")
        }
    }
}
