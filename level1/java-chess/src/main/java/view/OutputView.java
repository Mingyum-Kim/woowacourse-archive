package view;

import static domain.position.File.END_LETTER;
import static domain.position.File.START_LETTER;
import static domain.position.Rank.END_NUMBER;
import static domain.position.Rank.START_NUMBER;

import controller.constants.Winner;
import domain.GameResult;
import domain.game.ChessBoard;
import domain.game.Turn;
import domain.piece.Color;
import domain.piece.Piece;
import domain.position.File;
import domain.position.Position;
import domain.position.Rank;

public class OutputView {
    public void printStartMessage() {
        System.out.printf("> 체스 게임을 시작합니다.%n"
                + "> 게임 시작 : start%n"
                + "> 게임 종료 : end%n"
                + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3%n"
                + "> 점수 확인 : status%n"
                + "> 이전 게임 이어서 진행 : continue%n");
    }

    public void printContinuingMessage(final ChessBoard chessBoard, final Turn turn) {
        System.out.printf("%n> 진행 중이던 체스 게임을 이어서 진행합니다.%n");
        System.out.printf("> 현재는 %s 진영의 이동 차례입니다.%n", generateColorOutput(turn.getColor()));
        printChessBoard(chessBoard);
    }

    public void printChessBoard(final ChessBoard chessBoard) {
        for (int rank = END_NUMBER; rank >= START_NUMBER; rank--) {
            printPieceSymbol(chessBoard, rank);
            System.out.println();
        }
        System.out.println();
    }

    private void printPieceSymbol(final ChessBoard chessBoard, final int rank) {
        for (char file = START_LETTER; file <= END_LETTER; file++) {
            Position position = new Position(
                    new Position(new File(file), new Rank(rank)));
            System.out.print(generateSymbol(chessBoard, position));
        }
    }

    public String generateSymbol(final ChessBoard chessBoard, final Position position) {
        if (chessBoard.hasPiece(position)) {
            Piece piece = chessBoard.findPieceByPosition(position);
            return PieceMapper.getSymbol(piece);
        }
        return PieceMapper.getEmptySymbol();
    }

    public void printCheckmateColor(final Color color) {
        System.out.printf("%s 진영이 승리하였습니다.", generateColorOutput(color));
    }

    private String generateColorOutput(final Color color) {
        if (color == Color.BLACK) {
            return "검은색";
        }
        return "하얀색";
    }

    public void printGameResult(final GameResult gameResult) {
        System.out.printf(
                "검은색 진영의 점수 : %.1f, 하얀색 진영의 점수 : %.1f%n",
                gameResult.blackScore(),
                gameResult.whiteScore()
        );
        System.out.printf(generateWinnerOutput(gameResult.winner()));
    }

    private String generateWinnerOutput(final Winner winner) {
        if (winner == Winner.BLACK) {
            return "검은색 진영의 점수가 더 높습니다.%n";
        }
        if (winner == Winner.WHITE) {
            return "하얀색 진영의 점수가 더 높습니다.%n";
        }
        return "무승부입니다.%n";
    }

    public static void printErrorMessage(final String message) {
        System.out.println(message);
    }

    public void printEndGameMessage() {
        System.out.println("게임이 종료되었습니다.");
    }
}
