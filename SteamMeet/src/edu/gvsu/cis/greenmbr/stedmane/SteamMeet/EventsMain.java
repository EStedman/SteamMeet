package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.parse.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Evan on 4/18/14.
 */


/*
public class EventsMain extends Activity {
    TextView pull;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testingpuller);
        pull = (TextView)findViewById(R.id.testing1);
        //Parse.initialize(this, "2JUDU3NzfMd4QN1KY2HiKWFpAG9nSiAyeWM4aQNg",
        //        "YZazw5idYfUiivovNxZFUezRSBznPGbmTlvYDkZW");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("place");
        query.whereEqualTo("PlaceName", "fourth");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> placeList, ParseException e) {
                if (e == null) {
                    size = placeList.size();
                    pull.setText(Integer.toString(size));
                    Log.d("location", "emailAddress " + placeList.size());

                } else {
                    Log.d("location", "Error: " + e.getMessage());
                }
            }
        });
    } */




public class EventsMain extends ListActivity implements SimpleAdapter.ViewBinder, LocationListener,
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener{
    private ArrayList<Map<String, Object>> nearbyEvents;
    private SimpleAdapter eventsAdapter;
    private final double MAX_DISTANCE = 30;
    private LocationClient mapClient2;
    private Location myLoc;
    private ParseGeoPoint myGeoLoc;
    String email;
    Intent intented;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View header = getLayoutInflater().inflate(R.layout.event_header, null);
        ListView listview = getListView();
        listview.addHeaderView(header);
        nearbyEvents = new ArrayList<Map<String, Object>>();
        intented = getIntent();
        email = intented.getStringExtra("emailAddress");
        eventsAdapter = new SimpleAdapter(
                this,
                nearbyEvents,
                R.layout.event_page,
                new String[]{"PlaceName", "EmailAddress"},
                new int[]{R.id.main_text, R.id.sub_text}
        );
        setListAdapter(eventsAdapter);
        eventsAdapter.setViewBinder(this);
        mapClient2 = new LocationClient(this, this, this);
        /*
        myLoc = mapClient2.getLastLocation();
        myGeoLoc = new ParseGeoPoint(myLoc.getLatitude(), myLoc.getLongitude()); */
        new eventsTask().execute();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mapClient2 != null)
            mapClient2.connect();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mapClient2 != null)
            mapClient2.disconnect(); /* disconnect when our app is invisible */
    }




    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  ,
                new String[]{(String) nearbyEvents.get(position).get("emailAddress")});
        //i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        //i.putExtra(Intent.EXTRA_TEXT   , "body of email");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

        //TODO: CREATE INTENT TO OPEN EMAIL. LEFT CODE FROM HOMEWORK 3 BELOW

       /* int targetId = (Integer) eventsAdapter.get(position).get("appid");
        Intent detailIntent = new Intent(this, Achievements.class);
        detailIntent.putExtra("id", targetId);
        startActivity(detailIntent); */



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

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {
        return false;
    }

    //
    private class eventsTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            ParseObject sdf = null;
            ParseGeoPoint loc = null;
            loc = new ParseGeoPoint (intented.getDoubleExtra("lat", 0),
            intented.getDoubleExtra("lon", 0));
            ParseQuery<ParseObject> query = ParseQuery.getQuery("place");
            query.whereWithinMiles("location", loc, MAX_DISTANCE);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                for(ParseObject a : scoreList){
                        Map<String, Object> newPlace = new HashMap<String, Object>();
                        newPlace.put("EmailAddress", a.getString("EmailAddress"));
                        newPlace.put("PlaceName", a.getString("PlaceName"));
                        nearbyEvents.add(newPlace);
                }
                }
            });
            return null;
        }
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Void asd) {
            setProgressBarIndeterminateVisibility(false);
            eventsAdapter.notifyDataSetChanged(); // inform the ListView to update itself
        }

    }

}

