package com.dmgpersonal.simpleweather.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MonthWeather(val res: String): Parcelable {
    private var month: List<Weather> = listOf()
    init {
        month = parse(res)
    }

    private fun parse(res: String): List<Weather> {
        val result: MutableList<Weather> = ArrayList()
        // обработка строки ответа от сервера
        result.add(Weather(City("TestCity", 3.555, 0.222), 12, 10))
        return result
    }

    fun getCurrentWeather(day: Int) = month[day]

    fun getMonth() = month
}