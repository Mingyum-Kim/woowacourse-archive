package repository;

import static repository.mapper.ColorMapper.getColorByName;
import static repository.mapper.FileMapper.getFileByName;
import static repository.mapper.PieceRoleMapper.getPieceRoleByName;
import static repository.mapper.RankMapper.getRankByName;

import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.piecerole.PieceRole;
import domain.position.File;
import domain.position.Position;
import domain.position.Rank;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import repository.generator.ConnectionGenerator;

public class PiecePositionRepository {
    public int save(final Position position, final Piece piece) {
        String query = "INSERT INTO piece_position VALUES (?, ?, ?, ?)";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, position.fileName());
            preparedStatement.setString(2, position.rankName());
            preparedStatement.setString(3, piece.getPieceRoleName());
            preparedStatement.setString(4, piece.getColorName());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int saveAll(final Map<Position, Piece> piecePosition) {
        String query = "INSERT INTO piece_position VALUES (?, ?, ?, ?)";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (Map.Entry<Position, Piece> entry : piecePosition.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                preparedStatement.setString(1, position.fileName());
                preparedStatement.setString(2, position.rankName());
                preparedStatement.setString(3, piece.getPieceRoleName());
                preparedStatement.setString(4, piece.getColorName());

                preparedStatement.addBatch();
            }

            int[] updatedCounts = preparedStatement.executeBatch();
            connection.commit();

            return Arrays.stream(updatedCounts).sum();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public Piece findPieceByPosition(final Position position) {
        String query = "SELECT * FROM piece_position WHERE chess_board_file = ? AND chess_board_rank = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, position.fileName());
            preparedStatement.setString(2, position.rankName());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                PieceRole pieceRole = getPieceRoleByName(resultSet.getString("piece_role"));
                Color color = getColorByName(resultSet.getString("color"));
                return new Piece(pieceRole, color);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("[ERROR] 기물을 조회할 수 없습니다.");
    }

    public void deleteByPosition(final Position position) {
        String query = "DELETE FROM piece_position WHERE chess_board_file = ? AND chess_board_rank = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, position.fileName());
            preparedStatement.setString(2, position.rankName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePosition(final Position source, final Position target) {
        String query = "UPDATE piece_position SET chess_board_file = ?, chess_board_rank = ? WHERE chess_board_file = ? AND chess_board_rank = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, target.fileName());
            preparedStatement.setString(2, target.rankName());
            preparedStatement.setString(3, source.fileName());
            preparedStatement.setString(4, source.rankName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Position, Piece> findAllPiecePositions() {
        String query = "SELECT * FROM piece_position";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            return generatePiecePositions(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Position, Piece> generatePiecePositions(final ResultSet resultSet) throws SQLException {
        Map<Position, Piece> piecePositions = new HashMap<>();
        while (resultSet.next()) {
            File file = getFileByName(resultSet.getString("chess_board_file"));
            Rank rank = getRankByName(resultSet.getString("chess_board_rank"));
            PieceRole pieceRole = getPieceRoleByName(resultSet.getString("piece_role"));
            Color color = getColorByName(resultSet.getString("color"));
            piecePositions.put(new Position(file, rank), new Piece(pieceRole, color));
        }
        return piecePositions;
    }

    public void clear() {
        String query = "TRUNCATE piece_position";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
