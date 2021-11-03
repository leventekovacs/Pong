package com.pong.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SinglePlayerGameResult {

    private int rank;
    private String name;
    private String time;
    private int hit;
    private String date;

    public SinglePlayerGameResult() {
        rank = 0;
        name = "";
        time = "";
        hit = 0;
        date = "";
    }

    public SinglePlayerGameResult(int rank, String name, String time, int hit, String date) {
        this.rank = rank;
        this.name =  name;
        this.time = time;
        this.hit = hit;
        this.date = date;
    }

    public int getRank() { return rank; }
    public String getName() {
        return name;
    }
    public String getTime() {
        return time;
    }
    public int getHit() {
        return hit;
    }
    public String getDate() {
        return date;
    }
}
