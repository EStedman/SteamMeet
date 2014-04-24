package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.parse.Parse;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by Brett on 4/18/14.
 */
public class CreatePlaces extends Activity implements View.OnClickListener,
        LocationListener, GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10,
                              FAST_INTERVAL_CEILING_IN_MILLISECONDS = 15;
    private EditText input;
    private Button button;
    private LocationClient mapClient;
    private Location myLoc;
    private ParseGeoPoint myGeoLoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_setup);
        button = (Button) findViewById(R.id.button);
        input = (EditText) findViewById(R.id.editText);
        mapClient = new LocationClient(this, this, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mapClient != null)
            mapClient.connect();
    }
    @Override
     protected void onStop() {
        super.onStop();
        if (mapClient != null)
            mapClient.disconnect(); /* disconnect when our app is invisible */
    }

    public void onClick(View v) {
        if (v == button){
            ParseObject placeObject = new ParseObject("place");
            placeObject.put("email", "asdasd");
            myLoc = mapClient.getLastLocation();
            myGeoLoc = new ParseGeoPoint(myLoc.getLatitude(), myLoc.getLongitude());
            placeObject.put("location", myGeoLoc);


        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
