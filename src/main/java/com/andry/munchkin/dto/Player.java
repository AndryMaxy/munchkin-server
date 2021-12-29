package com.andry.munchkin.dto;

import com.fasterxml.jackson.annotation.JsonView;

public class Player {

    @JsonView(Views.Full.class)
    private String name;

    @JsonView(Views.Full.class)
    private int level = 1;

    @JsonView(Views.Full.class)
    private int bonus;

    @JsonView(Views.Full.class)
    private int sum;

    @JsonView(Views.Full.class)
    private String sex;

    public Player(String name, String sex) {
        this.name = name;
        this.sex = sex;
        updateSum();
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level < 1) return;

        this.level = level;
        updateSum();
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        if (bonus < 0) return;

        this.bonus = bonus;
        updateSum();
    }

    public int getSum() {
        return sum;
    }

    public void updateSum() {
        this.sum = level + bonus;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
