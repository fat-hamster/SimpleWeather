package com.dmgpersonal.simpleweather.view.cities_list

import android.appwidget.AppWidgetProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dmgpersonal.simpleweather.R
import com.dmgpersonal.simpleweather.databinding.FragmentCitiesListBinding
import com.dmgpersonal.simpleweather.model.Weather
import com.dmgpersonal.simpleweather.view.main.MainFragment
import com.dmgpersonal.simpleweather.viewmodel.AppState
import com.dmgpersonal.simpleweather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import okhttp3.internal.notify

class CitiesListFragment() : Fragment() {
    private var _binding: FragmentCitiesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private val adapter = CitiesFragmentListAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            //viewModel.getLiveData().value = AppState.SuccessData(weather)
            viewModel.getLiveData().postValue(AppState.SuccessData(weather))
            MainFragment.city = weather.city
            parentFragmentManager.popBackStack()
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listCitiesFragmentRecyclerView.adapter = adapter
        binding.citiesFragmentFAB.setOnClickListener { changeWeatherDataSet() }
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun changeWeatherDataSet() {
        if(isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            binding.citiesFragmentFAB.setImageResource(R.drawable.ic_world)
        } else {
            viewModel.getWeatherFromLocalSourceRus()
            binding.citiesFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessList -> {
                binding.citiesFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(appState.weatherData)
            }
            is AppState.Loading -> {
                binding.citiesFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.citiesFragmentLoadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.citiesFragmentFAB, getString(R.string.error),
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.reload)) {
                        viewModel.getWeatherFromLocalSourceRus()
                    }
                    .show()
            }
        }
    }

    companion object {
        var isDataSetRus: Boolean = true
        fun newInstance() =
            CitiesListFragment()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }
}