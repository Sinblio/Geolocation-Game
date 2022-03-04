package com.example.campuscontagion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.campuscontagion.util.Const;
import com.example.campuscontagion.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class TestChatActivity extends AppCompatActivity {

    Button btnConnect;
    Button btnSend;
    EditText txtName;
    EditText txtMessage;
    TextView txtConsole;
    String playerName;
    int playerID;

    WebSocketClient socketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sockets_test);



        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");
        playerID = intent.getIntExtra("id",0);


       // btnConnect=findViewById(R.id.btnConnect);
        btnSend=findViewById(R.id.btnSend);
       // txtName=findViewById(R.id.txtName);
        txtMessage=findViewById(R.id.txtSocketMessage);
        txtConsole=findViewById(R.id.txtConsole);




        Draft[] drafts = {new Draft_6455()};

        String w = Const.URL_TEST_SOCKETS+playerName;
        // String w = "ws://10.26.13.93:8080/websocket/" + txtName.getText().toString();
        try {
            socketClient = new WebSocketClient(new URI(w), drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                    String s=txtConsole.getText().toString();

                    txtConsole.setText("onOpen: is connecting \n" + s);

                }

                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    String s=txtConsole.getText().toString();
                    //t1.setText("hello world");
                    //Log.d("first", "run() returned: " + s);
                    //s=t1.getText().toString();
                    //Log.d("second", "run() returned: " + s);
                    txtConsole.setText(message +'\n' + s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    Log.d("CLOSE", "onClose() returned: " + s);

                }

                @Override
                public void onError(Exception e) {
                    //txtConsole.setText(" error:" + e.toString());
                    e.printStackTrace();
                    //String s=txtConsole.getText().toString();

                    //txtConsole.setText("onEror: " + e.toString()+ " \n" + s +"\n");
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        socketClient.connect();




//        btnConnect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//            }
//        });



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    socketClient.send(txtMessage.getText().toString());
                    //socketClient.send("bob");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
