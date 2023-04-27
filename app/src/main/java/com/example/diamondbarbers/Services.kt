package com.example.diamondbarbers

import android.os.Parcel
import android.os.Parcelable

class Services(
    var price: String,
    var time: String,
    var type: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(price)
        parcel.writeString(time)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Services> {
        override fun createFromParcel(parcel: Parcel): Services {
            return Services(parcel)
        }

        override fun newArray(size: Int): Array<Services?> {
            return arrayOfNulls(size)
        }
    }
}






