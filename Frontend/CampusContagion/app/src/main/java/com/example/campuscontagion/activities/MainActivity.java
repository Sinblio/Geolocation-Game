package com.example.campuscontagion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;


//volley imports

//json stuff
import com.example.campuscontagion.interfaces.IPresenter;
import com.example.campuscontagion.interfaces.IView;
import com.example.campuscontagion.util.Presenter;
import com.example.campuscontagion.R;


/**
 * The main activity for the app.
 * @author Jay Maxwell & Mitchell Bratina
 */
public class MainActivity extends AppCompatActivity implements IView {

    private EditText txtLoginBox;
    private EditText txtPasswordBox;


    //put a new text view box here
    private TextView txtServerMessageBox;


//    private String nameToPass;
//    private String username;
//    private String password;

    private int verifiedUserID;


 	/** 
     * @param savedInstanceState : The intance of the current view.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this prevents the keyboard from automatically focusing on the password field
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        //onscreen objects
        Button toGameLobby = findViewById(R.id.btnLobby);
        Button toManageAccount = findViewById(R.id.btnManageAccount);
        Button registerButton = findViewById(R.id.btnRegister);


        txtLoginBox = findViewById(R.id.txtLoginBox);
        txtPasswordBox = findViewById(R.id.txtPasswordBox);
        txtServerMessageBox = findViewById(R.id.txtBob);




        final IPresenter p = new Presenter(this);
        p.serverCheck();


        verifiedUserID =1;


    //ONCLICK LISTENER FOR REGISTER BUTTON
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.registerUser();
            }
        });

    //ONCLICK LISTERN FOR GAME LOBBY BUTTON
        toGameLobby.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                p.checkLoginCredentials();

            }

        });

        toManageAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                p.goToManageAccount();


            }
        });


    }








    //VIEW METHODS GO HERE


    @Override
    public void appendTextBox(String content) {
        txtServerMessageBox.append(content);

    }

    @Override
    public String getPWBoxData() {
        return txtPasswordBox.getText().toString();
    }

    @Override
    public String getUserBoxData() {
        return txtLoginBox.getText().toString();
    }

    @Override
    public void replaceTextBox(String content) {
        txtServerMessageBox.setText(content);
    }

    @Override
    public void setLoginAccepted(Boolean status) {
        //loginCheck = status;

    }

    @Override
    public void goToTheLobby() {
        String destination = "UserLandingPageActivity.class";
        Intent intent = new Intent(MainActivity.this, UserLandingPageActivity.class);
        intent.putExtra("playerName", txtLoginBox.getText().toString());
        intent.putExtra("id", verifiedUserID);
        startActivity(intent);
    }

    @Override
    public void goToManageAccount() {
        Intent intent = new Intent(MainActivity.this, ManageAccountActivity.class);
        intent.putExtra("playerName", txtLoginBox.getText().toString());
        intent.putExtra("id", verifiedUserID);
        startActivity(intent);
    }

    @Override
    public void setVerifiedUserID(int id) {
        verifiedUserID =id;
    }

}
