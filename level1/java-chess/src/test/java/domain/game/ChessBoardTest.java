package domain.game;

import static controller.constants.GameState.CHECKMATE;
import static controller.constants.GameState.RUNNING;
import static domain.piece.Color.BLACK;
import static domain.piece.Color.WHITE;
import static fixture.PiecePositionFixture.PIECE_POSITION_FOR_BLACK_WINS;
import static fixture.PiecePositionFixture.PIECE_POSITION_FOR_WHITE_WINS;
import static fixture.PositionFixture.A1;
import static fixture.PositionFixture.A5;
import static fixture.PositionFixture.A7;
import static fixture.PositionFixture.B1;
import static fixture.PositionFixture.B2;
import static fixture.PositionFixture.B3;
import static fixture.PositionFixture.B4;
import static fixture.PositionFixture.B5;
import static fixture.PositionFixture.B6;
import static fixture.PositionFixture.B7;
import static fixture.PositionFixture.B8;
import static fixture.PositionFixture.C3;
import static fixture.PositionFixture.C4;
import static fixture.PositionFixture.C5;
import static fixture.PositionFixture.C7;
import static fixture.PositionFixture.C8;
import static fixture.PositionFixture.D1;
import static fixture.PositionFixture.D2;
import static fixture.PositionFixture.D3;
import static fixture.PositionFixture.D5;
import static fixture.PositionFixture.D7;
import static fixture.PositionFixture.D8;
import static fixture.PositionFixture.E1;
import static fixture.PositionFixture.E6;
import static fixture.PositionFixture.F1;
import static fixture.PositionFixture.F2;
import static fixture.PositionFixture.F3;
import static fixture.PositionFixture.F4;
import static fixture.PositionFixture.G2;
import static fixture.PositionFixture.G4;
import static fixture.PositionFixture.H3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import controller.constants.GameState;
import domain.piece.Piece;
import domain.piece.piecerole.Bishop;
import domain.piece.piecerole.BlackPawn;
import domain.piece.piecerole.King;
import domain.piece.piecerole.Knight;
import domain.piece.piecerole.Queen;
import domain.piece.piecerole.Rook;
import domain.piece.piecerole.WhitePawn;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChessBoardTest {

    @DisplayName("이동 테스트")
    @Nested
    class MoveTest {
        @DisplayName("(b,2)에 위치한 piece를 (b,3)로 이동한다.")
        @Test
        void movePieceToTarget() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();

            chessBoard.move(B2, B3);

            Piece piece = chessBoard.findPieceByPosition(B3);
            assertThat(piece).isEqualTo(new Piece(WhitePawn.create(), WHITE));
        }

        @DisplayName("source에 piece가 없다면 에러를 반환한다.")
        @Test
        void movePieceIfSourceHasNotPiece() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();

            assertThatThrownBy(() -> chessBoard.move(C3, C4))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("옮기고자 하는 위치에 같은 진영의 Piece가 있다면 에러를 반환한다.")
        @Test
        void hasSameColorPiece() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();
            chessBoard.move(D2, D3);

            assertThatThrownBy(() -> chessBoard.move(D1, D3))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("같은 위치로의 이동이라면 에러를 반환한다.")
        @Test
        void moveToSameSquare() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();

            assertThatThrownBy(() -> chessBoard.move(B1, B1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("같은 진영의 기물이 있는 곳으로 이동한다면 에러를 반환한다.")
        @Test
        void moveToSamePiecePosition() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();

            assertThatThrownBy(() -> chessBoard.move(D1, D2))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("앞에 다른 진영의 기물이 있는 경우 폰이 이동하지 못한다.")
        @Test
        void movePawnWhenFrontSquareHasOtherPiece() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();
            chessBoard.move(B2, B4);
            chessBoard.move(B7, B5);

            assertThatThrownBy(() -> chessBoard.move(B4, B5))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("대각선에 다른 진영의 기물이 있는 경우 폰이 이동할 수 있다.")
        @Test
        void movePawnWhenDiagonalSquareHasOtherPiece() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();
            chessBoard.move(B2, B4);
            chessBoard.move(C7, C5);
            chessBoard.move(B4, C5);

            Piece piece = chessBoard.findPieceByPosition(C5);
            assertThat(piece).isEqualTo(new Piece(WhitePawn.create(), WHITE));
        }

        @DisplayName("대각선에 다른 진영의 기물이 없는 경우 폰이 이동할 수 없다.")
        @Test
        void cannotMovePawnWhenDiagonalSquareHasNotOtherPiece() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();
            chessBoard.move(B2, B4);
            chessBoard.move(B7, B6);
            assertThatThrownBy(() -> chessBoard.move(B4, C5))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("나이트를 제외한 기물은 이동하는 경로에 기물이 있으면 이동하지 못한다.")
        @Test
        void isOverlappedPath() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();
            chessBoard.move(D2, D3);
            chessBoard.move(B7, B6);

            assertThatThrownBy(() -> chessBoard.move(D1, D5))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("나이트는 이동하는 경로에 기물이 있어도 이동할 수 있다.")
        @Test
        void knightCanJump() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();
            chessBoard.move(B2, B3);
            chessBoard.move(B7, B5);
            chessBoard.move(B1, C3);

            assertThat(chessBoard.findPieceByPosition(C3)).isEqualTo(new Piece(Knight.create(), WHITE));
        }
    }

    @DisplayName("기물 확인 테스트")
    @Nested
    class hasPieceTest {
        @DisplayName("특정 위치에 기물이 있음을 확인한다.")
        @Test
        void hasPiece() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();

            assertThat(chessBoard.hasPiece(A1)).isTrue();
        }

        @DisplayName("특정 위치에 기물이 없음을 확인한다.")
        @Test
        void hasNotPiece() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();

            assertThat(chessBoard.hasPiece(A5)).isFalse();
        }
    }

    @DisplayName("게임 종료 테스트")
    @Nested
    class GameOverTest {
        @DisplayName("킹이 잡힌 경우 게임이 종료되는 상태를 반환한다.")
        @Test
        void gameEndsWhenKingCaptured() {
            ChessBoard chessBoard = new ChessBoard(generatePiecePositionForCapturingKing());
            GameState gameState = chessBoard.move(C8, B8);

            assertThat(gameState).isEqualTo(CHECKMATE);
        }

        @DisplayName("킹이 잡힌 경우 게임이 종료되는 상태를 반환한다.")
        @Test
        void gameIsRunningWhenKingIsNotCaptured() {
            ChessBoard chessBoard = new ChessBoard(generatePiecePositionForCapturingKing());
            GameState gameState = chessBoard.move(C8, D8);

            assertThat(gameState).isEqualTo(RUNNING);
        }

        /*
         * .Kr.....  8
         * P.PB....  7
         * .P..Q...  6
         * ........  5
         * .....nq.  4
         * .....p.p  3
         * .....pp.  2
         * ....rk..  1
         * abcdefgh
         */
        private Map<Position, Piece> generatePiecePositionForCapturingKing() {
            return new HashMap<>(
                    Map.ofEntries(
                            Map.entry(B8, new Piece(King.create(), BLACK)),
                            Map.entry(C8, new Piece(Rook.create(), WHITE)),
                            Map.entry(A7, new Piece(BlackPawn.create(), BLACK)),
                            Map.entry(C7, new Piece(BlackPawn.create(), BLACK)),
                            Map.entry(D7, new Piece(Bishop.create(), BLACK)),
                            Map.entry(B6, new Piece(BlackPawn.create(), BLACK)),
                            Map.entry(E6, new Piece(Queen.create(), BLACK)),
                            Map.entry(F4, new Piece(Knight.create(), WHITE)),
                            Map.entry(G4, new Piece(Queen.create(), WHITE)),
                            Map.entry(F3, new Piece(WhitePawn.create(), WHITE)),
                            Map.entry(H3, new Piece(WhitePawn.create(), WHITE)),
                            Map.entry(F2, new Piece(WhitePawn.create(), WHITE)),
                            Map.entry(G2, new Piece(WhitePawn.create(), WHITE)),
                            Map.entry(E1, new Piece(Rook.create(), WHITE)),
                            Map.entry(F1, new Piece(King.create(), WHITE))
                    )
            );
        }
    }

    @DisplayName("각 진영의 점수를 계산한다.")
    @Nested
    class calculateScore {
        @DisplayName("검은색 진영의 점수를 계산한다.")
        @Test
        void calculateBlackPieceScore() {
            ChessBoard chessBoard = new ChessBoard(PIECE_POSITION_FOR_BLACK_WINS);
            double score = chessBoard.calculateScore(BLACK);

            assertThat(score).isEqualTo(20);
        }

        @DisplayName("하얀색 진영의 점수를 계산한다.")
        @Test
        void calculateWhitePieceScore() {
            ChessBoard chessBoard = new ChessBoard(PIECE_POSITION_FOR_WHITE_WINS);
            double score = chessBoard.calculateScore(WHITE);

            assertThat(score).isEqualTo(19.5);
        }
    }
}
