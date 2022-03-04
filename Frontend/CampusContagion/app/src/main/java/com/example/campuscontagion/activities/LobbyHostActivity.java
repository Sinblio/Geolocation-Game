package com.example.campuscontagion.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


//general android imports
import android.util.Log;


//volley related imports
import com.example.campuscontagion.util.Const;
import com.example.campuscontagion.activities.GamePlay;
import com.example.campuscontagion.util.Const;
import com.example.campuscontagion.activities.GamePlay;
import com.example.campuscontagion.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


//gson for handling json stuff


//json stuff
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;


public class LobbyHostActivity extends AppCompatActivity {

    private String TAG = LobbyHostActivity.class.getSimpleName();
    private Button btnPlayGame;
    private Button btnSockets;
    private ProgressDialog pDialog;
    private TextView txtPlayerName;
    private TextView txtLobbyList;
    private String playerName;
    private int playerID;
    private boolean hostingAGame;
    private TextView txtLocation;
    private String locationMessage;

    private static final String KEY_LOCATION = "location";

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    //The entry point to the Fused Location Provider
    private FusedLocationProviderClient mFusedLocationProviderClient;


    // The geographical location where the device is currently located. That is the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    private WebSocketClient socketClient;

    // This tag will be used to cancel the request
    private String tag_string_req = "string_req";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }


        txtPlayerName = findViewById(R.id.txtPlayerName);
        txtLobbyList = findViewById(R.id.txtLobbyList);
        txtLocation = findViewById(R.id.txtLocation);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");
        playerID = intent.getIntExtra("id",0);
        hostingAGame = intent.getBooleanExtra("isHosting",false);



        //
        // if a player is hosting the game, it will append (game host) to his name in the box
        //
        txtPlayerName.append("(id=" + playerID + ") " + playerName);
        if (hostingAGame) {
            txtPlayerName.append(" (Game host)");
        }





        // if the intent passed has a TRUE value for the isAHost then this page will
        // initiate hosting the game by the player

        // if the intent passed has a FALSE value

        // initiates permissions to get location
        getLocationPermission();


        // this creates a web socket connection
        socketConnect();


        // first thing we do is tell the server our location
        // currently using hard-coded fake location data
        // lat  = 42.023874
        // long = -93.647530
        // this is in the middle of lake laverne, a place none of us are likely to take our phones


        locationMessage ="UpdateLocation42.xxxx-93.xxxx";
        txtLocation.append(locationMessage);

        getDeviceLocation();

        //double latitude = mLastKnownLocation.getLatitude();
        //double longitude = mLastKnownLocation.getLongitude();



        //socketClient.send("@JoinLobby##");
        //
        //
        //      @FindNear
        //      @UpdateLocation42.02####-93.6#####
        //
        //
        //getLobbyList();

        //these represent the buttons on the activity screen
        btnPlayGame = findViewById(R.id.btnPlayGame);
      //  Button refreshLobby = findViewById(R.id.btnRefresh);
        btnSockets = findViewById(R.id.btnSockets);

        //dialog box stuff, in case there are problems
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        btnSockets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LobbyHostActivity.this, TestChatActivity.class);
                intent.putExtra("playerName", playerName);
                intent.putExtra("id", playerID);
                startActivity(intent);
            }
        });


        btnPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hostingAGame) {
                    //until sockets work, this crashes app
                    //socketClient.send("StartGame");
                }

                Intent intent = new Intent(LobbyHostActivity.this, GamePlay.class);

                // using this intent data causes the app to crash
                //intent.putExtra("playerName", playerName);
                //intent.putExtra("id", playerID);
                startActivity(intent);
            }
        });



    }

    private void sendMessagesToSocket() {




        try {
            locationMessage = txtLocation.getText().toString();
            Log.d("location message", locationMessage);
            socketClient.send(locationMessage);
            txtLobbyList.append("Sent @UpdateLocation message to server");
        } catch (Exception e) {
            e.printStackTrace();
            txtLobbyList.append("Error on Update Location: "+e.toString()+"\n\n");
        }



        // this is where the player joins a lobby for the game
        // currently we only have one lobby, #0
        // lobby -1 will automagically join the closest lobby based on player's location
        try {
            socketClient.send("JoinLobby,-1");
            txtLobbyList.append("Sent @JoinLobby message to server");
        } catch (Exception e) {
            e.printStackTrace();
            //txtLobbyList.append(e.toString());
            txtLobbyList.append("Error on JoinLobby: "+e.toString()+"\n\n");
        }
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void socketConnect(){

        txtLobbyList.setText("initiating socket connection... \n");

        Draft[] drafts = {new Draft_6455()};

        String w = Const.URL_SOCKETS+playerID;
        try {
            socketClient = new WebSocketClient(new URI(w), drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                    txtLobbyList.append("connection opened \n");

                }

                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    //String s=txtConsole.getText().toString();
                    //t1.setText("hello world");
                    //Log.d("first", "run() returned: " + s);
                    //s=t1.getText().toString();
                    //Log.d("second", "run() returned: " + s);
                    //txtConsole.setText(message +'\n' + s);
                    txtLobbyList.append("onMessage: "+ message + "\n \n");
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    Log.d("CLOSE", "onClose() returned: " + s);
                    txtLobbyList.append("closing----" + s + "\n\n");

                }

                @Override
                public void onError(Exception e) {
                    txtLobbyList.append(" error:" + e.toString() +"\n\n");
                    e.printStackTrace();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        socketClient.connect();



    }

//    private void getLobbyList(){
//        txtLobbyList.setText("getting lobby  list");
//
//
//        String url = Const.URL_LOBBY;
//
//
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//                            Log.d(TAG, "inside of response");
//                            txtLobbyList.setText("");
//                            //txtServerResponse.setText(response.toString());
//                            //JSONArray jsonArray = response.getJSONArray(0);
//                            for (int i =0; i < response.length(); i++) {
//                                JSONObject object = response.getJSONObject(i);
//                                String name = object.getString("uname");
//                                int id = object.getInt("uid");
//
//                                txtLobbyList.append(id + ": " + name + "\n\n");
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
//        AppController.getInstance().addToRequestQueue(request, "json arrayrequest tag");
//
//    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device
                            mLastKnownLocation = task.getResult();
                            txtLocation.setText("UpdateLocation," + mLastKnownLocation.getLatitude()+","+mLastKnownLocation.getLongitude());
                            sendMessagesToSocket();
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exeption: %s", task.getException());
                        }
                    }
                });
            }
        } catch(SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
