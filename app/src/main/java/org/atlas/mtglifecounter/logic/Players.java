package org.atlas.mtglifecounter.logic;

import org.atlas.mtglifecounter.graphics.LifeCounter;

import java.util.ArrayList;
import java.util.List;

public class Players {

    private static List<LifeCounter> players_life = new ArrayList<>();

    public static List<LifeCounter> getPlayers_life() {
        return players_life;
    }

    public static void setPlayers_life(List<LifeCounter> players_life) {
        Players.players_life = players_life;
    }
}
