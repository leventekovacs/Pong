package com.pong.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Database {
    private final String URL = "jdbc:derby:PongDB";
    private final String USERNAME = "";
    private final String PASSWORD = "";

    private final String CREAT_SINGLE_PLAYER_GAME_RESULT_TABLE =
            "create table single_player_game_result(name varchar(25), time varchar(10), hit int, date varchar(20))";
    private final String CREAT_PLAYER_VS_AI_GAME_RESULT_TABLE =
            "create table player_vs_ai_game_result(name varchar(25), difficulty varchar(10), result varchar(10), date varchar(20), point int)";

    private final String SINGLE_PLAYER_GAME_RESULT_QUERY = "SELECT * FROM SINGLE_PLAYER_GAME_RESULT ORDER BY time DESC, hit ASC";
    private final String PLAYER_VS_AI_GAME_RESULT_QUERY = "SELECT * FROM PLAYER_VS_AI_GAME_RESULT ORDER BY point DESC, date DESC";

    private Connection connection = null;
    private Statement statement = null;
    PreparedStatement preparedStatement = null;
    private DatabaseMetaData databaseMetaData = null;
    private ResultSet resultSet = null;

    public Database() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(""+e);
        }

        if (connection != null){
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                System.out.println(""+e);
            }
        }

        try {
            databaseMetaData = connection.getMetaData();
        } catch (SQLException e) {
            System.out.println(""+e);
        }

        try {
            resultSet = databaseMetaData.getTables(null,"APP","SINGLE_PLAYER_GAME_RESULT",null);
            if(!resultSet.next()) {
                statement.execute(CREAT_SINGLE_PLAYER_GAME_RESULT_TABLE);
            }
            resultSet = databaseMetaData.getTables(null,"APP","PLAYER_VS_AI_GAME_RESULT",null);
            if(!resultSet.next()) {
                statement.execute(CREAT_PLAYER_VS_AI_GAME_RESULT_TABLE);
            }
        } catch (SQLException e) {
            System.out.println(""+e);
        }
    }

    public void addSinglePLayerGameResult(SinglePlayerGameResult singlePlayerGameResult) {
        String sql = "insert into single_player_game_result values(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, singlePlayerGameResult.getName());
            preparedStatement.setString(2, singlePlayerGameResult.getTime());
            preparedStatement.setInt(3, singlePlayerGameResult.getHit());
            preparedStatement.setString(4, singlePlayerGameResult.getDate());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(""+e);
        }
    }

    public void addPlayerVsAIGameResult(PlayerVsAIGameResult playerVsAIGameResult) {
        String sql = "insert into player_vs_ai_game_result values(?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playerVsAIGameResult.getName());
            preparedStatement.setString(2, playerVsAIGameResult.getDifficulty());
            preparedStatement.setString(3, playerVsAIGameResult.getResult());
            preparedStatement.setString(4, playerVsAIGameResult.getDate());
            preparedStatement.setInt(5, playerVsAIGameResult.getPoint());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(""+e);
        }
    }

    public ObservableList<SinglePlayerGameResult> getSinglePlayerGameData() {
        ObservableList<SinglePlayerGameResult> data = FXCollections.observableArrayList();
        try {
            resultSet = statement.executeQuery(SINGLE_PLAYER_GAME_RESULT_QUERY);
            int rank = 1;
            while (resultSet.next()) {
                data.add( new SinglePlayerGameResult(
                        rank++,
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ObservableList<PlayerVsAIGameResult> getPlayerVsAIGameData() {
        ObservableList<PlayerVsAIGameResult> data = FXCollections.observableArrayList();
        try {
            resultSet = statement.executeQuery(PLAYER_VS_AI_GAME_RESULT_QUERY);
            int rank = 1;
            while (resultSet.next()) {
                data.add( new PlayerVsAIGameResult(
                        rank++,
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public String getSinglePlayerRank1Name() {
        String sql = "SELECT name from single_player_game_result ORDER BY time DESC fetch first 1 rows only";
        try {
            resultSet = statement.executeQuery(sql);
            if(resultSet.next())
                return resultSet.getString(1);
        } catch (SQLException e) {
            System.out.println(""+e);
        }
        return null;
    }

    public String getPlayerVsAIRank1Name() {
        String sql = "SELECT name from player_vs_ai_game_result ORDER BY point DESC fetch first 1 rows only";
        try {
            resultSet = statement.executeQuery(sql);
            if(resultSet.next())
                return resultSet.getString(1);
        } catch (SQLException e) {
            System.out.println(""+e);
        }
        return null;
    }


    public void test() {
        /**
        try {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
         */

        /**
         try {
         statement.execute("delete from player_vs_ai_game_result where difficulty like 'Extreme'");
         } catch (SQLException e) {
         e.printStackTrace();
         }
         */

         /**
        try {
            statement.execute("UPDATE single_player_game_result SET name = 'Bence' where name like'bence'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

         */
    }
}
