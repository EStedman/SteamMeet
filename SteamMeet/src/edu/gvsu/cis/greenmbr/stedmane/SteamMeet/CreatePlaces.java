package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Brett on 4/18/14.
 */
public class CreatePlaces extends Activity implements View.OnClickListener{
    EditText input;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_setup);
        button = (Button) findViewById(R.id.button);
        input = (EditText) findViewById(R.id.editText);
        /*
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
          */
    }

    /*
    LocationManager locationManager = (LocationManager)
            getSystemService(Context.LOCATION_SERVICE);
    LocationListener locationListener = new MyLocationListener();

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            locationResult.gotLocation(loc);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
    */
    public void onClick(View v) {
        if (v == button){
            ParseObject placeObject = new ParseObject("place");
            placeObject.put("email", "filler");
        }
    }
}
