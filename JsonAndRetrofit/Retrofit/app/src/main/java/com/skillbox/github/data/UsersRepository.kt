package com.skillbox.github.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UsersRepository(
    val id: Long,
    val name: String,
    val owner: OwnerOfRepo
): Parcelable