package controller.dto;

import controller.constants.GameState;
import domain.piece.Piece;

public record MoveResult(
        ChessGameStatus chessGameStatus,
        GameState gameState,
        Piece movedPiece
) {
}
