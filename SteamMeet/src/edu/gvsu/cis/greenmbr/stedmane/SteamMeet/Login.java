package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Brett on 4/11/14.
 */
public class Login extends Activity {
    EditText input;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        input = (EditText) findViewById(R.id.editText);
    }
    public void onClick(View v) {
        if (v == input){
            String storage = input.getText().toString();
            Intent toMain = new Intent(this, MyActivity.class);
            toMain.putExtra("storage", storage);
            startActivity(toMain);
        }
    }
}
