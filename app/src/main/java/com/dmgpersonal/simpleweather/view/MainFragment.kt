package com.dmgpersonal.simpleweather.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dmgpersonal.simpleweather.viewmodel.AppState
import com.dmgpersonal.simpleweather.R
import com.dmgpersonal.simpleweather.model.Weather
import com.dmgpersonal.simpleweather.databinding.MainFragmentBinding
import com.dmgpersonal.simpleweather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view: View = binding.root
        return view
        //return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromLocalSource()

//        val observer = Observer<Any> { renderData(it) }
//        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
    }

    private fun renderData(appState: AppState) {
        val loadingLayout = binding.loadingLayout
        when (appState) {
            is AppState.Success -> {
                val weatherData = appState.weatherData
                loadingLayout.visibility = View.GONE
                setData(weatherData)
                //Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                Snackbar.make(requireView(), "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
               // Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                Snackbar
                    .make(requireView(), "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getWeatherFromLocalSource() }
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