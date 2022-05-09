package com.dmgpersonal.simpleweather.view.cities_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dmgpersonal.simpleweather.R
import com.dmgpersonal.simpleweather.model.Weather

class CitiesFragmentListAdapter(private var onItemViewClickListener:
                                CitiesListFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<CitiesFragmentListAdapter.CitiesViewHolder>() {
    private var weatherData: List<Weather> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        return CitiesViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_cities_recycler_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class CitiesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) {
            itemView.findViewById<TextView>(R.id.citiesFragmentRecyclerItemTextView).text =
                weather.city.city
            itemView.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(weather)
            }
        }
    }
}