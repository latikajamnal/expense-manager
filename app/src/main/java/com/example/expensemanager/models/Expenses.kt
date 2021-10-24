package com.example.expensemanager.models

import android.os.Parcel
import android.os.Parcelable

data class Expenses(val uid: String = "",
                    val name: String = "",
                    var expenseList: ArrayList<ExpenseList> = ArrayList() ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(ExpenseList.CREATOR)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(name)
        parcel.writeTypedList(expenseList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Expenses> {
        override fun createFromParcel(parcel: Parcel): Expenses {
            return Expenses(parcel)
        }

        override fun newArray(size: Int): Array<Expenses?> {
            return arrayOfNulls(size)
        }
    }

}