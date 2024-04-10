package domain;

import static controller.constants.GameState.NOT_STARTED;
import static controller.constants.GameState.RUNNING;
import static controller.constants.GameState.STOPPED;

import controller.constants.GameState;
import controller.dto.ChessGameStatus;
import controller.dto.MoveResult;
import domain.game.ChessBoard;
import domain.game.ChessBoardGenerator;
import domain.game.Turn;
import domain.piece.Color;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;

public class ChessGame {
    private ChessBoard chessBoard;
    private GameState gameState;
    private Turn turn;

    public ChessGame(final Turn turn, final Map<Position, Piece> piecePosition) {
        this.chessBoard = new ChessBoard(piecePosition);
        this.gameState = NOT_STARTED;
        this.turn = turn;
    }

    public ChessBoard start() {
        validateAlreadyRunning();
        gameState = RUNNING;
        turn = new Turn(Color.WHITE);
        chessBoard = ChessBoardGenerator.generate();
        return chessBoard;
    }

    private void validateAlreadyRunning() {
        if (gameState == RUNNING) {
            gameState = NOT_STARTED;
            throw new IllegalStateException("[ERROR] 게임이 이미 진행 중입니다. 기존 게임을 종료하고 다시 시작합니다.");
        }
    }

    public void end() {
        gameState = STOPPED;
    }

    public MoveResult move(final Position source, final Position target) {
        validateNotRunning();
        validateInvalidTurn(source);

        this.gameState = chessBoard.move(source, target);
        turn.changeTurn();

        ChessGameStatus chessGameStatus = new ChessGameStatus(chessBoard, turn);
        return new MoveResult(chessGameStatus, gameState, chessBoard.findPieceByPosition(target));
    }

    private void validateInvalidTurn(final Position source) {
        Piece sourcePiece = chessBoard.findPieceByPosition(source);
        if (turn.isNotTurn(sourcePiece)) {
            throw new IllegalArgumentException("[ERROR] 현재는 " + turn.getName() + "의 이동 차례입니다.");
        }
    }

    public GameResult status() {
        validateNotRunning();
        return GameResult.from(chessBoard);
    }

    private void validateNotRunning() {
        if (gameState != RUNNING) {
            throw new IllegalStateException("[ERROR] 게임이 진행 중인 상태가 아닙니다.");
        }
    }

    public ChessGameStatus continueGame() {
        gameState = RUNNING;
        return new ChessGameStatus(chessBoard, turn);
    }

    public boolean isContinuing() {
        return gameState == NOT_STARTED || gameState == RUNNING;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public boolean hasNotGameInProgress() {
        return chessBoard.isEmpty();
    }
}
