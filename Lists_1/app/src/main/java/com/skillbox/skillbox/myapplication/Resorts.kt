package com.skillbox.skillbox.myapplication

import android.os.Parcel
import android.os.Parcelable

sealed class Resorts: Parcelable {
    data class Seas(
        val name: String?,
        val country: String?,
        val photo: Int,
        val sea: String?
    ) : Resorts() {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(country)
            parcel.writeInt(photo)
            parcel.writeString(sea)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Seas> {
            override fun createFromParcel(parcel: Parcel): Seas {
                return Seas(parcel)
            }

            override fun newArray(size: Int): Array<Seas?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Mountains(
        val name: String?,
        val country: String?,
        val photo: Int,
        val mountain: String?
    ) : Resorts() {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(country)
            parcel.writeInt(photo)
            parcel.writeString(mountain)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Mountains> {
            override fun createFromParcel(parcel: Parcel): Mountains {
                return Mountains(parcel)
            }

            override fun newArray(size: Int): Array<Mountains?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Oceans(
        val name: String?,
        val country: String?,
        val photo: Int,
        val ocean: String?
    ) : Resorts() {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(country)
            parcel.writeInt(photo)
            parcel.writeString(ocean)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Oceans> {
            override fun createFromParcel(parcel: Parcel): Oceans {
                return Oceans(parcel)
            }

            override fun newArray(size: Int): Array<Oceans?> {
                return arrayOfNulls(size)
            }
        }
    }
}