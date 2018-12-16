package org.atlas.mtglifecounter.game;

import java.util.HashMap;

public class Player {

    private String name;
    private int life;
    private int poison;
    private HashMap<Player, Integer> commanderDamage;

    public Player() {
        name = "unknown";
        life = 20;
        poison = 0;
        commanderDamage = new HashMap<>();
    }

    public Player(String name, int life, int poison, HashMap<Player, Integer> commanderDamage) {
        this.name = name;
        this.life = life;
        this.poison = poison;
        this.commanderDamage = commanderDamage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getPoison() {
        return poison;
    }

    public void setPoison(int poison) {
        this.poison = poison;
    }

    public HashMap<Player, Integer> getCommanderDamage() {
        return commanderDamage;
    }

    public void setCommanderDamage(HashMap<Player, Integer> commanderDamage) {
        this.commanderDamage = commanderDamage;
    }
}
