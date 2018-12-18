package org.atlas.mtglifecounter.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.game.Game;
import org.atlas.mtglifecounter.game.Player;

import java.util.ArrayList;
import java.util.List;

public class NamesActivity extends AppCompatActivity {

    Game game = Game.getInstance();
    EditText p1;
    EditText p2;
    EditText p3;
    EditText p4;
    EditText p5;
    EditText p6;
    List<EditText> names = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_names);

        p1 = findViewById(R.id.player_name_1);
        p2 = findViewById(R.id.player_name_2);
        p3 = findViewById(R.id.player_name_3);
        p4 = findViewById(R.id.player_name_4);
        p5 = findViewById(R.id.player_name_5);
        p6 = findViewById(R.id.player_name_6);

        names.add(p1);
        names.add(p2);
        names.add(p3);
        names.add(p4);
        names.add(p5);
        names.add(p6);
    }

    public void pressed_continue(View view) {
        // Sets the name for each player
        int i = 0;
        for (Player player : game.getPlayers()) {
            String name = names.get(i).getText().toString();
            player.setName(name);
            i++;
        }

        Intent animation = new Intent(this, SetupGameActivity.class);
        startActivity(animation);
    }
}
