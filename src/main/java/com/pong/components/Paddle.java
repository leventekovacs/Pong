package com.pong.components;

import com.pong.constants.Constants;

public class Paddle {
    private final String color;
    private double x;
    private double y;
    private final double width;
    private final double height;
    private final double arcHeight;

    public Paddle() {
        color = Constants.PADDLE_COLOR;
        x = Constants.SINGLE_PLAYER_GAME_WIDTH / 2 - Constants.SINGLE_PLAYER_PADDLE_WIDTH / 2;
        y = Constants.SINGLE_PLAYER_GAME_PADDLE_Y_POSITION;
        width = Constants.SINGLE_PLAYER_PADDLE_WIDTH;
        height = Constants.SINGLE_PLAYER_PADDLE_HEIGHT;
        arcHeight = Constants.PADDLE_ARCH_HEIGHT;
    }

    public Paddle(PaddleSide ps){
        color = Constants.PADDLE_COLOR;
        width = Constants.MULTIPLAYER_PADDLE_WIDTH;
        height = Constants.MULTIPLAYER_PADDLE_HEIGHT;
        arcHeight = Constants.PADDLE_ARCH_HEIGHT;
        if(ps == PaddleSide.LEFT) {
            x = 0;
            y = Constants.MULTIPLAYER_GAME_HEIGHT / 2 - Constants.MULTIPLAYER_PADDLE_HEIGHT / 2;
        }
        if(ps == PaddleSide.RIGHT){
            x = Constants.MULTIPLAYER_GAME_RIGHT_PADDLE_X_POSITION;
            y = Constants.MULTIPLAYER_GAME_HEIGHT / 2 - Constants.MULTIPLAYER_PADDLE_HEIGHT / 2;
        }
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

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getArcHeight() {
        return arcHeight;
    }
}
