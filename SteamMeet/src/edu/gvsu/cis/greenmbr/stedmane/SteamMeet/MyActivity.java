package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MyActivity extends Activity implements View.OnClickListener {
    // Called when the activity is first created.
    private TextView profile, user, clanID, state;
    private String userSave, profileSave, clanSave, stateSave;
    private ImageView avatar;
    private String profNumber;
    private Button eventButton, linkEmail;
    //private Spinner eventOrProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileview);
        user = (TextView) findViewById(R.id.persona);
        profile = (TextView) findViewById(R.id.profileSite);
        state = (TextView) findViewById(R.id.activeState);
        clanID = (TextView) findViewById(R.id.clan);
        avatar = (ImageView) findViewById(R.id.imageView);
        eventButton = (Button) findViewById(R.id.toEvents);
        linkEmail = (Button) findViewById(R.id.link);
        Intent intented = getIntent();
        profNumber = intented.getStringExtra("storage");
        eventButton.setOnClickListener(this);
        linkEmail.setOnClickListener(this);
        /*If have time to finish spinner

        eventOrProfile = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventOrProfile.setAdapter(adapter);
        eventOrProfile.setOnItemSelectedListener(this);
        */

        if(savedInstanceState != null){
            userSave = savedInstanceState.getString("UserSave");
            profileSave = savedInstanceState.getString("ProfileSave");
            clanSave = savedInstanceState.getString("ClanSave");
            stateSave = savedInstanceState.getString("StateSave");
            user.setText(userSave);
            profile.setText(profileSave);
            clanID.setText(clanSave);
            state.setText(stateSave);
            new imageTask().execute();
        }
        else{
            new ProfileTask().execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("UserSave", user.getText().toString());
        outState.putString("ProfileSave", profile.getText().toString());
        outState.putString("ClanSave", clanID.getText().toString());
        outState.putString("StateSave", state.getText().toString());
    }

    @Override
    public void onClick(View v) {
        if(v == eventButton){
            Intent toEvents = new Intent(this, EventsMain.class);
            startActivity(toEvents);
        }
        if(v == linkEmail){
            Intent toEmail = new Intent(this, EmailLogin.class);
            startActivity(toEmail);
        }
    }

    /*Part of spinner

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selectedItem = parent.getItemAtPosition(pos).toString();
        if("Events".equalsIgnoreCase(selectedItem)){
            Intent eventPass = new Intent(this, EventsMain.class);
            startActivity(eventPass);
        }
        if("Profile".equalsIgnoreCase(selectedItem)){
            Intent profilePass = new Intent(this, MyActivity.class);
            startActivity(profilePass);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        setContentView(R.layout.profileview);
    }
    */

    private class ProfileTask extends AsyncTask<Void, Integer, JSONObject> {
        private Drawable avatarImg;
        @Override
        protected JSONObject doInBackground(Void... params) {
            URL profileURL;
            try {
                profileURL = new URL("http://api.steampowered.com/" +
                        "ISteamUser/GetPlayerSummaries/v00" +
                        "02/?key=A35259FADACBD1E99D1101AD8" +
                        "4321147&steamids=" + profNumber);
                String out = "";
                HttpURLConnection conn = (HttpURLConnection) profileURL.openConnection();
                Scanner scan = new Scanner(conn.getInputStream());
                while (scan.hasNextLine()) {
                    out += scan.nextLine();
                }
                JSONObject arr = new JSONObject(out);
                JSONArray obj = (arr.getJSONObject("response")).getJSONArray("players");
                JSONObject profileObj = obj.getJSONObject(0);
                String avatarString = profileObj.getString("avatarfull");
                URL avatarURL = new URL(avatarString);
                avatarImg = Drawable.createFromStream(avatarURL.openStream(), "Picture");
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
        protected void onPostExecute(JSONObject profileData) {
            try {
                String userTemp = profileData.getString("personaname");
                String profTemp = profileData.getString("profileurl");
                String clanTemp = profileData.getString("primaryclanid");
                int stateTemp = profileData.getInt("personastate");
                avatar.setImageDrawable(avatarImg);

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
            }
        }
    }
    private class imageTask extends AsyncTask<Void, Integer, Void> {
        Drawable avatar2;
        @Override
        protected Void doInBackground(Void... params) {
            URL profileURL;
            try {
                profileURL = new URL("http://api.steampowered.com/" +
                        "ISteamUser/GetPlayerSummaries/v00" +
                        "02/?key=A35259FADACBD1E99D1101AD8" +
                        "4321147&steamids=" + profNumber);
                String out = "";
                HttpURLConnection conn = (HttpURLConnection) profileURL.openConnection();
                Scanner scan = new Scanner(conn.getInputStream());
                while (scan.hasNextLine()) {
                    out += scan.nextLine();
                }
                JSONObject arr = new JSONObject(out);
                JSONArray obj = (arr.getJSONObject("response")).getJSONArray("players");
                JSONObject profileObj = obj.getJSONObject(0);
                String avatarString = profileObj.getString("avatarfull");
                URL avatarURL = new URL(avatarString);
                avatar2 = Drawable.createFromStream(avatarURL.openStream(), "Picture");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            avatar.setImageDrawable(avatar2);
        }
    }
}
