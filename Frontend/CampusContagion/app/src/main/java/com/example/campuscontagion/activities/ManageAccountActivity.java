package com.example.campuscontagion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.campuscontagion.interfaces.IPresenter;
import com.example.campuscontagion.interfaces.IView;
import com.example.campuscontagion.util.Presenter;
import com.example.campuscontagion.R;


public class ManageAccountActivity extends AppCompatActivity implements IView {
    private TextView txtPlayer;
    private TextView txtPWResponse;


    private TextView txtStatBox;
    private EditText pwBox;

    private String playerName;
    private int uid;

    private int verifiedUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        // REMOVES DEFAULT SOFTKEYBOARD
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // SCREEN OBJECTS SETUP
        txtStatBox = findViewById(R.id.txtStatBox);
        txtPlayer = findViewById(R.id.txtPlayer);
        txtPWResponse = findViewById(R.id.txtPWResponse);
        pwBox = findViewById(R.id.txtChangePWBox);
        Button btnChangePW = findViewById(R.id.btnChangePW);
        Button btnLobby = findViewById((R.id.btnLobby));

        // INTENT INFO RETRIEVAL FOR DISPLAY
        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");
        uid = intent.getIntExtra("id", 420);
        txtPlayer.setText("Settings for: " + playerName);


        verifiedUserID = -1;

        final IPresenter p = new Presenter(this);
        p.loadUserStats(uid);


        //BUTTON LISTERN FOR LOBBY BUTTON
        btnLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyLogin();
            }
        });

        //BUTON LISTENER FOR CHANGE PW BUTTON
        btnChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.changePW(uid);
            }
        });
    }



    //METHOD TO GO TO THE HOSE PAGE, USER IS ALREADY LOGGED IN
    public void easyLogin() {
        Intent intent = new Intent(ManageAccountActivity.this, LobbyHostActivity.class);
        intent.putExtra("playerName", playerName);
        intent.putExtra("id", uid);


        //  to do here :
        //  implement code adding user ID to the lobby via volley post


        startActivity(intent);

    }

    // VIEW METHODS GO HERE
    @Override
    public void appendTextBox(String content) {
        txtStatBox.append(content);
    }

    @Override
    public void replaceTextBox(String content){
        txtPWResponse.setText(content);
    }

    @Override
    public void setLoginAccepted(Boolean status) {

    }

    @Override
    public String getPWBoxData(){
        return pwBox.getText().toString();
    }

    @Override
    public String getUserBoxData(){
        return "";
    }

    @Override
    public void goToTheLobby() {

    }

    @Override
    public void goToManageAccount() {

    }

    @Override
    public void setVerifiedUserID(int id) {
        verifiedUserID=id;
    }
}
