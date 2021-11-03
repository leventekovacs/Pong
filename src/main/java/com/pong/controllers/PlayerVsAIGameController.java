package com.pong.controllers;

import com.pong.GameManager;
import com.pong.constants.Difficulty;
import com.pong.constants.Constants;
import com.pong.engines.MultiPlayerGameEngine;
import com.pong.model.AddDataToDatabase;
import com.pong.model.PlayerVsAIGameResult;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class PlayerVsAIGameController extends MultiPlayerGameEngine implements Initializable, AddDataToDatabase {

    @FXML
    protected AnchorPane difficultyPane;

    private Difficulty difficulty;
    private double velAI;
    private double maxAIVel;

    private String result = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameOverPane.setDisable(true);
        gameOverPane.setVisible(false);

        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        canvas.setOnMouseMoved(mouseEvent ->  paddleLeft.setY(mouseEvent.getY() - paddleLeft.getHeight()/2));
        canvas.setOnMouseClicked(mouseEvent -> gameStarted = true);

        drawBackGround(gc);
        drawBall(gc, ball);
        drawPaddles(gc, paddleLeft, paddleRight);
        drawOpeningText(gc);
        drawScores(gc);

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
            drawDifficulty(gc);

            ball.moveBall();
            moveAIPaddle();
            collision(ball,paddleLeft,paddleRight);

            if(ballOutside(ball)) {
                if(ball.getX() < Constants.MULTIPLAYER_GAME_WIDTH / 2) {
                    player2Scores++;
                    drawBackGround(gc);
                    drawBall(gc,ball);
                    drawPaddles(gc,paddleLeft,paddleRight);
                    drawScores(gc);
                    drawDifficulty(gc);
                    if(player2Scores == 3) {
                        resultLabel.setText("You lost!");
                        result = player1Scores + " - " + player2Scores;
                        gameOver();
                    }
                } else {
                    player1Scores++;
                    drawBackGround(gc);
                    drawBall(gc,ball);
                    drawPaddles(gc,paddleLeft,paddleRight);
                    drawScores(gc);
                    drawDifficulty(gc);
                    if(player1Scores == 3) {
                        resultLabel.setText("You won!");
                        result = player1Scores + " - " + player2Scores;
                        gameOver();
                    }
                }
                gameStarted = false;
                startNewRound(gc);
            }
        }
    }

    private void moveAIPaddle() {
        if(velAI > Math.abs(ball.getVelX())) {
            velAI = 1;
        }

        if(velAI < Math.abs(ball.getVelX()) && velAI < maxAIVel) {
            velAI += 0.1;
        }

        if(ball.getY() <=  paddleRight.getY() + Constants.MULTIPLAYER_PADDLE_HEIGHT / 2) {
            paddleRight.setY(paddleRight.getY() - velAI);
        } else {
            paddleRight.setY(paddleRight.getY() + velAI);
        }
    }

    private void drawDifficulty(GraphicsContext gc) {
        gc.setFont(Font.font("Impact",48));
        gc.setFill(Color.valueOf(Constants.BACKGROUND_COLOR_2));
        switch (difficulty) {
            case BEGINNER -> gc.fillText("Beginner", Constants.MULTIPLAYER_GAME_WIDTH / 2, 50);
            case EASY -> gc.fillText("Easy", Constants.MULTIPLAYER_GAME_WIDTH / 2, 50);
            case MEDIUM -> gc.fillText("Medium", Constants.MULTIPLAYER_GAME_WIDTH / 2, 50);
            case HARD -> gc.fillText("Hard", Constants.MULTIPLAYER_GAME_WIDTH / 2, 50);
            case EXTREME -> gc.fillText("Extreme", Constants.MULTIPLAYER_GAME_WIDTH / 2, 50);
        }
    }

    protected void gameOver() {
        gameStarted = false;
        tl.stop();
        gameOverPane.setDisable(false);
        gameOverPane.setVisible(true);
        addGameResultToDatabase();
        canvas.setOpacity(0.5);
    }

    public void addGameResultToDatabase() {
        String difficultyString;
        int point = 0;

        //give value to difficulty string and calculate points
        switch (this.difficulty) {
            case BEGINNER -> {
                difficultyString = "Beginner";
                if (result.charAt(0) == '3') {
                    if (result.charAt(4) == '0') point += 17;
                    if (result.charAt(4) == '1') point += 16;
                    if (result.charAt(4) == '2') point += 15;
                } else {
                    if (result.charAt(0) == '0') point += 0;
                    if (result.charAt(0) == '1') point += 5;
                    if (result.charAt(0) == '2') point += 10;
                }
            }
            case EASY -> {
                difficultyString = "Easy";
                if (result.charAt(0) == '3') {
                    if (result.charAt(4) == '0') point += 20;
                    if (result.charAt(4) == '1') point += 19;
                    if (result.charAt(4) == '2') point += 18;
                } else {
                    if (result.charAt(0) == '0') point += 1;
                    if (result.charAt(0) == '1') point += 6;
                    if (result.charAt(0) == '2') point += 11;
                }
            }
            case MEDIUM -> {
                difficultyString = "Medium";
                if (result.charAt(0) == '3') {
                    if (result.charAt(4) == '0') point += 23;
                    if (result.charAt(4) == '1') point += 22;
                    if (result.charAt(4) == '2') point += 21;
                } else {
                    if (result.charAt(0) == '0') point += 2;
                    if (result.charAt(0) == '1') point += 7;
                    if (result.charAt(0) == '2') point += 12;
                }
            }
            case HARD -> {
                difficultyString = "Hard";
                if (result.charAt(0) == '3') {
                    if (result.charAt(4) == '0') point += 26;
                    if (result.charAt(4) == '1') point += 25;
                    if (result.charAt(4) == '2') point += 24;
                } else {
                    if (result.charAt(0) == '0') point += 3;
                    if (result.charAt(0) == '1') point += 8;
                    if (result.charAt(0) == '2') point += 13;
                }
            }
            case EXTREME -> {
                difficultyString = "Extreme";
                if (result.charAt(0) == '3') {
                    if (result.charAt(4) == '0') point += 29;
                    if (result.charAt(4) == '1') point += 28;
                    if (result.charAt(4) == '2') point += 27;
                } else {
                    if (result.charAt(0) == '0') point += 4;
                    if (result.charAt(0) == '1') point += 9;
                    if (result.charAt(0) == '2') point += 5;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + this.difficulty);
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("y.MM.dd HH:mm");

        PlayerVsAIGameResult playerVsAIGameResult = new PlayerVsAIGameResult(
                0, GameManager.getPlayerName(), difficultyString, result, simpleDateFormat.format(date), point);

        GameManager.getDatabase().addPlayerVsAIGameResult(playerVsAIGameResult);
    }

    @FXML
    private void startNewGame() {
        setNewGameProperties();
        tl.play();
        difficultyPane.setDisable(false);
        difficultyPane.setVisible(true);
    }

    @FXML
    private void setDifficultyBeginner() {
        difficulty = Difficulty.BEGINNER;
        velAI = 1;
        maxAIVel = 1;
        difficultyPane.setDisable(true);
        difficultyPane.setVisible(false);
        canvas.setOpacity(1);
        gameStarted = true;
    }

    @FXML
    private void setDifficultyEasy() {
        difficulty = Difficulty.EASY;
        velAI = 1;
        maxAIVel = 1.5;
        difficultyPane.setDisable(true);
        difficultyPane.setVisible(false);
        canvas.setOpacity(1);
        gameStarted = true;
    }

    @FXML
    private void setDifficultyMedium() {
        difficulty = Difficulty.MEDIUM;
        velAI = 1;
        maxAIVel = 2;
        difficultyPane.setDisable(true);
        difficultyPane.setVisible(false);
        canvas.setOpacity(1);
        gameStarted = true;
    }

    @FXML
    private void setDifficultyHard() {
        difficulty = Difficulty.HARD;
        velAI = 1;
        maxAIVel = 3;
        difficultyPane.setDisable(true);
        difficultyPane.setVisible(false);
        canvas.setOpacity(1);
        gameStarted = true;
    }

    @FXML
    private void setDifficultyExtreme() {
        difficulty = Difficulty.EXTREME;
        velAI = 1;
        maxAIVel = 4;
        difficultyPane.setDisable(true);
        difficultyPane.setVisible(false);
        canvas.setOpacity(1);
        gameStarted = true;
    }
}
