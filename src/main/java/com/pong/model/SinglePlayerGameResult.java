package com.pong.model;

public class SinglePlayerGameResult {

    private final int rank;
    private final String name;
    private final String time;
    private final int hit;
    private final String date;

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
