package org.atlas.mtglifecounter.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.util.Math;

public class GameActivity extends AppCompatActivity {

    private FrameLayout game_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game_layout = findViewById(R.id.game_layout);
    }

    private void loadGrid() {
        int life = SetupGameActivity.starting_life;
        boolean commander = SetupGameActivity.commander_active;

        int columns = (int) java.lang.Math.sqrt(PlayerSelectionActivity.players_selected);
        int rows = Math.divideApproach(PlayerSelectionActivity.players_selected, columns);
        int xOffset = game_layout.getWidth() / columns;
        int yOffset = game_layout.getHeight() / rows;
        int x = 0;
        int y = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //createLifeBox(x, y, life, commander)
                x += xOffset;
            }
            x = 0;
            y += yOffset;
        }
    }
}
