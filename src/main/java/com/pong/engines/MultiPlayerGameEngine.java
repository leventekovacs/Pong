package com.pong.engines;

import com.pong.GameManager;
import com.pong.components.Ball;
import com.pong.components.PaddleSide;
import com.pong.constants.GameMode;
import com.pong.components.Paddle;
import com.pong.constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public abstract class MultiPlayerGameEngine extends GameEngine {

    @FXML
    protected Canvas canvas;

    @FXML
    protected AnchorPane gameOverPane;

    @FXML
    protected Label resultLabel;

    protected int player1Scores, player2Scores;

    public MultiPlayerGameEngine() {
        gm = GameMode.MULTIPLAYER;
        gameStarted = false;

        player1Scores = 0;
        player2Scores = 0;

        ball = new Ball(GameMode.MULTIPLAYER);

        paddleLeft = new Paddle(PaddleSide.LEFT);
        paddleRight = new Paddle(PaddleSide.RIGHT);

    }

    protected void drawScores(GraphicsContext gc) {
        gc.setFont(Font.font(48));
        gc.setFill(Color.WHITE);
        gc.fillText(player1Scores + "\t\t\t\t\t\t" + player2Scores, Constants.MULTIPLAYER_GAME_WIDTH / 2, 100);
    }

    protected void startNewRound(GraphicsContext gc) {
        gc.setStroke(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(32));
        gc.strokeText("Click to start new round", Constants.MULTIPLAYER_GAME_WIDTH / 2, Constants.MULTIPLAYER_GAME_HEIGHT / 2.5);
        ball = new Ball(GameMode.MULTIPLAYER);
    }

    @Override
    protected void collision(Ball ball, Paddle paddleLeft, Paddle paddle2) {
        //keep the ball on the edge
        if(ball.getY() <= 0 || ball.getY() + Constants.BALL_DIAMETER >= Constants.MULTIPLAYER_GAME_HEIGHT){
            ball.setVelY(ball.getVelY() * -1);
            return;
        }

        //collision with the left paddle
        if(ball.getY() >= paddleLeft.getY() - Constants.BALL_DIAMETER && ball.getY() <= paddleLeft.getY() + Constants.MULTIPLAYER_PADDLE_HEIGHT && ball.getX() <= Constants.MULTIPLAYER_GAME_LEFT_PADDLE_X_POSITION){
            ball.setVelX(-(ball.getVelX() - Constants.BALL_VELOCITY_INCREASE));
            if(ball.getVelY() <= 0){
                ball.setVelY(ball.getVelY() - Constants.BALL_VELOCITY_INCREASE);
            } else {
                ball.setVelY(ball.getVelY() + Constants.BALL_VELOCITY_INCREASE);
            }
            return;
        }

        //collision with the right paddle
        if(ball.getY() >= paddleRight.getY() - Constants.BALL_DIAMETER && ball.getY() <= paddleRight.getY() + Constants.MULTIPLAYER_PADDLE_HEIGHT && ball.getX() + Constants.BALL_DIAMETER >= Constants.MULTIPLAYER_GAME_RIGHT_PADDLE_X_POSITION){
            ball.setVelX(-(ball.getVelX() + Constants.BALL_VELOCITY_INCREASE));
            if(ball.getVelY() <= 0){
                ball.setVelY(ball.getVelY() - Constants.BALL_VELOCITY_INCREASE);
            } else {
                ball.setVelY(ball.getVelY() + Constants.BALL_VELOCITY_INCREASE);
            }
        }
    }

    @Override
    protected boolean ballOutside(Ball ball) {
        return ball.getX() < 0 || ball.getX() > Constants.MULTIPLAYER_GAME_RIGHT_PADDLE_X_POSITION;
    }

    protected void setNewGameProperties() {
        gameOverPane.setDisable(true);
        gameOverPane.setVisible(false);
        player1Scores = 0;
        player2Scores = 0;
        ball = new Ball(GameMode.MULTIPLAYER);
        drawBackGround(gc);
        drawBall(gc,ball);
        drawPaddles(gc,paddleLeft,paddleRight);
        drawOpeningText(gc);
        drawScores(gc);
    }

    @FXML
    public void backToMainMenu() {
        GameManager.SceneManager.switchToMainMenuScene();
    }
}


