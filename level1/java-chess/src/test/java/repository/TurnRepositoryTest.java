package repository;

import static domain.piece.Color.BLACK;
import static domain.piece.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Turn;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.generator.ConnectionGenerator;

class TurnRepositoryTest {
    private final TurnRepository turnRepository = new TurnRepository();

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = ConnectionGenerator.getConnection();
        connection.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        Connection connection = ConnectionGenerator.getConnection();
        connection.rollback();
    }

    @DisplayName("현재 차례의 정보를 저장한다.")
    @Test
    void saveTurn() {
        Turn turn = new Turn(BLACK);

        turnRepository.save(turn);

        assertThat(turnRepository.find()).isEqualTo(new Turn(BLACK));
    }

    @DisplayName("현재 차례의 정보를 업데이트한다.")
    @Test
    void updateTurn() {
        Turn first = new Turn(WHITE);
        Turn second = new Turn(BLACK);
        turnRepository.save(first);

        turnRepository.save(second);

        assertThat(turnRepository.find()).isEqualTo(second);
    }

    @DisplayName("현재 차례의 정보를 조회한다.")
    @Test
    void findTurn() {
        Turn turn = new Turn(BLACK);
        turnRepository.save(turn);

        assertThat(turnRepository.find()).isEqualTo(turn);
    }
}
