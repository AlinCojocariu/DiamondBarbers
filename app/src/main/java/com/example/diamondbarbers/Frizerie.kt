package com.example.diamondbarbers

import android.os.Parcel
import android.os.Parcelable

class Frizerie(
    var nume: String,
    var adresa: String,
    var oras: String,
    var hairStylists: List<HairStylist>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(HairStylist.CREATOR)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nume)
        parcel.writeString(adresa)
        parcel.writeString(oras)
        parcel.writeTypedList(hairStylists)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Frizerie> {
        override fun createFromParcel(parcel: Parcel): Frizerie {
            return Frizerie(parcel)
        }

        override fun newArray(size: Int): Array<Frizerie?> {
            return arrayOfNulls(size)
        }
    }
}