package org.atlas.mtglifecounter.controllers;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.graphics.Colors;
import org.atlas.mtglifecounter.graphics.LifeCounter;
import org.atlas.mtglifecounter.logic.Players;
import org.atlas.mtglifecounter.util.Math;

public class GameActivity extends AppCompatActivity {

    public static int currentPlayerSettings;
    private FrameLayout game_layout;
    private int[] colors = Colors.colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        game_layout = findViewById(R.id.game_layout);
        loadGrid();
    }

    public void pressedColorSettings(View view, int player) {
        GameActivity.currentPlayerSettings = player;
        Intent animation = new Intent(this, ColorSettingsActivity.class);
        startActivity(animation);
    }

    private void loadGrid() {
        Display display = getWindowManager(). getDefaultDisplay();
        int players_selected = PlayerSelectionActivity.players_selected;

        int columns = (int) java.lang.Math.sqrt(players_selected);
        int rows = Math.ceilingDivision(players_selected, columns);
        int width = display.getWidth();
        int height = display.getHeight();
        int xOffset = width / columns;
        int yOffset = height / rows;
        int x = 0;
        int y = 0;

        int p = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int color = Math.getRandomNumberInRange(0, colors.length - 1);

                // Sets the Life Counter Views
                LifeCounter lifeCounter = new LifeCounter(this);
                lifeCounter.setX(x);
                lifeCounter.setY(y);
                lifeCounter.setPlayer(p);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(xOffset, yOffset);
                lifeCounter.setLayoutParams(params);
                if (Players.colors.size() <= p) {
                    Players.colors.add(p, colors[color]);
                }
                lifeCounter.setBackgroundColor(Players.colors.get(p));

                // Load the Life Counter
                game_layout.addView(lifeCounter);

                x += xOffset;
                p++;
            }
            x = 0;
            y += yOffset;
        }
    }
}
