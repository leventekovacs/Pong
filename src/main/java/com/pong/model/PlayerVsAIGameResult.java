package com.pong.model;

public class PlayerVsAIGameResult {

    private int rank;
    private String name;
    private String difficulty;
    private String result;
    private String date;
    private int point;

    public PlayerVsAIGameResult(int rank) {
        rank = 0;
        name = "";
        difficulty = "";
        result = "";
        date = "";
        point = 0;
    }

    public PlayerVsAIGameResult(int rank, String name, String difficulty, String result, String date, int point) {
        this.rank = rank;
        this.name = name;
        this.difficulty = difficulty;
        this.result = result;
        this.date = date;
        this.point = point;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getResult() {
        return result;
    }

    public String getDate() {
        return date;
    }

    public int getPoint() {
        return point;
    }
}
