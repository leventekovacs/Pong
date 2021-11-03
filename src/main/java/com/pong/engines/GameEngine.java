package com.pong.engines;

import com.pong.components.Ball;
import com.pong.constants.GameMode;
import com.pong.components.Paddle;
import com.pong.constants.Constants;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public abstract class GameEngine {
    protected GameMode gm;

    protected boolean gameStarted;

    protected GraphicsContext gc;

    protected Ball ball;
    protected Paddle paddle;
    protected Paddle paddleLeft;
    protected Paddle paddleRight;

    protected Timeline tl;

    protected void drawOpeningText(GraphicsContext gc) {
        gc.setStroke(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(32));

        if(gm == GameMode.SINGLE_PLAYER) {
            gc.strokeText("Click to start the game", Constants.SINGLE_PLAYER_GAME_WIDTH / 2, Constants.SINGLE_PLAYER_GAME_HEIGHT / 2.5);
        }
        if(gm == GameMode.MULTIPLAYER) {
            gc.strokeText("Click to start the game", Constants.MULTIPLAYER_GAME_WIDTH / 2, Constants.MULTIPLAYER_GAME_HEIGHT / 2.5);
        }
    }

    protected void drawBackGround(GraphicsContext gc) {
        if(gm == GameMode.SINGLE_PLAYER) {
            gc.setFill(Color.valueOf(Constants.BACKGROUND_COLOR));
            gc.fillRect(0,0,Constants.SINGLE_PLAYER_GAME_WIDTH,Constants.SINGLE_PLAYER_GAME_HEIGHT);
            gc.setFill(Color.valueOf(Constants.BACKGROUND_COLOR_2));
            gc.fillRect(0,Constants.SINGLE_PLAYER_GAME_PADDLE_Y_POSITION,Constants.SINGLE_PLAYER_GAME_WIDTH,(Constants.SINGLE_PLAYER_GAME_HEIGHT - Constants.SINGLE_PLAYER_GAME_PADDLE_Y_POSITION));
        }
        if(gm == GameMode.MULTIPLAYER) {
            gc.setFill(Color.valueOf(Constants.BACKGROUND_COLOR));
            gc.fillRect(0,0,Constants.MULTIPLAYER_GAME_WIDTH,Constants.MULTIPLAYER_GAME_HEIGHT);
            gc.setFill(Color.valueOf(Constants.BACKGROUND_COLOR_2));
            gc.fillRect(0,0,Constants.MULTIPLAYER_PADDLE_WIDTH,Constants.MULTIPLAYER_GAME_HEIGHT);
            gc.setFill(Color.valueOf(Constants.BACKGROUND_COLOR_2));
            gc.fillRect(Constants.MULTIPLAYER_GAME_RIGHT_PADDLE_X_POSITION,0,Constants.MULTIPLAYER_PADDLE_WIDTH ,Constants.MULTIPLAYER_GAME_HEIGHT);
        }
    }

    protected void drawBall(GraphicsContext gc, Ball ball) {
        gc.setFill(Color.valueOf(ball.getColor()));
        gc.fillOval(ball.getX(),ball.getY(), ball.getD(), ball.getD());
    }

    protected void drawPaddle(GraphicsContext gc, Paddle paddle) {
        gc.setFill(Color.valueOf(paddle.getColor()));
        gc.fillRoundRect(paddle.getX(), paddle.getY(),paddle.getWidth(), paddle.getHeight(), paddle.getArcHeight(), paddle.getArcHeight());
    }

    protected void drawPaddles(GraphicsContext gc, Paddle paddleLeft, Paddle paddleRight) {
        gc.setFill(Color.valueOf(paddleLeft.getColor()));
        gc.fillRoundRect(paddleLeft.getX(), paddleLeft.getY(),paddleLeft.getWidth(), paddleLeft.getHeight(), paddleLeft.getArcHeight(), paddleLeft.getArcHeight());
        gc.setFill(Color.valueOf(paddleRight.getColor()));
        gc.fillRoundRect(paddleRight.getX(), paddleRight.getY(),paddleRight.getWidth(), paddleRight.getHeight(), paddleRight.getArcHeight(), paddleRight.getArcHeight());
    }

    protected abstract void collision(Ball ball, Paddle paddle, Paddle paddle2);

    protected abstract boolean ballOutside(Ball ball);

    protected abstract void gameOver();
}
