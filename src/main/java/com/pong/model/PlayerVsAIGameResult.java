package com.pong.model;

public class PlayerVsAIGameResult {

    private final int rank;
    private final String name;
    private final String difficulty;
    private final String result;
    private final String date;
    private final int point;

    public PlayerVsAIGameResult() {
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
