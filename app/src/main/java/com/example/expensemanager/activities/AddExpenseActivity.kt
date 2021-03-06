package com.example.expensemanager.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import com.example.expensemanager.R
import com.example.expensemanager.firebase.FireStore
import com.example.expensemanager.models.Expenses
import kotlinx.android.synthetic.main.activity_add_expense.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.*
import com.google.firebase.auth.FirebaseAuth


class AddExpenseActivity : BaseActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    private var cal = Calendar.getInstance()
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var selectedCategory: String
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_expense)
        setUpExpenseUI()
        et_expense_date.setOnClickListener(this)
        btn_expense_save.setOnClickListener(this)
        dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
    }

    fun setUpExpenseUI() {
        spinner = findViewById(R.id.dropdown_categories)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.expense_category,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
        val todayDate =
            SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date()).toString()
        (et_expense_date as TextView).text = todayDate
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.et_expense_date -> {
                datePickerDialog = DatePickerDialog(
                    this,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                )
                //following line to restrict future date selection
                datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
                datePickerDialog.show()
            }
            R.id.btn_expense_save -> {
                if ((et_expense.text.toString() != "") || (et_expense.text.toString() != "0")) {
                    addExpense()
                } else {
                    showErrorSnackBar("Please add Expense Value")
                }
            }
        }
    }

    /**
     * A function to update the selected date in the UI with selected format.
     * This function is created because every time we don't need to add format which we have added here to show it in the UI.
     */
    private fun updateDateInView() {
        val myFormat = "dd.MM.yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault()) // A date format
        et_expense_date.setText(
            sdf.format(cal.time).toString()
        ) // A selected date using format which we have used is set to the UI.
    }

    private fun addExpense() {
        val expenseArrayList: ArrayList<String> = ArrayList()
        var expense = Expenses(
            FirebaseAuth.getInstance().currentUser?.uid!!.toString(),
            "", // name would be fetched later
            et_expense.text.toString().toFloat(),
            selectedCategory,
            et_expense_description.text.toString(),
            et_expense_date.text.toString()
        )
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStore().addExpenseFetchName(this, expense)
    }

    fun expenseAddedSuccessfully() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent!!.getItemAtPosition(position)
        selectedCategory = spinner.selectedItem.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        parent!!.getItemAtPosition(0)
        selectedCategory = spinner.selectedItem.toString()
    }
}
