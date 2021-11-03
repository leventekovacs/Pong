package com.pong.timer;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class Stopwatch {

    private int elapsedTime;
    private int seconds;
    private int minutes;

    private Timeline tl;

    public Stopwatch() {
        tl = new Timeline(new KeyFrame(Duration.millis(1000), e -> run()));
        tl.setCycleCount(Animation.INDEFINITE);
    }

    public void run() {
        elapsedTime += 1000;
        seconds = (elapsedTime/1000) % 60;
        minutes = elapsedTime/60000;

    }

    public void start() {
        tl.play();
    }

    public String getTime() { return String.format("%02d:%02d",minutes,seconds); }

    public void pause() {
        tl.stop();
    }

    public void stop() {
        elapsedTime = 0;
        tl.stop();
    }
}
