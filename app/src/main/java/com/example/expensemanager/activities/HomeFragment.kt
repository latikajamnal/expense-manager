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
import com.example.expensemanager.models.Users
import kotlinx.android.synthetic.main.home.*
import kotlin.math.exp


class HomeFragment : Fragment() {

    // A global variable for user details.
    private lateinit var mUserDetails: Users
    private lateinit var mExpenseList: ArrayList<Map<String, Any>>
    private var mFoodExpense: Float = 0f
    private var mTravelExpense: Float = 0f
    private var mEntertainmentExpense: Float = 0f
    private var mShoppingExpense: Float = 0f
    private var mOthersExpense: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).showProgressDialog(resources.getString(R.string.please_wait))
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
        mFoodExpense = calculateDataFromExpense("Food")
        mEntertainmentExpense = calculateDataFromExpense("Entertainment")
        mTravelExpense = calculateDataFromExpense("Travel")
        mShoppingExpense = calculateDataFromExpense("Shopping")
        mOthersExpense = calculateDataFromExpense("Others")
        tv_food_amount.text = "$" + mFoodExpense.toString()
        tv_entertainment_amount.text = "$" + mEntertainmentExpense.toString()
        tv_travel_amount.text = "$" + mTravelExpense.toString()
        tv_shopping_amount.text = "$" + mShoppingExpense.toString()
        tv_other_amount.text = "$" + mOthersExpense.toString()
        tv_total_amount.text =
            "$" + (mFoodExpense + mEntertainmentExpense + mTravelExpense + mShoppingExpense + mOthersExpense).toString()
        (activity as MainActivity).hideProgressDialog()
    }

    fun calculateDataFromExpense(category: String): Float {
        var totalExpense: Float = 0f
        for (expense in mExpenseList) {
            if (expense["category"] == category) {
                totalExpense += expense["amount"].toString().toFloat()
            }
        }
        return totalExpense
    }
}
