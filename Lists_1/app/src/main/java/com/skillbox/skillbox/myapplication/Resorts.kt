package com.skillbox.skillbox.myapplication

import android.os.Parcel
import android.os.Parcelable

sealed class Resorts: Parcelable {
    data class Sea(
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

        companion object CREATOR : Parcelable.Creator<Sea> {
            override fun createFromParcel(parcel: Parcel): Sea {
                return Sea(parcel)
            }

            override fun newArray(size: Int): Array<Sea?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Mountain(
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

        companion object CREATOR : Parcelable.Creator<Mountain> {
            override fun createFromParcel(parcel: Parcel): Mountain {
                return Mountain(parcel)
            }

            override fun newArray(size: Int): Array<Mountain?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Ocean(
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

        companion object CREATOR : Parcelable.Creator<Ocean> {
            override fun createFromParcel(parcel: Parcel): Ocean {
                return Ocean(parcel)
            }

            override fun newArray(size: Int): Array<Ocean?> {
                return arrayOfNulls(size)
            }
        }
    }
}