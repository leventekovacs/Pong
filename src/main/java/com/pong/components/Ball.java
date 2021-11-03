package com.pong.components;


import com.pong.constants.Constants;
import com.pong.constants.GameMode;

import java.util.concurrent.ThreadLocalRandom;


public class Ball {

    private final String color;
    private double x;
    private double y;
    private final double d;
    private double velX;
    private double velY;

    public Ball(GameMode gm) {
        color = Constants.BALL_COLOR;
        d = Constants.BALL_DIAMETER;

        if(gm == GameMode.SINGLE_PLAYER){
            x = Constants.SINGLE_PLAYER_GAME_WIDTH / 2;
            y = Constants.SINGLE_PLAYER_GAME_HEIGHT / 2;
            velX = ThreadLocalRandom.current().nextDouble(-1, 1);
            velY = ThreadLocalRandom.current().nextDouble(-1, 1) <= 0 ? -1 : 1;
        } else {
            x = Constants.MULTIPLAYER_GAME_WIDTH / 2;
            y = Constants.MULTIPLAYER_GAME_HEIGHT / 2;
            velX = ThreadLocalRandom.current().nextDouble(-1, 1) <= 0 ? -1 : 1;
            velY = ThreadLocalRandom.current().nextDouble(-1, 1);
        }
    }

    public void moveBall() {
        x += velX;
        y += velY;
    }

    public String getColor() {
        return color;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getD() {
        return d;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double speedY) {
        this.velY = speedY;
    }
}
