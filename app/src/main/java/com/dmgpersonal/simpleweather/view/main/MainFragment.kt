package com.dmgpersonal.simpleweather.view.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmgpersonal.simpleweather.viewmodel.AppState
import com.dmgpersonal.simpleweather.R
import com.dmgpersonal.simpleweather.model.Weather
import com.dmgpersonal.simpleweather.databinding.MainFragmentBinding
import com.dmgpersonal.simpleweather.model.City
import com.dmgpersonal.simpleweather.view.cities_list.CitiesListFragment
import com.dmgpersonal.simpleweather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    // эта часть кода мне, вероятно, не нужна.
    companion object {
        const val BUNDLE_EXTRA = "weather"

//        fun newInstance(bundle: Bundle): MainFragment {
//            val fragment = MainFragment()
//            fragment.arguments = bundle
//
//            return fragment
//        }
        fun newInstance() : MainFragment {
            return MainFragment()
        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getWeatherFromLocalSourceCity(City("Москва", 55.755826, 37.617299900000035))
            //viewModel.getWeatherFromLocalSourceRus()

        binding.cityName.setOnClickListener { listCities() }
    }

    private fun listCities() {
        val citiesList = CitiesListFragment.newInstance()
        activity?.supportFragmentManager!!.beginTransaction()
            .replace(R.id.container, citiesList)
            .addToBackStack("")
            .commit()
    }

    private fun renderData(appState: AppState) {
        val loadingLayout = binding.loadingLayout
        when (appState) {
            is AppState.SuccessList -> {
                val weatherData = appState.weatherData
                loadingLayout.visibility = View.GONE
                setData(weatherData[0]) //TODO: Изменить в дальнейшем!!!
                Snackbar.make(requireView(), "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.SuccessData -> {
                loadingLayout.visibility = View.GONE
                setData(appState.weather)
                Snackbar.make(requireView(), "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
                Snackbar
                    .make(requireView(), "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getWeatherFromLocalSourceRus() }
                    .show()
            }
        }
    }

    private fun setData(weatherData: Weather) {
        val cityName = binding.cityName
        val citiCoordinates = binding.cityCoordinates
        val temperatureValue = binding.temperatureValue
        val feelsLikeValue = binding.feelsLikeValue

        cityName.text = weatherData.city.city
        citiCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            weatherData.city.lat.toString(),
            weatherData.city.lot.toString()
        )
        temperatureValue.text = weatherData.temperature.toString()
        feelsLikeValue.text = weatherData.feelsLike.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}