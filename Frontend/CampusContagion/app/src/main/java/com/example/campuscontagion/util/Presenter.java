package com.example.campuscontagion.util;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.StringRequest;
import com.example.campuscontagion.interfaces.IPresenter;
import com.example.campuscontagion.interfaces.IView;

public class Presenter implements IPresenter {

    IView v;

    public Presenter (IView v) {
        this.v = v;
    }

    public void loadUserStats(int id) {
        final int uid = id;
        String url = Const.URL_ALL_USER_STATS;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("making JsonArrayReq", "test");
                            JSONObject object = response.getJSONObject(uid);

                            int powerUpsUsed = object.getInt("powerupsUsed");
                            int timesExtracted = object.getInt("timesExtracted");
                            int playersInfected = object.getInt("playersInfected");
                            int gamesPlayed = object.getInt("gamesPlayed");
                            String dateCreated = object.getString("dateCreated");
                            String lastLogin = object.getString("lastLogin");

                            int id = object.getInt("uid");

                            v.appendTextBox("Account created: " + dateCreated + "\n");
                            v.appendTextBox("Last login: " + lastLogin + "\n\n");
                            v.appendTextBox("Games Played: " + gamesPlayed + "\n");
                            v.appendTextBox("Times Extracted: " + timesExtracted + "\n");
                            v.appendTextBox("Players infected: " + playersInfected + "\n");
                            v.appendTextBox("Power-Ups used: " + powerUpsUsed + "\n");



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        AppController.getInstance().addToRequestQueue(request, "json arrayrequest tag");
    }



    public void changePW(int id) {
        String pWord = v.getPWBoxData();
        String url = Const.URL_CHANGE_PW + id + "/pword/" + pWord;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    v.replaceTextBox(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });
        Log.d("adding to request queue", "tag");
        AppController.getInstance().addToRequestQueue(request, "tag");
    }



     public void serverCheck(){
        v.replaceTextBox("Status: ");
        String url=Const.URL_SERVER_STATUS;
        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("server response", response);

                v.appendTextBox(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                v.replaceTextBox(error.toString());
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "string reqest");
    }

    public void registerUser() {

        String uname = v.getUserBoxData();
        String pword = v.getPWBoxData();

        String url = Const.URL_CREATE_USER+"uname="+uname+"&pword="+pword;
        Log.d("output is url", url);


        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        v.replaceTextBox("Server Response: " + response);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });
        Log.d("adding to request queue", "tag");
        AppController.getInstance().addToRequestQueue(request,"tag");


//    /createuser/?uname=<username>&pword=<password>
//    Creates a new user with an auto-generated UID and uses the specified uname and password
//    Returns the newly created user
//    Example: /createuser/?uname=Timmy&pword=tempPass
    }



     public void checkLoginCredentials() {


        // this is the value that gets passed to the next activity
        // ideally it will get overwritten by a successful call to the database
        // during the login process.
        String n = "Test Player";

        boolean checker = false;
        v.setLoginAccepted(false);
        final String username = v.getUserBoxData();
        final String password = v.getPWBoxData();

        String url = Const.URL_USER_LIST;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //JSONObject jsonObject = null;
                        Log.d("before the try", "blah");
                        try {
                            Log.d("just after the try", "blah");

                            for (int i = 0; i < response.length(); i++) {
                                Log.d("insdide the loop", "okay");
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("uname");
                                String pw = jsonObject.getString("password");
                                int id = jsonObject.getInt("uid");


                                Log.d("name", name);
                                Log.d("username", username);
                                if (name.equals(username) && pw.equals(password)) {
                                    //checker = true;
                                    Log.d("found match", password);
                                    v.setVerifiedUserID(id);
                                    v.goToTheLobby();

                                    //break;
                                    //debugging test


//                                    Intent intent = new Intent(MainActivity.this, UserLandingPageActivity.class);
//                                    intent.putExtra("playerName", name);
//                                    intent.putExtra("id", id);
//                                    startActivity(intent);

                                    //  to do here :
                                    //  implement code adding user ID to the lobby via volley post

                                } else {
//                                    v.setLoginAccepted(false;);
//
//                                    v.replaceTextBox("Login error.");
//                                    Log.d("why no message", "should login error");
                                }

                            }
                        } catch(JSONException e){
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });

        Log.d("adding to request queue", "tag");
        AppController.getInstance().addToRequestQueue(request,"tag");
        //return checker;
    }


    public void goToManageAccount() {

        final String username = v.getUserBoxData();
        final String password = v.getPWBoxData();

        String url = Const.URL_USER_LIST;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //JSONObject jsonObject = null;
                        Log.d("before the try", "blah");
                        try {
                            Log.d("just after the try", "blah");

                            for (int i = 0; i < response.length(); i++) {
                                //Log.d("insdide the loop", "okay");
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("uname");
                                String pw = jsonObject.getString("password");
                                int id = jsonObject.getInt("uid");


                                Log.d("name", name);
                                Log.d("username", username);
                                Log.d("i", "i");
                                if (name.equals(username) && pw.equals(password)) {
                                    v.setVerifiedUserID(id);
                                    v.goToManageAccount();
//                                    //debugging test
//                                    Log.d("call intent", name);
//
//                                    Intent intent = new Intent(MainActivity.this, ManageAccountActivity.class);
//                                    intent.putExtra("playerName", name);
//                                    intent.putExtra("id", id);
//                                    startActivity(intent);
                                }

                            }



                        } catch(JSONException e){
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });

        Log.d("adding to request queue", "tag");
        AppController.getInstance().addToRequestQueue(request,"tag");
    }

}
