package com.pong.controllers;

import com.pong.components.Ball;
import com.pong.GameManager;
import com.pong.constants.GameMode;
import com.pong.components.Paddle;
import com.pong.constants.Constants;
import com.pong.engines.GameEngine;
import com.pong.model.AddDataToDatabase;
import com.pong.model.Database;
import com.pong.model.SinglePlayerGameResult;
import com.pong.timer.Stopwatch;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.canvas.Canvas;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.net.URL;


public class SinglePlayerGameController extends GameEngine implements Initializable, AddDataToDatabase {

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane gameOverPane;

    @FXML
    private Label stopwatchLabel;

    @FXML
    private Label timeLabel;

    private Stopwatch stopwatch;
    private int hit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gm = GameMode.SINGLE_PLAYER;

        stopwatch = new Stopwatch();
        hit = 0;
        ball = new Ball(GameMode.SINGLE_PLAYER);
        paddle = new Paddle();

        gameOverPane.setDisable(true);
        gameOverPane.setVisible(false);

        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        canvas.setOnMouseMoved(mouseEvent -> paddle.setX(mouseEvent.getX() - (Constants.SINGLE_PLAYER_PADDLE_WIDTH / 2)));
        canvas.setOnMouseClicked(mouseEvent -> { gameStarted = true; stopwatch.start(); } );

        drawBackGround(gc);
        drawBall(gc,ball);
        drawPaddle(gc,paddle);
        drawOpeningText(gc);

        tl = new Timeline(new KeyFrame(Duration.millis(Constants.DRAWING_TIME), e -> run()));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();
    }

    private void run() {
        if(gameStarted) {
            stopwatchLabel.setText(stopwatch.getTime());
            drawBackGround(gc);
            drawPaddle(gc,paddle);
            drawBall(gc,ball);

            ball.moveBall();
            collision(ball,paddle,null);

            if(ballOutside(ball)){
                gameOver();
            }
        }
    }

    @Override
    protected void collision(Ball ball, Paddle paddle, Paddle paddle2) {
        //collision with the top edge
        if (ball.getY() <= 0) {
            ball.setVelY(ball.getVelY() * -1);
            return;
        }
        //collision with the left edge
        if(ball.getX() <= 0){
            ball.setVelX(ball.getVelX() * -1);
            return;
        }
        //collision with the right edge
        if(ball.getX() >= Constants.SINGLE_PLAYER_GAME_WIDTH - Constants.BALL_DIAMETER ) {
            ball.setVelX(ball.getVelX() * -1);
            return;
        }
        //collision with the paddle
        if((ball.getX() + Constants.BALL_DIAMETER >= paddle.getX() && ball.getX() <= paddle.getX()+ paddle.getWidth()) && ball.getY() >= Constants.SINGLE_PLAYER_GAME_PADDLE_Y_POSITION - Constants.BALL_DIAMETER) {
            ball.setVelY(-(ball.getVelY() + Constants.BALL_VELOCITY_INCREASE) );
            if(ball.getVelX() <= 0) {
                ball.setVelX(ball.getVelX() - Constants.BALL_VELOCITY_INCREASE);
            } else {
                ball.setVelX(ball.getVelX() + Constants.BALL_VELOCITY_INCREASE);
            }
            hit++;
            return;
        }
    }

    @Override
    protected boolean ballOutside(Ball ball) {
        if(ball.getY() > Constants.SINGLE_PLAYER_GAME_PADDLE_Y_POSITION) {
            ball.setX(Constants.SINGLE_PLAYER_GAME_WIDTH / 2 - Constants.BALL_DIAMETER / 2);
            ball.setY(Constants.SINGLE_PLAYER_GAME_HEIGHT / 2 - Constants.BALL_DIAMETER / 2);
            return true;
        }
        return false;
    }

    @Override
    protected void gameOver() {
        gameStarted = false;
        tl.stop();
        timeLabel.setText(stopwatchLabel.getText());
        gameOverPane.setDisable(false);
        gameOverPane.setVisible(true);
        canvas.setOpacity(0.5);
        stopwatch.pause();
        stopwatchLabel.setVisible(false);
        addGameResultToDatabase();
        stopwatch.stop();
    }

    @Override
    public void addGameResultToDatabase() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("Y.MM.dd HH:mm");

        SinglePlayerGameResult singlePlayerGameResult = new SinglePlayerGameResult(0, GameManager.getPlayerName(), stopwatch.getTime(), hit, simpleDateFormat.format(date));

        GameManager.getDatabase().addSinglePLayerGameResult(singlePlayerGameResult);
    }

    @FXML
    private void startNewGame() {
        gameOverPane.setDisable(true);
        gameOverPane.setVisible(false);
        ball = new Ball(GameMode.SINGLE_PLAYER);
        drawBackGround(gc);
        drawBall(gc,ball);
        drawPaddle(gc,paddle);
        drawOpeningText(gc);
        canvas.setOpacity(1);
        hit = 0;
        stopwatch.start();
        stopwatchLabel.setVisible(true);
        tl.play();
    }

    @FXML
    private void backToMainMenu() {
        GameManager.SceneManager.switchToMainMenuScene();
    }
}
