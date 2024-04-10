package repository;

import static domain.piece.Color.BLACK;
import static domain.piece.Color.WHITE;
import static fixture.PositionFixture.A1;
import static fixture.PositionFixture.B1;
import static fixture.PositionFixture.B2;
import static fixture.PositionFixture.G2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Piece;
import domain.piece.piecerole.Queen;
import domain.piece.piecerole.Rook;
import domain.position.Position;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.generator.ConnectionGenerator;

class PiecePositionRepositoryTest {
    private final PiecePositionRepository repository = new PiecePositionRepository();

    @BeforeEach
    void setUp() throws SQLException {
        repository.clear();
        Connection connection = ConnectionGenerator.getConnection();
        connection.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        Connection connection = ConnectionGenerator.getConnection();
        connection.rollback();
    }

    @DisplayName("체스판의 위치와 기물의 정보를 데이터베이스에 저장한다.")
    @Test
    void savePiecePosition() {
        Piece piece = new Piece(Rook.create(), BLACK);

        int rows = repository.save(A1, piece);

        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("체스판의 위치를 기준으로 기물을 조회한다.")
    @Test
    void findPieceByPosition() {
        Piece piece = new Piece(Rook.create(), BLACK);
        repository.save(B1, piece);

        Piece savedPiece = repository.findPieceByPosition(B1);

        assertThat(savedPiece).isEqualTo(piece);
    }

    @DisplayName("기물의 체스판 위치 정보를 모두 제거한다.")
    @Test
    void clear() {
        Piece piece = new Piece(Rook.create(), BLACK);
        repository.save(B1, piece);

        repository.clear();

        assertThatThrownBy(() -> repository.findPieceByPosition(B1))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("기물의 위치 정보를 삭제한다.")
    @Test
    void delete() {
        Piece piece = new Piece(Rook.create(), BLACK);
        repository.save(B1, piece);

        repository.deleteByPosition(B1);

        assertThatThrownBy(() -> repository.findPieceByPosition(B1))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("기물의 위치 정보를 수정한다.")
    @Test
    void updatePosition() {
        Piece piece = new Piece(Rook.create(), BLACK);
        repository.save(B1, piece);

        repository.updatePosition(B1, B2);

        assertThat(repository.findPieceByPosition(B2)).isEqualTo(piece);
    }

    @DisplayName("기물의 모든 위치 정보를 조회한다.")
    @Test
    void findAllPiecePositions() {
        Piece a1 = new Piece(Rook.create(), BLACK);
        Piece b1 = new Piece(Queen.create(), BLACK);
        Piece g2 = new Piece(Queen.create(), WHITE);
        repository.save(A1, a1);
        repository.save(B1, b1);
        repository.save(G2, g2);

        Map<Position, Piece> piecePositions = repository.findAllPiecePositions();

        assertThat(piecePositions.get(A1)).isEqualTo(a1);
        assertThat(piecePositions.get(B1)).isEqualTo(b1);
        assertThat(piecePositions.get(G2)).isEqualTo(g2);
    }
}
