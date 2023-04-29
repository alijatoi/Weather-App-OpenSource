package com.example.weatherappusingopenmeteo.presentation.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherappusingopenmeteo.R
import com.example.weatherappusingopenmeteo.data.local.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.databinding.FragmentSearchBinding
import com.example.weatherappusingopenmeteo.presentation.WeatherViewModel
import com.example.weatherappusingopenmeteo.utils.NetworkUtils
import com.example.weatherappusingopenmeteo.utils.PermissionCheck
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment() {

    companion object {
        init {
            System.loadLibrary("keys")
        }
    }
    external fun getApi(): String

    @Inject
    lateinit var permissionCheck: PermissionCheck
    @Inject
    lateinit var checkInternet : NetworkUtils

    var places : Place? = null
    lateinit var weatherViewModel : WeatherViewModel

    private val autoComplete = 100

    private lateinit var viewBinding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSearchBinding.inflate(inflater, container, false)
        viewBinding.mainCardview.visibility = View.GONE
        viewBinding.autocompleteFragment.visibility = View.GONE

        viewBinding.searcButton.setOnClickListener {
            searchWeather()
        }

        weatherViewModel.cityWeather.observe(viewLifecycleOwner){weather ->
                showCityWeather(weather)
        }

        return viewBinding.root
    }

    private fun showCityWeather(update: CurrentWeatherEntity?) {
        viewBinding.cityName.text = places?.name.toString()
        viewBinding.temperature.text = update?.temperature.toString()
        viewBinding.windDirectionText.text = update?.winddirection.toString()
        viewBinding.windSpeedText.text = update?.windspeed.toString()
    }

    private fun searchWeather() {
        if (permissionCheck.requestLocationPermission()) {
            if (checkInternet.isNetworkConnected()) {

        Places.initialize(requireActivity().applicationContext, getApi())

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.NAME, Place.Field.LAT_LNG))


        // return after the user has made a selection.
        val fields = listOf(Place.Field.NAME, Place.Field.LAT_LNG)

        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(requireActivity())
        startActivityForResult(intent, autoComplete)
    }
            else {
                showPopup("Connect Internet","Please Connect Internet To Update The Weather")
            }
        }
        else{
            showPopup("Location On","Please Turn on Location to Update the Weather",)
        }
    }



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == autoComplete) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        places = place
                        viewBinding.mainCardview.visibility = View.VISIBLE
                        weatherViewModel.getCityWeather(place.latLng)
                        Log.i("ssssssss", "Placeeeeee: ${place.name}, ${place.latLng.latitude}")

                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        showPopup("Error", "Error  searching for city !")
                    }
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showPopup(title: String, message : String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { _, _ ->
        }
        val dialog = builder.create()
        dialog.show()
    }
}