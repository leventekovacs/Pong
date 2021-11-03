package com.pong.controllers;

import com.pong.GameManager;
import com.pong.model.PlayerVsAIGameResult;
import com.pong.model.SinglePlayerGameResult;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.ResourceBundle;

public class LeaderboardsController implements Initializable {

    @FXML
    private TableView<SinglePlayerGameResult> singlePlayerTable;

    @FXML
    private TableView<PlayerVsAIGameResult> playerVsAITable;

    @FXML
    private AnchorPane singlePlayerLeaderboardPane;

    @FXML
    private AnchorPane playerVsAILeaderboardPane;

    private static ObservableList<SinglePlayerGameResult> singlePlayerLeaderboardData;
    private static ObservableList<PlayerVsAIGameResult> playerVsAILeaderboardData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        singlePlayerLeaderboardData = GameManager.getDatabase().getSinglePlayerGameData();
        playerVsAILeaderboardData = GameManager.getDatabase().getPlayerVsAIGameData();
        createTables();
        switchToSinglePlayerLeaderboard();
    }

    @FXML
    public void backToMainMenu() {
        GameManager.SceneManager.switchToMainMenuScene();
    }

    public void switchToSinglePlayerLeaderboard() {
        playerVsAILeaderboardPane.setDisable(true);
        playerVsAILeaderboardPane.setVisible(false);
        singlePlayerLeaderboardPane.setDisable(false);
        singlePlayerLeaderboardPane.setVisible(true);
    }

    public void switchToPlayerVsAILeaderboard() {
        singlePlayerLeaderboardPane.setDisable(true);
        singlePlayerLeaderboardPane.setVisible(false);
        playerVsAILeaderboardPane.setDisable(false);
        playerVsAILeaderboardPane.setVisible(true);
    }

    private void createTables() {
        TableColumn<SinglePlayerGameResult, Integer> singlePlayerRankColumn = new TableColumn<>( "Rank");
        singlePlayerRankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        singlePlayerRankColumn.setPrefWidth(70);

        TableColumn<SinglePlayerGameResult, String> singlePlayerNameColumn = new TableColumn<>("Name");
        singlePlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        singlePlayerNameColumn.setPrefWidth(180);

        TableColumn<SinglePlayerGameResult, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeColumn.setPrefWidth(150);

        TableColumn<SinglePlayerGameResult, Integer> hitColumn = new TableColumn<>("Hit");
        hitColumn.setCellValueFactory(new PropertyValueFactory<>("hit"));
        hitColumn.setPrefWidth(100);

        TableColumn<SinglePlayerGameResult, String> singlePlayerDateColumn = new TableColumn<>("Date");
        singlePlayerDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        singlePlayerDateColumn.setPrefWidth(200);

        singlePlayerTable.getColumns().addAll(singlePlayerRankColumn,singlePlayerNameColumn,timeColumn,hitColumn,singlePlayerDateColumn);
        singlePlayerTable.setItems(singlePlayerLeaderboardData);

        TableColumn<PlayerVsAIGameResult, Integer> playerVsAIRankColumn = new TableColumn<>( "Rank");
        playerVsAIRankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        playerVsAIRankColumn.setPrefWidth(70);

        TableColumn<PlayerVsAIGameResult, String> playerVsAINameColumn = new TableColumn<>("Name");
        playerVsAINameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playerVsAINameColumn.setPrefWidth(180);

        TableColumn<PlayerVsAIGameResult, String> difficultyColumn = new TableColumn<>("Difficulty");
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        difficultyColumn.setPrefWidth(150);

        TableColumn<PlayerVsAIGameResult, String> resultColumn = new TableColumn<>("Result");
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        resultColumn.setPrefWidth(100);

        TableColumn<PlayerVsAIGameResult, String> playerVsAIDateColumn = new TableColumn<>("Date");
        playerVsAIDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        playerVsAIDateColumn.setPrefWidth(200);

        playerVsAITable.getColumns().addAll(playerVsAIRankColumn,playerVsAINameColumn,difficultyColumn,resultColumn,playerVsAIDateColumn);
        playerVsAITable.setItems(playerVsAILeaderboardData);
    }
}
