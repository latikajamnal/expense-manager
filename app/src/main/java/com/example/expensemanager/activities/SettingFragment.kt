package com.example.expensemanager.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.expensemanager.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_setting_acitivity.*


class SettingFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_setting_acitivity, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ll_sign_out.setOnClickListener(this)
        ll_about_us.setOnClickListener(this)
        ll_contact_us.setOnClickListener(this)
        ll_edit_profile.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_sign_out -> {
                // Here sign outs the user from firebase in this device.
                FirebaseAuth.getInstance().signOut()
                // Send the user to the intro screen of the application.
                val intent = Intent(activity, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                activity?.finish() // Finishing the fragment activity
            }
            R.id.ll_about_us -> {
                Snackbar.make(this.requireActivity().findViewById(android.R.id.content), "About us", Snackbar.LENGTH_SHORT).show()
            }
            R.id.ll_contact_us -> {
                Snackbar.make(this.requireActivity().findViewById(android.R.id.content), "Contact us", Snackbar.LENGTH_SHORT).show()
            }
            R.id.ll_edit_profile -> {
                Snackbar.make(this.requireActivity().findViewById(android.R.id.content), "Edit me", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
