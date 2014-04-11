package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Brett on 4/11/14.
 */
public class Login extends Activity {
    EditText input;
    Button explain, main;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        input = (EditText) findViewById(R.id.editText);
        explain = (Button) findViewById(R.id.button);
        main = (Button) findViewById(R.id.button2);
    }
    public void onClick(View v) {
        if (v == main){
            String storage = input.getText().toString();
            Intent toMain = new Intent(this, MyActivity.class);
            toMain.putExtra("storage", storage);
            startActivity(toMain);
        }
        if (v == explain){
            Intent toExplain = new Intent(this, Explain.class);
            startActivity(toExplain);
        }
    }
}
