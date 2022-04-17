package com.dmgpersonal.simpleweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmgpersonal.simpleweather.databinding.MainActivityBinding
import com.dmgpersonal.simpleweather.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        //setContentView(R.layout.main_activity)
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}