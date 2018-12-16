package org.atlas.mtglifecounter.game;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static Game instance = null;
    private List<Player> players = new ArrayList<>();
    private boolean commander = false;
    private boolean vanguard = false;
    private int startingLife = 20;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public static Game getInstance(List<Player> players, boolean commander, boolean vanguard, int startingLife) {
        if (instance == null) {
            instance = new Game(players, commander,vanguard, startingLife);
        }
        return instance;
    }

    private Game() {
    }

    private Game(List<Player> players, boolean commander, boolean vanguard, int startingLife) {
        this.players = players;
        this.commander = commander;
        this.vanguard = vanguard;
        this.startingLife = startingLife;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
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

    public int getStartingLife() {
        return startingLife;
    }

    public void setStartingLife(int startingLife) {
        this.startingLife = startingLife;
    }
}
