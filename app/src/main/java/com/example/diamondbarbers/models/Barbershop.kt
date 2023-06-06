package com.example.diamondbarbers.models

import android.os.Parcel
import android.os.Parcelable

class Barbershop(
    var id: Int,
    var name: String,
    var address: String,
    var city: String,
    var hairStylists: List<HairStylist>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(HairStylist)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(city)
        parcel.writeTypedList(hairStylists)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Barbershop> {
        override fun createFromParcel(parcel: Parcel): Barbershop {
            return Barbershop(parcel)
        }

        override fun newArray(size: Int): Array<Barbershop?> {
            return arrayOfNulls(size)
        }
    }
}