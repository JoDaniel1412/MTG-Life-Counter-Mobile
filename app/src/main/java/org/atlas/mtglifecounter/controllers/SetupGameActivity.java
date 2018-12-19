package org.atlas.mtglifecounter.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Switch;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.game.Game;
import org.atlas.mtglifecounter.game.Player;

import java.util.List;

public class SetupGameActivity extends AppCompatActivity {

    private Game game = Game.getInstance();
    private EditText starting_life_entry;
    private Switch commander_switch;
    private Switch vanguard_switch;
    private Switch display_players_name_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setup_game);

        starting_life_entry = findViewById(R.id.starting_life_entry);
        commander_switch = findViewById(R.id.commander_switch);
        vanguard_switch = findViewById(R.id.vanguard_switch);
        display_players_name_switch = findViewById(R.id.display_players_name_switch);

        // Disables commander
        if (game.getPlayers().size() == 1) commander_switch.setEnabled(false);
    }

    public void pressed_play(View view) {
        Game game = Game.getInstance();
        game.setStartingLife(Integer.parseInt(starting_life_entry.getText().toString()));
        game.setCommander(commander_switch.isChecked());
        game.setVanguard(vanguard_switch.isChecked());
        game.setPlayerNamesDisplayed(display_players_name_switch.isChecked());

        Log.i("SetupGameAttributes", "Starting Life: " + game.getStartingLife());
        Log.i("SetupGameAttributes", "Commander enable: " + game.isCommander());
        Log.i("SetupGameAttributes", "Vanguard enable: " + game.isVanguard());
        Log.i("SetupGameAttributes", "Players Name enable: " + game.isPlayerNamesDisplayed());

        // Sets the life for each Player
        List<Player> players = game.getPlayers();
        for (Player player : players) {
            player.setLife(game.getStartingLife());
        }

        Intent animation = new Intent(this, GameActivity.class);
        startActivity(animation);
    }
}
