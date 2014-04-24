package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.*;

/**
 * Created by Evan on 4/18/14.
 */
public class EventsMain extends Activity{
    private TextView hey;
    int testint;
    String testString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_page);
        hey = (TextView) findViewById(R.id.hey);
        Parse.initialize(this, "2JUDU3NzfMd4QN1KY2HiKWFpAG9nSiAyeWM4aQNg", "YZazw5idYfUiivovNxZFUezRSBznPGbmTlvYDkZW");
        ParseObject testingSave = new ParseObject("SavedObj");
        testingSave.put("email", "testing1");
        testingSave.put("location", 1345);
        testingSave.saveInBackground();
        String objectId = testingSave.getObjectId();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("testingSave");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if(e == null){
                    Toast toast = Toast.makeText(getApplicationContext(), "emptyied ", 5);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "you fucked up ", 5);
                    toast.show();
                }
            }
        });
        testint = testingSave.getInt("location");
        testString = testingSave.getString("email");
        hey.setText(testint + " " + testString);
    }
}
