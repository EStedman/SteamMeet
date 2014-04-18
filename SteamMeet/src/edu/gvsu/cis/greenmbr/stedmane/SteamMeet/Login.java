package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Brett on 4/11/14.
 */
public class Login extends Activity implements View.OnClickListener{
    EditText input;
    Button explain, main;
    public static final String PREFS = "MyPrefsFile";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            Toast toast = Toast.makeText(getApplicationContext(), savedInstanceState.getString("wuzzat"), 3);
            toast.show();
        }

        setContentView(R.layout.login);
        input = (EditText) findViewById(R.id.editText);
        explain = (Button) findViewById(R.id.button);
        main = (Button) findViewById(R.id.button2);
        explain.setOnClickListener(this);
        main.setOnClickListener(this);
        SharedPreferences settings = getSharedPreferences(PREFS, 0);
        String prof = settings.getString("storage", null);
        input.setText(prof);
        //
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public void onClick(View v) {
        if (v == main){
            String storage = input.getText().toString();
            URL profileURL;
            String out = "";
            JSONObject arr = null;
            try {
                profileURL = new URL("http://api.steampowered.com/" +
                        "ISteamUser/GetPlayerSummaries/v00" +
                        "02/?key=A35259FADACBD1E99D1101AD8" +
                        "4321147&steamids=" + storage);
                HttpURLConnection conn = (HttpURLConnection) profileURL.openConnection();
                Scanner scan = new Scanner(conn.getInputStream());
                if(!scan.hasNextLine())

                while (scan.hasNextLine()) {
                    out += scan.nextLine();
                }
                arr = new JSONObject(out);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(arr != null){
                SharedPreferences prof = getSharedPreferences(PREFS, 0);
                SharedPreferences.Editor editor = prof.edit();
                editor.putString("profnum", storage);
                editor.commit();
                Intent toMain = new Intent(this, MyActivity.class);
                toMain.putExtra("storage", storage);
                startActivity(toMain);
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid ID", 3);
                toast.show();
            }
        }
        if (v == explain){
            Intent toExplain = new Intent(this, Explain.class);
            startActivity(toExplain);
        }
    }
}
