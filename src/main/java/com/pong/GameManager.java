package com.pong;

import com.pong.model.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import static com.pong.GameManager.SceneManager.*;

public class GameManager extends Application {

    public static Stage primaryStage;

    private static String playerName;

    private static final Database database = new Database();

    public static class SceneManager {
        public static Scene mainMenuScene, singePlayerGameScene, playerVsAIGameScene, playerVsPlayerGameScene, leaderboardsScene;

        public static void switchToMainMenuScene() {
            primaryStage.setScene(mainMenuScene);
        }

        public static void switchToSinglePlayerGameScene() {
            loadFXML("singlePlayerGame-view.fxml");
            primaryStage.setScene(singePlayerGameScene);
        }

        public static void switchToPlayerVsAIGameScene() {
            loadFXML("playerVsAIGame-view.fxml");
            primaryStage.setScene(playerVsAIGameScene);
        }

        public static void switchToPlayerVsPlayerGameScene() {
            loadFXML("playerVsPlayerGame-view.fxml");
            primaryStage.setScene(playerVsPlayerGameScene);
        }

        public static void switchToLeaderboardsScene() {
            loadFXML("leaderboards-view.fxml");
            primaryStage.setScene(leaderboardsScene);
        }

        protected static void loadFXML(String fileName) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SceneManager.class.getResource("/"+fileName));
            try {
                switch (fileName) {
                    case "mainMenu-view.fxml" -> mainMenuScene = new Scene(Objects.requireNonNull(fxmlLoader).load());
                    case "singlePlayerGame-view.fxml" -> singePlayerGameScene = new Scene(Objects.requireNonNull(fxmlLoader).load());
                    case "playerVsAIGame-view.fxml" -> playerVsAIGameScene = new Scene(Objects.requireNonNull(fxmlLoader).load());
                    case "playerVsPlayerGame-view.fxml" -> playerVsPlayerGameScene = new Scene(Objects.requireNonNull(fxmlLoader).load());
                    case "leaderboards-view.fxml" -> leaderboardsScene = new Scene(Objects.requireNonNull(fxmlLoader).load());
                    default -> System.err.println("Failed to set the scene");
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to load the "+fileName);
            }
        }
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setX(250);
        primaryStage.setY(0);
        primaryStage.setTitle("Pong");
        primaryStage.setResizable(false);
        SceneManager.loadFXML("mainMenu-view.fxml");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
        database.test();
    }

    public static void startGame(String[] args) {
        launch(args);
    }

    public static String getPlayerName() {
        return GameManager.playerName;
    }

    public static void setPlayerName(String playerName) {
        GameManager.playerName = playerName;
    }

    public static Database getDatabase() {
        return database;
    }
}