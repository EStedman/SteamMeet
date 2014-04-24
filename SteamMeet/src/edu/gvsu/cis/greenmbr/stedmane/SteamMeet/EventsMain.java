package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class EventsMain extends ListActivity implements SimpleAdapter.ViewBinder, LocationListener,
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener{
    private ArrayList<Map<String, Object>> nearbyEvents;
    private SimpleAdapter eventsAdapter;
    private final double MAX_DISTANCE = 30;
    private LocationClient mapClient;
    private Location myLoc;
    private ParseGeoPoint myGeoLoc;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "2JUDU3NzfMd4QN1KY2HiKWFpAG9nSiAyeWM4aQNg",
                "YZazw5idYfUiivovNxZFUezRSBznPGbmTlvYDkZW");
        View header = getLayoutInflater().inflate(R.layout.event_header, null);
        ListView listview = getListView();
        listview.addHeaderView(header);
        nearbyEvents = new ArrayList<Map<String, Object>>();
        eventsAdapter = new SimpleAdapter(
                this,
                nearbyEvents,                            /* data source */
                R.layout.event_page,   /* builtin layout of 2 textviews */
                new String[]{"placeName", "emailAddress", "img_icon_url"},
                /* how arraylist contents are mapped onto the view layout */
                new int[]{R.id.main_text, R.id.sub_text, R.id.img}
        );
        setListAdapter(eventsAdapter);
        eventsAdapter.setViewBinder(this);
        mapClient = new LocationClient(this, this, this);
        myLoc = mapClient.getLastLocation();
        myGeoLoc = new ParseGeoPoint(myLoc.getLatitude(), myLoc.getLongitude());
        new eventsTask().execute();
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

    @Override
    public boolean setViewValue(View view, Object obj, String objInString) {
        if (view.getId() == R.id.img) {
            ImageView pic = (ImageView) view;
            pic.setImageDrawable((Drawable) obj);
            return true;
        } else
            return false;
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

    //
    private class eventsTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Place");
            query.whereWithinMiles("location", myGeoLoc, MAX_DISTANCE);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                for(ParseObject a : scoreList){
                        Map<String, Object> newPlace = new HashMap<String, Object>();
                        newPlace.put("emailAddress", a.getString("emailAddress"));
                        newPlace.put("placeName", a.getString("placeName"));
                        nearbyEvents.add(newPlace);
                }
                }
            });
            return null;
        }
        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected void onPostExecute(Void asd) {
            setProgressBarIndeterminateVisibility(false);
            eventsAdapter.notifyDataSetChanged(); // inform the ListView to update itself
        }

    }
}
