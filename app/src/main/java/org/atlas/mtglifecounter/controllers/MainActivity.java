package org.atlas.mtglifecounter.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.atlas.mtglifecounter.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pressed_start(View view) {
        Intent animation = new Intent(this, PlayerSelectionActivity.class);
        startActivity(animation);
    }
}
