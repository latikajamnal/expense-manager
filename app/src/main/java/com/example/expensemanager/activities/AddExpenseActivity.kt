package com.example.expensemanager.activities

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.expensemanager.R
import kotlinx.android.synthetic.main.activity_add_expense.*
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity(), View.OnClickListener {

    private var cal = Calendar.getInstance()
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_expense)
        setUpExpenseUI()
        et_expense_date.setOnClickListener(this)
        dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
    }

    fun setUpExpenseUI() {
        val spinner: Spinner = findViewById(R.id.dropdown_categories)
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
        }
    }

    /**
     * A function to update the selected date in the UI with selected format.
     * This function is created because every time we don't need to add format which we have added here to show it in the UI.
     */
    private fun updateDateInView() {
        val myFormat = "dd.MM.yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault()) // A date format
        et_expense_date.setText(sdf.format(cal.time).toString()) // A selected date using format which we have used is set to the UI.
    }
}
