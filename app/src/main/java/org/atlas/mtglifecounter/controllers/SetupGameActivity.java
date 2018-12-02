package org.atlas.mtglifecounter.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import org.atlas.mtglifecounter.R;

public class SetupGameActivity extends AppCompatActivity {

    public static boolean commander_active;
    public static int starting_life;
    private Switch commander_switch;
    private EditText starting_life_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_game);

        commander_switch = findViewById(R.id.commander_switch);
        starting_life_entry = findViewById(R.id.starting_life_entry);
    }

    public void pressed_play(View view) {
        commander_active = commander_switch.isChecked();
        starting_life = Integer.parseInt(starting_life_entry.getText().toString());

        Log.i("SetupGameAttributes", "Starting Life: " + starting_life);
        Log.i("SetupGameAttributes", "Commander enable: " + commander_active);

        Intent animation = new Intent(this, GameActivity.class);
        startActivity(animation);
    }
}
