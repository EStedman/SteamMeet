package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Brett on 4/11/14.
 */
public class Login extends Activity implements View.OnClickListener{
    EditText input;
    Button explain, main;

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
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
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
