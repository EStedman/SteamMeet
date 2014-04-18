package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Evan on 4/18/14.
 */
public class EventsMain extends Activity{
    private TextView hey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_page);
        hey = (TextView) findViewById(R.id.hey);
    }
}
