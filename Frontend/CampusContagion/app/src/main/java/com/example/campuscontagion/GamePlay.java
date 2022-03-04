package com.example.campuscontagion;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

// Map imports
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

//json stuff
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Used to control the map view and handle location permissions.
 * @author Jay Maxwell & Mitchell Bratina
 */
// volley imports

// gson imports

// json imports

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import com.example.campuscontagion.util.AppController;
import com.example.campuscontagion.util.Const;


public class GamePlay extends FragmentActivity implements OnMapReadyCallback {

    Map<String, double[]> playersInfo = new HashMap<>();

    private static final String TAG = GamePlay.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;

    //The entry point to the Fused Location Provider
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Ames, Iowa) and default zoom to use when location permission is not granted
    private final LatLng mDefaultLocation = new LatLng(42.028384, -93.650837);
    private static final int DEFAULT_ZOOM = 17;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    /**
     * @param savedInstanceState : The intance of the current view.
     */
    // Websocket Stuff
    WebSocketClient socketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        setContentView(R.layout.activity_game_play);

        // Construct a FusedLocationProviderClient
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * @param outState : The location of the device on the map.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    /**
     * Manipulates the map once available.
     */
    /**
     * @param googleMap : The map seen in the view.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));

        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();

        makePlayerLocationRequest();

    }

    /**
     * Ask for user's location access.
     */
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


    /**
     * @param requestCode : The request code for location permissions.
     * @param permissions : The permissions from the device.
     * @param grantResults : The resulted permissions from the request.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    /**
     * Update the user's location on the view.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Get the location of the device to then update and display on the view.
     */
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
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exeption: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void makePlayerLocationRequestTest() {

        double[] first = {42.03, -93.647};
        playersInfo.put("john", first);

        double[] second = {42.0273, -93.649};
        playersInfo.put("tom", second);
    }

    public boolean makePlayerLocationRequestSocket() {
        Draft[] drafts = {new Draft_6455()};

        String w = Const.URL_SOCKETS;

        try {
            socketClient = new WebSocketClient(new URI(w), drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("OPEN", "Client is connecting.");
                }

                @Override
                public void onMessage(String location) {
                    Log.d("LOC","Received a player location.");

                    String[] split_location = location.split(" ");

                    double latitude = Double.valueOf(split_location[0]);
                    double longitude = Double.valueOf(split_location[1]);

                    Circle circle3 = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(latitude, longitude))
                            .radius(1)
                            .strokeColor(Color.RED)
                            .fillColor(Color.RED));
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    Log.d("CLOSE", "Closing connection.");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean makePlayerLocationRequestSocketTwo() {
        Draft[] drafts = {new Draft_6455()};

        String w = Const.URL_SOCKETS;

        try {
            socketClient = new WebSocketClient(new URI(w), drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("OPEN", "Client is connecting.");
                }

                @Override
                public void onMessage(String location) {
                    Log.d("LOC","Received a player location.");

                    String[] split_location = location.split(" ");


                    //////NEED to check on this

                    int split_length = split_location.length;

                    for (int i = 0; i < split_length; i += 2) {
                        String[] location_pair = split_location[i].split(",");
                        double latitude = Double.valueOf((location_pair[0]));
                        double longitude = Double.valueOf((location_pair[1]));

                        Circle drawcircle = mMap.addCircle(new CircleOptions()
                                .center(new LatLng(latitude, longitude))
                                .radius(1)
                                .strokeColor(Color.RED)
                                .fillColor(Color.RED));
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    Log.d("CLOSE", "Closing connection.");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void makePlayerLocationRequest() {
        String url = Const.URL_USER_LOCS;
        Log.d("inside request methaod", url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("player");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject players = jsonArray.getJSONObject(i);

                                // extract data from player
                                String name = players.getString("name");
                                int id = players.getInt("id");
                                double latitude = players.getDouble("location_lat");
                                double longitude = players.getDouble("location_long");

                                double[] location = {latitude, longitude};
                                playersInfo.put(name, location);

                                Log.v("PLAYERLOC", "Player name: " + name + " Lat: " + location[0] + " Long:" + location[1]);

                                Circle circle2 = mMap.addCircle(new CircleOptions()
                                    .center(new LatLng(latitude, longitude))
                                    .radius(1)
                                    .strokeColor(Color.RED)
                                    .fillColor(Color.RED));


                            }
                        } catch (JSONException e) {
                            Log.v("PLAYERLOC", "The json request did not work");
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Log.v("PLAYERLOC", "Hit the end of the stuff.");

        // add request to the volley request queue
        AppController.getInstance().addToRequestQueue(request, "json arrayrequest tag");
    }

}
