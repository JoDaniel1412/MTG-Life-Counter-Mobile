package org.atlas.mtglifecounter.controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.game.Game;
import org.atlas.mtglifecounter.game.Player;
import org.atlas.mtglifecounter.graphics.ColorSelector;
import org.atlas.mtglifecounter.graphics.Colors;
import org.atlas.mtglifecounter.graphics.CommanderCounter;
import org.atlas.mtglifecounter.graphics.LifeCounter;
import org.atlas.mtglifecounter.util.Math;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    private Game game = Game.getInstance();
    private FrameLayout game_layout;
    private FrameLayout colors_layout;
    private FrameLayout commander_layout;
    private FrameLayout vanguard_layout;
    private Button commander_button;

    private int[] colors = Colors.colors;

    private Player playerColorSelector;
    private Player playerCommanderSelector;
    private HashMap<Player, CommanderCounter> currentCommanderMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        game_layout = findViewById(R.id.game_layout);
        colors_layout = findViewById(R.id.colors_layout);
        commander_layout = findViewById(R.id.commander_layout);
        vanguard_layout = findViewById(R.id.vanguard_layout);
        commander_button = findViewById(R.id.commander_button);
        loadGrid();
    }

    public void openColorLayout(Player player) {
        playerColorSelector = player;
        colors_layout.setVisibility(View.VISIBLE);
        loadColorsGrid();

        enabledGameLayout(false);
    }

    public void closeColorLayout(int color) {
        LifeCounter lifeCounter = game.getLifeCounters().get(playerColorSelector);
        assert lifeCounter != null;
        lifeCounter.setColor(color);
        lifeCounter.setBackgroundColor(color);

        colors_layout.setVisibility(View.INVISIBLE);
        enabledGameLayout(true);
    }

    public void openCommanderLayout(Player player) {
        playerCommanderSelector = player;
        commander_layout.removeAllViews();
        commander_layout.addView(commander_button);
        commander_layout.setVisibility(View.VISIBLE);
        loadCommanderGrid();
        enabledGameLayout(false);
    }

    public void closeCommanderLayout(View view) {
        // Updates the damage in the current Player
        HashMap<Player, Integer> map = playerCommanderSelector.getCommanderDamage();
        for (Map.Entry<Player, Integer> entry : map.entrySet()) {
            Player player = entry.getKey();
            for (Map.Entry<Player, CommanderCounter> entry2 : currentCommanderMap.entrySet()) {
                if (player.equals(entry2.getKey())){
                    int damage = entry2.getValue().getDamage();
                    entry.setValue(damage);
                }
            }
        }

        commander_layout.setVisibility(View.INVISIBLE);
        enabledGameLayout(true);
    }

    private void enabledGameLayout(boolean enabled) {
        for(Map.Entry<Player, LifeCounter> entry : game.getLifeCounters().entrySet()) {
            entry.getValue().setTouchable(enabled);
        }
    }

    private void loadGrid() {
        Display display = getWindowManager(). getDefaultDisplay();
        int size = game.getPlayers().size();
        int color = Math.getRandomNumberInRange(0, colors.length - 1);

        int columns = (int) java.lang.Math.sqrt(size);
        int rows = Math.ceilingDivision(size, columns);
        int width = display.getWidth();
        int height = display.getHeight();
        int xOffset = width / columns;
        int yOffset = height / rows;
        int x = 0;
        int y = 0;

        int p = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // Case the last rows wont be completed fill
                if (p == size - 1 && j == columns - 2) {
                    xOffset = width;
                    j++;
                }

                color %= Colors.colors.length;
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
                color++;
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

    private void loadCommanderGrid() {
        List<Player> playerList = game.getPlayers();
        int playerIndex = playerList.indexOf(playerCommanderSelector);
        HashMap<Player, Integer> commanderDamages = playerList.get(playerIndex).getCommanderDamage();
        currentCommanderMap = new HashMap<>();

        int size = commanderDamages.size();
        int columns = (int) java.lang.Math.sqrt(size);
        int rows = Math.ceilingDivision(size, columns);
        int width = commander_layout.getWidth();
        int height = commander_layout.getHeight();
        int xOffset = width / columns;
        int yOffset = (int) (height / rows * 0.9);
        float x = 0;
        float y = -100;

        int c = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (c > size) break;
                Player player = playerList.get(c);
                String name = player.getName();

                // In case the loop reaches the player in the commander selector
                if (player.equals(playerCommanderSelector)){
                    j--;
                    c++;
                    continue;
                }

                // Case the last rows wont be completed fill
                if (c == size - 1 && j == columns - 2) {
                    x += xOffset / 2;
                    j++;
                }

                int damage = commanderDamages.get(player);
                int color = game.getLifeCounters().get(player).getColor();

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(xOffset, yOffset);

                // Sets the names in a TextView
                CommanderCounter commanderCounter = new CommanderCounter(this);
                commanderCounter.setX(x);
                commanderCounter.setY(y);
                commanderCounter.setLayoutParams(params);
                commanderCounter.setName(name);
                commanderCounter.setDamage(damage);
                commanderCounter.setBackgroundColor(color);

                // Adds the view to the commander_layout
                currentCommanderMap.put(player, commanderCounter);
                commander_layout.addView(commanderCounter);
                x += xOffset;
                c++;
            }
            x = 0;
            y += yOffset;
        }
    }
}
