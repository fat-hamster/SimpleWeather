package com.dmgpersonal.simpleweather.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import com.dmgpersonal.simpleweather.AppState
import com.dmgpersonal.simpleweather.R
import com.dmgpersonal.simpleweather.databinding.MainFragmentBinding
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
        viewModel.getWeather()

//        val observer = Observer<Any> { renderData(it) }
//        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
    }

    private fun renderData(appState: AppState) {
        val loadingLayout = binding.loadingLayout
        when (appState) {
            is AppState.Success -> {
                //val weatherData = appState.weatherData
                loadingLayout.visibility = View.GONE
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
                    .setAction("Reload") { viewModel.getWeather() }
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}