package domain.game;

import static controller.constants.GameState.CHECKMATE;
import static controller.constants.GameState.RUNNING;

import controller.constants.GameState;
import domain.piece.Color;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;

public class ChessBoard {
    private final Map<Position, Piece> piecePosition;

    public ChessBoard(final Map<Position, Piece> piecePosition) {
        this.piecePosition = piecePosition;
    }

    public GameState move(final Position source, final Position target) {
        validateMovement(source, target);
        return update(source, target);
    }

    private void validateMovement(final Position source, final Position target) {
        validateSourceExists(source);

        Piece sourcePiece = piecePosition.get(source);

        validateDifferentSourceTarget(source, target);
        validateOpponentTarget(source, target);

        sourcePiece.validateMovableRoute(source, target, piecePosition);
    }

    private void validateOpponentTarget(final Position source, final Position target) {
        if (hasSameColorPiece(source, target)) {
            throw new IllegalArgumentException("[ERROR] 같은 진영의 기물이 있는 곳으로 옮길 수 없습니다.");
        }
    }

    private boolean hasSameColorPiece(final Position source, final Position target) {
        Piece sourcePiece = piecePosition.get(source);

        if (piecePosition.containsKey(target)) {
            Piece targetPiece = piecePosition.get(target);
            return sourcePiece.isEqualColor(targetPiece.getColor());
        }
        return false;
    }

    private void validateSourceExists(final Position source) {
        if (!piecePosition.containsKey(source)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 Piece가 존재하지 않습니다.");
        }
    }

    private void validateDifferentSourceTarget(final Position source, final Position target) {
        if (source.equals(target)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로의 이동입니다.");
        }
    }

    private GameState update(final Position source, final Position target) {
        GameState gameState = checkGameIsEnded(target);

        Piece sourcePiece = piecePosition.get(source);
        piecePosition.put(target, sourcePiece);
        piecePosition.remove(source);

        return gameState;
    }

    private GameState checkGameIsEnded(final Position target) {
        if (isCheckmate(target)) {
            return CHECKMATE;
        }
        return RUNNING;
    }

    private boolean isCheckmate(final Position target) {
        return hasPiece(target) && findPieceByPosition(target).doesGameEndsWhenCaptured();
    }

    public boolean hasPiece(final Position position) {
        return piecePosition.containsKey(position);
    }

    public Piece findPieceByPosition(final Position position) {
        return piecePosition.get(position);
    }

    public double calculateScore(final Color color) {
        double score = 0;
        for (Map.Entry<Position, Piece> entry : piecePosition.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            if (piece.isEqualColor(color)) {
                score += piece.calculateScore(position, piecePosition);
            }
        }
        return score;
    }

    public boolean isEmpty() {
        return piecePosition.isEmpty();
    }

    public Map<Position, Piece> getPiecePosition() {
        return piecePosition;
    }
}
