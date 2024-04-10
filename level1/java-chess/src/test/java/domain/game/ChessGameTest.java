package domain.game;

import static domain.piece.Color.WHITE;
import static fixture.PositionFixture.A2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.ChessGame;
import domain.piece.Piece;
import domain.piece.piecerole.Queen;
import domain.position.File;
import domain.position.Position;
import domain.position.Rank;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameTest {
    @DisplayName("체스 게임을 시작한다.")
    @Test
    void startChessGame() {
        ChessGame chessGame = new ChessGame(
                new Turn(WHITE),
                new HashMap<>()
        );
        chessGame.start();

        assertThat(chessGame.isContinuing()).isTrue();
    }

    @DisplayName("체스 게임을 종료한다.")
    @Test
    void endChessGame() {
        ChessGame chessGame = new ChessGame(
                new Turn(WHITE),
                new HashMap<>()
        );
        chessGame.start();
        chessGame.end();

        assertThat(chessGame.isContinuing()).isFalse();
    }

    @DisplayName("체스 게임이 시작되지 않은 상태에서 이동을 하는 경우 오류를 반환한다.")
    @Test
    void failMoveIfGameIsNotStarted() {
        Map<Position, Piece> piecePosition = new HashMap<>();
        piecePosition.put(A2, new Piece(Queen.create(), WHITE));

        ChessGame chessGame = new ChessGame(
                new Turn(WHITE),
                piecePosition
        );

        Position source = new Position(new File('a'), new Rank(2));
        Position target = new Position(new File('a'), new Rank(3));

        assertThatThrownBy(() -> chessGame.move(source, target))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("체스 게임이 중단된 상태에서 이동을 하는 경우 오류를 반환한다.")
    @Test
    void failMoveIfGameIsStopped() {
        Map<Position, Piece> piecePosition = new HashMap<>();
        piecePosition.put(A2, new Piece(Queen.create(), WHITE));

        ChessGame chessGame = new ChessGame(
                new Turn(WHITE),
                piecePosition
        );

        chessGame.start();
        chessGame.end();
        Position source = new Position(new File('a'), new Rank(2));
        Position target = new Position(new File('a'), new Rank(3));

        assertThatThrownBy(() -> chessGame.move(source, target))
                .isInstanceOf(IllegalStateException.class);
    }
}
