package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Evan on 4/18/14.
 */
public class EventsMain extends ListActivity implements SimpleAdapter.ViewBinder{
    private ArrayList<Map<String, Object>> nearbyEvents;
    private SimpleAdapter eventsAdapter;
    private final double MAX_DISTANCE = 30;
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
        View header = getLayoutInflater().inflate(R.layout.event_header, null);
        ListView listview = getListView();
        listview.addHeaderView(header);
        nearbyEvents = new ArrayList<Map<String, Object>>();
        eventsAdapter = new SimpleAdapter(
                this,
                nearbyEvents,                            /* data source */
                R.layout.event_page,   /* builtin layout of 2 textviews */
                new String[]{"placename", "emailaddress", "img_icon_url"},
                /* how arraylist contents are mapped onto the view layout */
                new int[]{R.id.main_text, R.id.sub_text, R.id.img}
        );
        setListAdapter(eventsAdapter);
        eventsAdapter.setViewBinder(this);
        new eventsTask().execute();
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
                new String[]{(String) nearbyEvents.get(position).get("emailaddress")});
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
//
    private class eventsTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Place");
            query.whereWithinMiles("location", /* add geopoint */, MAX_DISTANCE);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                    for(ParseObject a : scoreList){
                        Map<String, Object> newPlace = new HashMap<String, Object>();
                        newPlace.put("emailaddress", a.getString("emailaddress"));
                        newPlace.put("placename", a.getString("placename"));
                        nearbyEvents.add(newPlace);
                        //TODO: Add GPS awareness
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
