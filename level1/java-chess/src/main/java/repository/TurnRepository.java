package repository;

import static domain.piece.Color.WHITE;
import static repository.mapper.TurnMapper.getTurnByName;

import domain.game.Turn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import repository.generator.ConnectionGenerator;

public class TurnRepository {
    public void save(final Turn turn) {
        String query = "INSERT INTO game_setting VALUES (?, ?) ON DUPLICATE KEY UPDATE content = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "turn");
            preparedStatement.setString(2, turn.getName());
            preparedStatement.setString(3, turn.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Turn find() {
        String query = "SELECT content FROM game_setting WHERE category = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "turn");

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getTurnByName(resultSet.getString("content"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Turn(WHITE);
    }

    public void clear() {
        String query = "DELETE FROM game_setting WHERE category = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "turn");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
