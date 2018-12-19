package org.atlas.mtglifecounter.game;

import org.atlas.mtglifecounter.graphics.LifeCounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {

    private static Game instance = null;
    private List<Player> players = new ArrayList<>();
    private HashMap<Player, LifeCounter> lifeCounters = new HashMap<>();
    private boolean commander = false;
    private boolean vanguard = false;
    private boolean playerNamesDisplayed;
    private int startingLife = 20;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public static Game getInstance(List<Player> players, boolean commander, boolean vanguard, boolean playerNamesDisplayed, int startingLife) {
        if (instance == null) {
            instance = new Game(players, commander,vanguard, playerNamesDisplayed, startingLife);
        }
        return instance;
    }

    private Game() {
    }

    private Game(List<Player> players, boolean commander, boolean vanguard, boolean playerNamesDisplayed, int startingLife) {
        this.players = players;
        this.commander = commander;
        this.vanguard = vanguard;
        this.playerNamesDisplayed = playerNamesDisplayed;
        this.startingLife = startingLife;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public HashMap<Player, LifeCounter> getLifeCounters() {
        return lifeCounters;
    }

    public void setLifeCounters(HashMap<Player, LifeCounter> lifeCounters) {
        this.lifeCounters = lifeCounters;
    }

    public boolean isCommander() {
        return commander;
    }

    public void setCommander(boolean commander) {
        this.commander = commander;
    }

    public boolean isVanguard() {
        return vanguard;
    }

    public void setVanguard(boolean vanguard) {
        this.vanguard = vanguard;
    }

    public boolean isPlayerNamesDisplayed() {
        return playerNamesDisplayed;
    }

    public void setPlayerNamesDisplayed(boolean playerNamesDisplayed) {
        this.playerNamesDisplayed = playerNamesDisplayed;
    }

    public int getStartingLife() {
        return startingLife;
    }

    public void setStartingLife(int startingLife) {
        this.startingLife = startingLife;
    }
}
