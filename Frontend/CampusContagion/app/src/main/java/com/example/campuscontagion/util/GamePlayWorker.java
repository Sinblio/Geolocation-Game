package com.example.campuscontagion.util;

import android.graphics.Color;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.campuscontagion.interfaces.ILocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Worker that does the background grunt work for the GamePlay class
 * @author Jay Maxwell & Mitchell Bratina
 */
public class GamePlayWorker implements ILocationRequest {

    // Holds all the players' location info to be drawn on the map
    public Map<String, double[]> playersInfo = new HashMap<>();

    // GoogleMap object that will be presented in the GamePlay class
    public GoogleMap mMap;

    // Websocket client to communicate with the websocket
    public WebSocketClient socketClient;

    /**
     * Makes a player location request from the server and receives a JSON array which it
     * then parses into individual player latitude and longitude pairs. The pairs are then
     * used to draw circles on the map that will be displayed to the player
     */
    public void makePlayerLocationRequest() {
        String url = Const.URL_USER_LOCS;
        Log.d("inside request method", url);
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

                                Circle circle = mMap.addCircle(new CircleOptions()
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

    /**
     * First of two methods to handle player location requests when using a
     * web socket. This handles individual player location "packets" by
     * splitting the latitude and longitude apart by a space and then using
     * the pair to draw a circle on the map that will be displayed to the
     * player in the GamePlay class.
     * @return true if no exceptions, false otherwise
     */
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

                    Circle circle = mMap.addCircle(new CircleOptions()
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

    /**
     * Second of two methods to handle player location requests when using
     * a web socket. This handles a large "packet" of all player location
     * latitude, longitude pairs separated by commas. After parsing the pairs,
     * it will use them to draw circles on the map that will be displayed to
     * the player in the GamePlay class.
     * @return true if no exception, false otherwise
     */
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

                    int split_length = split_location.length;

                    for (int i = 0; i < split_length; i += 2) {
                        String[] location_pair = split_location[i].split(",");
                        double latitude = Double.valueOf((location_pair[0]));
                        double longitude = Double.valueOf((location_pair[1]));

                        Circle circle = mMap.addCircle(new CircleOptions()
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

    /**
     * This is a simple test location request that can be used if
     * the hashmap is used to draw the circles in the GamePlay class,
     * but this is now somewhat depreciated. Left in for testing.
     */
    public void makePlayerLocationRequestTest() {

        double[] first = {42.03, -93.647};
        playersInfo.put("john", first);

        double[] second = {42.0273, -93.649};
        playersInfo.put("tom", second);
    }
}
