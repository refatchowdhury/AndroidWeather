package com.refat.restweather.app.retrofit;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.refat.restweather.app.R;


public class AutoPlaceActivity extends Activity {
    public static String TAG =AutoPlaceActivity.class.getSimpleName();
    public static final String PREFS_NAME = "LOC_PREFS";
    private String city;
    private double lat,lon;
    private SharedPreference sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //setTheme(android.R.style.Theme_Dialog);
        setContentView(R.layout.activity_auto_place);
        sharedPreference = new SharedPreference();
        Button close_button = (Button) findViewById(R.id.close_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreference.save(AutoPlaceActivity.this,city, lat,lon);
                Log.i(TAG, "Saved new location in sharedpreference.");
                finish();
            }
        });
       // PlaceAutocompleteFragment autocompleteFragment1 =new PlaceAutocompleteFragment();
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                Log.i(TAG, "Place: " + place.getId().toString());
                Log.i(TAG, "Place: " + place.getLatLng().toString());
                city=place.getName().toString();
                lat=place.getLatLng().latitude;
                lon=place.getLatLng().longitude;
                // Save the text in SharedPreference
               // sharedPreference.save(AutoPlaceActivity.this,place.getName().toString(), place.getLatLng().latitude,place.getLatLng().longitude);



            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }



}
