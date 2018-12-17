package org.atlas.mtglifecounter.controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.game.Game;
import org.atlas.mtglifecounter.game.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerSelectionActivity extends AppCompatActivity {

    private int players_selected = 1;
    private List<ImageView> player_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player_selection);

        loadPlayerList();
    }

    public void pressed_continue(View view) {
        Log.i("PlayerSelection", "Players selected: " + players_selected);
        // Creates the players and add them to Game
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < players_selected; i++) {
            players.add(new Player());
        }
        Game.getInstance().setPlayers(players);

        Intent animation = new Intent(this, NamesActivity.class);
        startActivity(animation);
    }

    @SuppressLint("FindViewByIdCast")
    private void loadPlayerList() {
        // Adds the players icons to the list
        player_list.add(findViewById(R.id.p1));
        player_list.add(findViewById(R.id.p2));
        player_list.add(findViewById(R.id.p3));
        player_list.add(findViewById(R.id.p4));
        player_list.add(findViewById(R.id.p5));
        player_list.add(findViewById(R.id.p6));

        // Sets the images alpha and event
        for (final ImageView player : player_list) {
            player.setAlpha(50);

            player.setOnClickListener(v -> {
                players_selected = player_list.indexOf(player) + 1;
                setPlayersAlphaOnClick(players_selected);
            });
        }
        player_list.get(0).setAlpha(255);
    }

    private void setPlayersAlphaOnClick(int k) {
        int i;
        for (i = 0; i < k; i++) {
            player_list.get(i).setAlpha(255);
        }

        for (int j = i; j < player_list.size(); j++) {
            player_list.get(j).setAlpha(50);
        }
    }

}
