package com.skillbox.skillbox.myapplication.classes

import android.os.Parcel
import android.os.Parcelable

data class ImagesForLists(
    val id: Long,
    val picture: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeInt(picture)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImagesForLists> {
        override fun createFromParcel(parcel: Parcel): ImagesForLists {
            return ImagesForLists(parcel)
        }

        override fun newArray(size: Int): Array<ImagesForLists?> {
            return arrayOfNulls(size)
        }
    }
}
