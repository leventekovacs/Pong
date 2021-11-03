package com.pong.controllers;

import com.pong.constants.Constants;
import com.pong.engines.MultiPlayerGameEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerVsPlayerGameController extends MultiPlayerGameEngine implements Initializable {

    private boolean isPaddleRightDisabled;
    private boolean isPaddleLeftDisabled;

    @FXML
    private Label resultLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isPaddleLeftDisabled = true;
        isPaddleRightDisabled = true;

        gameOverPane.setDisable(true);
        gameOverPane.setVisible(false);

        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(keyEvent -> {
            if(!isPaddleLeftDisabled) {
                if(keyEvent.getCode() == KeyCode.W){
                    if(paddleLeft.getY() >= - (Constants.MULTIPLAYER_PADDLE_HEIGHT / 2))
                        paddleLeft.setY(paddleLeft.getY() - Constants.PLAYER_VS_PLAYER_PADDLE_VELOCITY);
                }
                if(keyEvent.getCode() == KeyCode.S){
                    if(paddleLeft.getY() <= Constants.MULTIPLAYER_GAME_HEIGHT - Constants.MULTIPLAYER_PADDLE_HEIGHT / 2)
                        paddleLeft.setY(paddleLeft.getY() + Constants.PLAYER_VS_PLAYER_PADDLE_VELOCITY);
            }
            }
            if(!isPaddleRightDisabled) {
                if(keyEvent.getCode() == KeyCode.UP){
                    if(paddleRight.getY() >= - (Constants.MULTIPLAYER_PADDLE_HEIGHT / 2))
                        paddleRight.setY(paddleRight.getY() - Constants.PLAYER_VS_PLAYER_PADDLE_VELOCITY);
                }
                if(keyEvent.getCode() == KeyCode.DOWN){
                    if(paddleRight.getY() <= Constants.MULTIPLAYER_GAME_HEIGHT - Constants.MULTIPLAYER_PADDLE_HEIGHT / 2)
                        paddleRight.setY(paddleRight.getY() + Constants.PLAYER_VS_PLAYER_PADDLE_VELOCITY);
                }
            }
        });
        canvas.setOnMouseClicked(mouseEvent -> gameStarted = true);

        drawBackGround(gc);
        drawBall(gc, ball);
        drawPaddles(gc, paddleLeft, paddleRight);
        drawOpeningText(gc);
        drawScores(gc);
        canvas.setOpacity(1);

        tl = new Timeline(new KeyFrame(Duration.millis(Constants.DRAWING_TIME), e -> run()));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();
    }

    private void run() {
        if(gameStarted) {
            drawBackGround(gc);
            drawBall(gc,ball);
            drawPaddles(gc,paddleLeft,paddleRight);
            drawScores(gc);

            ball.moveBall();
            collision(ball,paddleLeft,paddleRight);

            if(ballOutside(ball)) {
                if(ball.getX() < Constants.MULTIPLAYER_GAME_WIDTH / 2) {
                    player2Scores++;
                    drawBackGround(gc);
                    drawBall(gc,ball);
                    drawPaddles(gc,paddleLeft,paddleRight);
                    drawScores(gc);
                    if(player2Scores == 3) {
                        resultLabel.setText("Right player won!");
                        gameOver();
                    }
                } else {
                    player1Scores++;
                    drawBackGround(gc);
                    drawBall(gc,ball);
                    drawPaddles(gc,paddleLeft,paddleRight);
                    drawScores(gc);
                    if(player1Scores == 3) {
                        resultLabel.setText("Left player won!");
                        gameOver();
                    }
                }
                gameStarted = false;
                startNewRound(gc);
            }
            if(ball.getVelX() < 0) {
                isPaddleRightDisabled = true;
                isPaddleLeftDisabled = false;
            } else {
                isPaddleRightDisabled = false;
                isPaddleLeftDisabled = true;
            }
        }
    }

    protected void gameOver() {
        gameStarted = false;
        tl.stop();
        gameOverPane.setDisable(false);
        gameOverPane.setVisible(true);
        canvas.setOpacity(0.5);
    }

    @FXML
    private void startNewGame() {
        setNewGameProperties();
        canvas.setOpacity(1);
        tl.play();
    }
}
