package com.example.diamondbarbers.models

import android.os.Parcel
import android.os.Parcelable

class HairStylist(
    var id: Int,
    var name: String,
    var image: String,
    var phone: String,
    var services: List<Service>,
    var appointments: List<Appointment>,
    var products: List<Product>,
    var gallery: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(Service)!!,
        parcel.createTypedArrayList(Appointment.CREATOR)!!,
        parcel.createTypedArrayList(Product)!!,
        parcel.createStringArrayList()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(phone)
        parcel.writeTypedList(services)
        parcel.writeTypedList(appointments)
        parcel.writeTypedList(products)
        parcel.writeStringList(gallery)
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
