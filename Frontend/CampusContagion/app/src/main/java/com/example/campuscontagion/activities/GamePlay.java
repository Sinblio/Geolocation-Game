package com.example.campuscontagion.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

// Map imports
import com.example.campuscontagion.util.GamePlayWorker;
import com.example.campuscontagion.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


/**
 * Used to control the map view and handle location permissions.
 * @author Jay Maxwell & Mitchell Bratina
 */
public class GamePlay extends FragmentActivity implements OnMapReadyCallback {

    GamePlayWorker worker = new GamePlayWorker();

    private static final String TAG = GamePlay.class.getSimpleName();
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
        if (worker.mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, worker.mMap.getCameraPosition());
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
        worker.mMap = googleMap;
        worker.mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));

        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();

        worker.makePlayerLocationRequest();

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
        if (worker.mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                worker.mMap.setMyLocationEnabled(true);
                worker.mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                worker.mMap.setMyLocationEnabled(false);
                worker.mMap.getUiSettings().setMyLocationButtonEnabled(false);
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
                            worker.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exeption: %s", task.getException());
                            worker.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            worker.mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

}
