package com.example.diamondbarbers

import android.os.Parcel
import android.os.Parcelable

class Appointments(
    var date: String,
    var hour: String,
    var name: String,
    var phone: String,
    var type: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(hour)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Appointments> {
        override fun createFromParcel(parcel: Parcel): Appointments {
            return Appointments(parcel)
        }

        override fun newArray(size: Int): Array<Appointments?> {
            return arrayOfNulls(size)
        }
    }
}