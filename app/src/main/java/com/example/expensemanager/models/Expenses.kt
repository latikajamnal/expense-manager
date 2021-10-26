package com.example.expensemanager.models

import android.os.Parcel
import android.os.Parcelable

data class Expenses(
    val uid: String = "",
    var name: String = "",
    var amount: Float,
    var category: String,
    var notes: String,
    var date: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(name)
        parcel.writeFloat(amount)
        parcel.writeString(category)
        parcel.writeString(notes)
        parcel.writeString(date)
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
