package com.example.expensemanager.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class ExpenseList(var amount: String,
                       var category: String,
                       var notes: String,
                       var date: String  ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(amount)
        parcel.writeString(category)
        parcel.writeString(notes)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExpenseList> {
        override fun createFromParcel(parcel: Parcel): ExpenseList {
            return ExpenseList(parcel)
        }

        override fun newArray(size: Int): Array<ExpenseList?> {
            return arrayOfNulls(size)
        }
    }

}