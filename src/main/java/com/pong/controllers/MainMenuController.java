package com.pong.controllers;

import com.pong.GameManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private AnchorPane logInPane;

    @FXML
    private AnchorPane logOutPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label nameLabel;

    @FXML
    private Label infoLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logOutPane.setDisable(true);
        logOutPane.setVisible(false);
        infoLabel.setVisible(false);
    }

    @FXML
    public void startSinglePlayerGame() {
        if(GameManager.getPlayerName() == null) {
            infoLabel.setVisible(true);
        } else {
            new SinglePlayerGameController();
            GameManager.SceneManager.switchToSinglePlayerGameScene();
            infoLabel.setVisible(false);
        }
    }

    @FXML
    public void startPlayerVsAIGame() {
        if(GameManager.getPlayerName() == null) {
            infoLabel.setVisible(true);
        } else {
            new PlayerVsAIGameController();
            GameManager.SceneManager.switchToPlayerVsAIGameScene();
            infoLabel.setVisible(false);
        }
    }

    @FXML
    public void startPlayerVsPlayer() {
        new PlayerVsPlayerGameController();
        GameManager.SceneManager.switchToPlayerVsPlayerGameScene();
        infoLabel.setVisible(false);
    }

    @FXML
    public void viewLeaderboards() {
        new LeaderboardsController();
        GameManager.SceneManager.switchToLeaderboardsScene();
        infoLabel.setVisible(false);
    }

    @FXML
    public void closeApp() {
        GameManager.primaryStage.close();
    }

    @FXML
    public void logIn() {
        GameManager.setPlayerName(nameTextField.getText());

        if(GameManager.getPlayerName() == null){
            infoLabel.setVisible(true);
        } else {
            nameLabel.setText(GameManager.getPlayerName());
            logInPane.setDisable(true);
            logInPane.setVisible(false);
            nameTextField.clear();
            logOutPane.setDisable(false);
            logOutPane.setVisible(true);
            infoLabel.setVisible(false);
        }
    }

    @FXML
    public void logOut() {
        GameManager.setPlayerName(null);
        nameLabel.setText(null);
        logOutPane.setDisable(true);
        logOutPane.setVisible(false);
        logInPane.setDisable(false);
        logInPane.setVisible(true);
        infoLabel.setVisible(false);
    }
}
