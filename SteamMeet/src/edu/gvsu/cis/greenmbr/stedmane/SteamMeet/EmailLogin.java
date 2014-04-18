package edu.gvsu.cis.greenmbr.stedmane.SteamMeet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Evan on 4/18/14.
 */
public class EmailLogin extends Activity implements View.OnClickListener {
    private TextView emailTitle, emailWarning;
    private EditText emailEnter;
    private Button emailConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_login);
        emailTitle = (TextView) findViewById(R.id.emailTitle);
        emailWarning = (TextView) findViewById(R.id.emailWarning);
        emailEnter = (EditText) findViewById(R.id.emailEnter);
        emailConfirm = (Button) findViewById(R.id.emailConfirm);
        emailConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == emailConfirm){

        }
    }
}
