package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Brett on 4/11/14.
 */
public class Login extends Activity implements View.OnClickListener{
    private EditText input;
    private Button explain, test, confirm;
    private Drawable avatar2 = null;
    private TextView note;
    private Boolean idWorked;
    public static final String PREFS = "MyPrefsFile";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        note = (TextView) findViewById(R.id.note);
        input = (EditText) findViewById(R.id.editText);
        explain = (Button) findViewById(R.id.button);
        test = (Button) findViewById(R.id.test);
        confirm = (Button) findViewById(R.id.confirm);
        idWorked = null;
        explain.setOnClickListener(this);
        test.setOnClickListener(this);
        confirm.setOnClickListener(this);
        SharedPreferences settings = this.getSharedPreferences(PREFS, 0);
        String prof = settings.getString("profnum", null);
        input.setText(prof);
        if(prof != null){
            String storage = input.getText().toString();
            Intent toMain = new Intent(this, MyActivity.class);
            toMain.putExtra("storage", storage);
            startActivity(toMain);
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v == test){
            new imageTask().execute();
        }
        if (v == explain){
            Intent toExplain = new Intent(this, Explain.class);
            startActivity(toExplain);
        }
        if (v == confirm){
            if(idWorked){
                String storage = input.getText().toString();
                SharedPreferences prof = this.getSharedPreferences(PREFS, 0);
                SharedPreferences.Editor editor = prof.edit();
                editor.putString("profnum", storage);
                editor.commit();
                Intent toMain = new Intent(this, MyActivity.class);
                toMain.putExtra("storage", storage);
                startActivity(toMain);
            }
            else
                note.setText("You did not enter a Valid ID!");
        }
    }
    private class imageTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            URL profileURL;
            String storage = input.getText().toString();
            try {
                profileURL = new URL("http://api.steampowered.com/" +
                        "ISteamUser/GetPlayerSummaries/v00" +
                        "02/?key=A35259FADACBD1E99D1101AD8" +
                        "4321147&steamids=" + storage);
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
                avatar2 = null;
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
                avatar2 = null;
            } catch (IOException e) {
                e.printStackTrace();
                avatar2 = null;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(avatar2 == null){
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid ID", 10);
                toast.show();
                idWorked = false;
                confirm.setVisibility(-1);
                test.setVisibility(1);
                note.setTextColor(Color.parseColor("#ff290c"));
                note.setText("You did not enter a Valid ID!");
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "worked ", 5);
                toast.show();
                confirm.setVisibility(1);
                test.setVisibility(-1);
                idWorked = true;
                note.setTextColor(Color.parseColor("#0CFF18"));
                note.setText("Your Id Is Valid");




            }
        }
    }
}
