package com.dmgpersonal.simpleweather.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val city: String,
    val lat: Double,
    val lot: Double
) : Parcelable