package com.example.diamondbarbers

import android.os.Parcel
import android.os.Parcelable

class HairStylist(
    var id: Int,
    var name: String,
    var image: String,
    var phone: String,
    var services: List<Services>,
    var appointments: List<Appointments>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(Services.CREATOR)!!,
        parcel.createTypedArrayList(Appointments.CREATOR)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(phone)
        parcel.writeTypedList(services)
        parcel.writeTypedList(appointments)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HairStylist> {
        override fun createFromParcel(parcel: Parcel): HairStylist {
            return HairStylist(parcel)
        }

        override fun newArray(size: Int): Array<HairStylist?> {
            return arrayOfNulls(size)
        }
    }
}