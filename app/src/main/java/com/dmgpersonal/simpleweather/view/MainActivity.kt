package com.dmgpersonal.simpleweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.dmgpersonal.simpleweather.R
import com.dmgpersonal.simpleweather.databinding.MainActivityBinding
import com.dmgpersonal.simpleweather.model.City
import com.dmgpersonal.simpleweather.view.main.MainFragment
import com.dmgpersonal.simpleweather.view.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Здесь должен быть город определенный через геолокацию, пока хардкод

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_settings -> {
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack("")
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}