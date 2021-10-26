package com.example.expensemanager.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.expensemanager.R
import com.example.expensemanager.firebase.FireStore
import com.example.expensemanager.models.ExpenseList
import com.example.expensemanager.models.Expenses
import com.example.expensemanager.models.Users
import kotlinx.android.synthetic.main.home.*
import kotlin.math.log


class HomeFragment : Fragment() {

    // A global variable for user details.
    private lateinit var mUserDetails: Users
    private lateinit var mExpenseList: ArrayList<Map<String, Any>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FireStore().loadUserDataInFragment(this@HomeFragment)
        FireStore().loadUserExpenseDataInFragment(this@HomeFragment)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ib_add_button.setOnClickListener {
            startActivity(Intent(activity, AddExpenseActivity::class.java))
        }
    }

    /**
     * A function to set the existing details in UI.
     */
    fun setUserDataInUI(user: Users) {
        mUserDetails = user
        tv_name.text = "Welcome ${mUserDetails.name.capitalize()} !"
    }

    /**
     * A function to set the existing details in UI.
     */
    fun setUserExpenseDataInUI(expensesList: ArrayList<Map<String, Any>>) {
        mExpenseList = expensesList
        Log.e("TAG", "setUserExpenseDataInUI: ---> $expensesList", )
    }

    fun calculateDataFromExpense(category: String){
        var totalExpense: Int =  0

    }
}
