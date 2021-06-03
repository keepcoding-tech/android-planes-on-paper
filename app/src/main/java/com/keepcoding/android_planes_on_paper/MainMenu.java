package com.keepcoding.android_planes_on_paper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainMenu extends Activity {
    // declaring layout components
    private Button btn_privateGame;
    private Button btn_randomGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // initializing layout components
        btn_randomGame = findViewById(R.id.btn_randomGame);
        btn_privateGame = findViewById(R.id.btn_privateGame);

        Intent randomIntent = new Intent(MainMenu.this, RandomRoomMenu.class);
        Intent privateIntent = new Intent(MainMenu.this, PrivateRoomMenu.class);

        btn_randomGame.setOnClickListener(view -> startActivity(randomIntent));
        btn_privateGame.setOnClickListener(view -> startActivity(privateIntent));
    }
}