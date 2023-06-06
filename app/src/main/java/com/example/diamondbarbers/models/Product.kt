package com.example.diamondbarbers.models

import android.os.Parcel
import android.os.Parcelable

class Product(var index:Int, var productName:String):Parcelable {

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

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}