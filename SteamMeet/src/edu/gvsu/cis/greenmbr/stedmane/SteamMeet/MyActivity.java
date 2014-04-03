package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MyActivity extends Activity {
    // Called when the activity is first created.
    TextView profile, user, clanID, state;
    ImageView avatar; //Testing commit

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        user = (TextView) findViewById(R.id.persona);
        profile = (TextView) findViewById(R.id.profileSite);
        state = (TextView) findViewById(R.id.activeState);
        clanID = (TextView) findViewById(R.id.clan);
        avatar = (ImageView) findViewById(R.id.imageView);
        new ProfileTask().execute();
    }

    private class ProfileTask extends AsyncTask<Void, Integer, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {
            URL profileURL = null;
            try {
                profileURL = new URL("http://api.steampowered.com/" +
                        "ISteamUser/GetPlayerSummaries/v00" +
                        "02/?key=A35259FADACBD1E99D1101AD8" +
                        "4321147&steamids=76561198046688891");
                String out = "";
                HttpURLConnection conn = (HttpURLConnection) profileURL.openConnection();
                Scanner scan = new Scanner(conn.getInputStream());
                while (scan.hasNextLine()) {
                    out += scan.nextLine();
                }
                JSONObject arr = new JSONObject(out);
                JSONArray obj = (arr.getJSONObject("response")).getJSONArray("players");
                JSONObject profileObj = obj.getJSONObject(0);
                return profileObj;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONObject profileFirst) {
            try {
                String userTemp = profileFirst.getString("personaname");
                String profTemp = profileFirst.getString("profileurl");
                String clanTemp = profileFirst.getString("primaryclanid");
                int stateTemp = profileFirst.getInt("personastate");

                /*
                String avatarURL = profileFirst.getString("avatarmedium");
                URL imgUrl = new URL(avatarURL);
                Drawable avatarTemp = Drawable.createFromStream(imgUrl.openStream(), "Picture");
                avatar.setImageDrawable(avatarTemp);
                */

                user.setText("User Name: " + userTemp);
                profile.setText("Steam Profile: " + profTemp);
                clanID.setText("Clan ID: " + clanTemp);
                if (stateTemp == 1)
                    state.setText("State: Online");
                else if (stateTemp == 2) {
                    state.setText("State: Busy");
                } else if (stateTemp == 3) {
                    state.setText("State: Away");
                } else if (stateTemp == 4) {
                    state.setText("State: Snooze");
                } else if (stateTemp == 5) {
                    state.setText("State: Looking to Play");
                } else if (stateTemp == 6) {
                    state.setText("State: Looking to Trade");
                } else
                    state.setText("State: Offline");
            } catch (JSONException e) {
                e.printStackTrace();
            }/* catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }
}

