package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.widget.TextView;
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
    private EditText input;
    private Button newEvent;
    private LocationClient mapClient;
    private Location myLoc;
    private ParseGeoPoint myGeoLoc;
    private Intent intented, toMain;
    private String storage, emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_setup);
        Parse.initialize(this, "2JUDU3NzfMd4QN1KY2HiKWFpAG9nSiAyeWM4aQNg",
                "YZazw5idYfUiivovNxZFUezRSBznPGbmTlvYDkZW");
        newEvent = (Button) findViewById(R.id.newEvent);
        input = (EditText) findViewById(R.id.newTitle);
        newEvent.setOnClickListener(this);
        mapClient = new LocationClient(this, this, this);
        intented = getIntent();
        storage = intented.getStringExtra("storage");
        emailAddress = intented.getStringExtra("emailAddress");
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
        if (v == newEvent){

            ParseObject placeObject = new ParseObject("place");
            placeObject.put("EmailAddress", emailAddress);
            myLoc = mapClient.getLastLocation();
            myGeoLoc = new ParseGeoPoint(myLoc.getLatitude(), myLoc.getLongitude());
            placeObject.put("location", myGeoLoc);
            placeObject.put("PlaceName", input.getText().toString());
            placeObject.saveInBackground();
            toMain = new Intent(this,MyActivity.class);
            toMain.putExtra("emailAddress", emailAddress);
            toMain.putExtra("storage", storage);
            startActivity(toMain);
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

