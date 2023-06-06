package com.example.diamondbarbers

import android.os.Parcel
import android.os.Parcelable

class Products(var index:Int ,var productName:String):Parcelable {

//    operator fun get(position: Int): Any {
//        return productsList[position]
//
//    }

    //    operator fun set(postion:Int , newName:String) {
//        productList[position]=newName
//
//    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!

    )



    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

        parcel.writeInt(index)
        parcel.writeString(productName)
    }

    companion object CREATOR : Parcelable.Creator<Products> {
        override fun createFromParcel(parcel: Parcel): Products {
            return Products(parcel)
        }

        override fun newArray(size: Int): Array<Products?> {
            return arrayOfNulls(size)
        }
    }
}