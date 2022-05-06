package com.dmgpersonal.simpleweather.model

class RepositoryImpl: Repository {
    override fun getWeatherFromServer(city: City): Weather {
//        val inetPermission = ManagePermissions(this, )
//        val client = OkHttpClient()
//        //tC = tK − 273 ,15 температура из Кельвина с Цельсия
//
//        val request = Request.Builder()
//            //.url("https://community-open-weather-map.p.rapidapi.com/climate/month?q=San%20Francisco")
//            .url("https://community-open-weather-map.p.rapidapi.com/climate/month?q=${city.city}")
//            .get()
//            .addHeader("X-RapidAPI-Host", "community-open-weather-map.p.rapidapi.com")
//            .addHeader("X-RapidAPI-Key", "9e565928b5mshf36360f401f34d8p11e6abjsna50ed9231201")
//            .build()
//
//        val response = client.newCall(request).execute()
        return Weather()
    }

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
        return getWorldCities()
    }

    override fun getWeatherFromLocalStorageCity(city: City): Weather {
        return Weather(city, 23, 21)
    }
}