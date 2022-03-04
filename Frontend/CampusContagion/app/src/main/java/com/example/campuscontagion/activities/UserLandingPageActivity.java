package com.example.campuscontagion.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

import com.example.campuscontagion.R;


//general android imports


public class UserLandingPageActivity extends AppCompatActivity {

    private String playerName;
    private int playerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing_page);



        Button toHostGame = findViewById(R.id.btnHostGame);
        Button toJoinGame = findViewById(R.id.btnJoinGame);

        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");
        playerID = intent.getIntExtra("id",0);

        toHostGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLobyHost();
            }
        });


        toJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLobbyJoin();
            }
        });

    }

    public void goToLobyHost(){
        Intent intent = new Intent(UserLandingPageActivity.this, LobbyHostActivity.class);
        intent.putExtra("playerName", playerName);
        intent.putExtra("id", playerID);
        intent.putExtra("isHosting", true);
        startActivity(intent);

    }

    public void goToLobbyJoin(){
        Intent intent = new Intent(UserLandingPageActivity.this, LobbyHostActivity.class);
        intent.putExtra("playerName", playerName);
        intent.putExtra("id", playerID);
        intent.putExtra("isHosting", false);
        startActivity(intent);
    }
}
