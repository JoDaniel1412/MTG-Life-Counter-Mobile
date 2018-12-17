package org.atlas.mtglifecounter.controllers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.game.Game;
import org.atlas.mtglifecounter.game.Player;
import org.atlas.mtglifecounter.graphics.ColorSelector;
import org.atlas.mtglifecounter.graphics.Colors;
import org.atlas.mtglifecounter.graphics.LifeCounter;
import org.atlas.mtglifecounter.util.Math;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    private Game game = Game.getInstance();
    private FrameLayout game_layout;
    private FrameLayout colors_layout;
    private int[] colors = Colors.colors;
    private Player playerColorSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        game_layout = findViewById(R.id.game_layout);
        colors_layout = findViewById(R.id.colors_layout);
        loadGrid();
    }

    public void openColorSelector(Player player) {
        colors_layout.setBackgroundColor(Color.WHITE);
        playerColorSelector = player;
        loadColorsGrid();
        colors_layout.setVisibility(View.VISIBLE);
    }

    public void closeColorSelector(int color) {
        LifeCounter lifeCounter = game.getLifeCounters().get(playerColorSelector);
        assert lifeCounter != null;
        lifeCounter.setColor(color);
        lifeCounter.setBackgroundColor(color);

        colors_layout.setVisibility(View.INVISIBLE);
    }

    private void loadGrid() {
        Display display = getWindowManager(). getDefaultDisplay();
        int players_selected = game.getPlayers().size();

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
                Player player = game.getPlayers().get(p);

                // Sets the Life Counter Views
                LifeCounter lifeCounter = new LifeCounter(this);
                lifeCounter.setGameActivity(this);
                lifeCounter.setX(x);
                lifeCounter.setY(y);
                lifeCounter.setPlayer(player);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(xOffset, yOffset);
                lifeCounter.setLayoutParams(params);
                lifeCounter.setColor(Colors.colors[color]);
                lifeCounter.setBackgroundColor(Colors.colors[color]);

                // Load the Life Counter
                game.getLifeCounters().put(player, lifeCounter);
                game_layout.addView(lifeCounter);

                x += xOffset;
                p++;
            }
            x = 0;
            y += yOffset;
        }
    }

    private void loadColorsGrid() {
        int size = Colors.colors.length;

        int columns = (int) java.lang.Math.sqrt(size);
        int rows = Math.ceilingDivision(size, columns);
        int width = colors_layout.getWidth();
        int height = colors_layout.getHeight();
        int xOffset = width / (columns + 1);
        int yOffset = height / (rows + 1);
        float x = xOffset / 2;
        float y = yOffset / 2;

        int c = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (c >= size) break;

                // Sets the ColorSelector Views
                ColorSelector colorSelector = new ColorSelector(this);
                colorSelector.setGameActivity(this);
                colorSelector.setX(x);
                colorSelector.setY(y);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(xOffset, yOffset);
                colorSelector.setLayoutParams(params);
                colorSelector.setColor(Colors.colors[c]);
                colorSelector.setBackgroundColor(Colors.colors[c]);

                // Load the ColorSelector
                colors_layout.addView(colorSelector);

                x += xOffset;
                c++;
            }
            x = xOffset / 2;
            y += yOffset;
        }
    }
}
