package com.dmgpersonal.simpleweather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmgpersonal.simpleweather.AppState
import java.lang.Thread.sleep
import kotlin.random.Random

class MainViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()) : ViewModel() {

    fun getLiveData() = liveDataToObserve
    fun getWeather() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(2000)
            val rnd = Random.nextInt(0, 10)
            when(rnd) {
                in 1..5 -> liveDataToObserve.postValue(AppState.Success(Any()))
                else -> liveDataToObserve.postValue(AppState.Error(Throwable()))
            }

        }.start()
    }
}