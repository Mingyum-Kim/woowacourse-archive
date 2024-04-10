package controller.dto;

import domain.game.ChessBoard;
import domain.game.Turn;

public record ChessGameStatus(
        ChessBoard chessBoard,
        Turn turn
) {
}
