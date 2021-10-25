package com.example.expensemanager.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.expensemanager.activities.AddExpenseActivity
import com.example.expensemanager.activities.HomeFragment
import com.example.expensemanager.activities.MainActivity
import com.example.expensemanager.activities.SignUpActivity
import com.example.expensemanager.models.ExpenseList
import com.example.expensemanager.models.Expenses
import com.example.expensemanager.models.Users
import com.example.expensemanager.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class FireStore {

    private final val TAG = "FireStore"

    // Create a instance of Firebase Firestore
    private val mFireStore = FirebaseFirestore.getInstance()
    private var mName: String = ""

    /**
     * A function to make an entry of the registered user in the firestore database.
     */
    fun registerUser(activity: SignUpActivity, userInfo: Users) {

        mFireStore.collection("USERS")
            // Document ID for users fields. Here the document it is the User ID.
            .document(getCurrentUserID())
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.userRegisteredSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error writing document",
                    e
                )
            }
    }

    /**
     * A function for getting the user id of current logged user.
     */
    fun getCurrentUserID(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser
        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun addExpenseFetchName(
        activity: AddExpenseActivity,
        expenseArrayList: ArrayList<ExpenseList>
    ) {
        mFireStore.collection(Constants.USERS)
            .whereEqualTo(Constants.ID, FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnSuccessListener { document ->
                val currentUserName = document.documents[0].toObject(Users::class.java)!!.name
                activity.hideProgressDialog()
                var expense = Expenses(
                    getCurrentUserID(),
                    currentUserName,
                    expenseArrayList
                )
                addExpensesToDB(activity, expense)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "getCurrentUserName: Exception --> $e")
            }


    }

    fun addExpensesToDB(activity: AddExpenseActivity, expense: Expenses) {
        mFireStore.collection("EXPENSES")
            .document()
            .set(expense, SetOptions.merge())
            .addOnSuccessListener {
                Toast.makeText(activity, "Expense added successfully", Toast.LENGTH_SHORT).show()
                activity.expenseAddedSuccessfully()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Failed to create expense", Toast.LENGTH_SHORT).show()
            }
    }

    fun loadUserDataInFragment(fragment: HomeFragment) {
        // Here we pass the collection name from which we wants the data.
        mFireStore.collection(Constants.USERS)
            // The document id to get the Fields of user.
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                // Here we have received the document snapshot which is converted into the User Data model object.
                val loggedInUser = document.toObject(Users::class.java)!!
                fragment.setUserDataInUI(loggedInUser)
            }.addOnFailureListener {
                Log.e(
                    TAG,
                    "Error while getting loggedIn user details"
                )
            }
    }
}
