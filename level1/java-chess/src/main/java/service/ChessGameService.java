package service;

import controller.dto.ChessGameStatus;
import controller.dto.MoveResult;
import domain.ChessGame;
import domain.GameResult;
import domain.game.ChessBoard;
import domain.position.Position;
import repository.PiecePositionRepository;
import repository.TurnRepository;
import view.OutputView;

public class ChessGameService {
    private final PiecePositionRepository piecePositionRepository;
    private final TurnRepository turnRepository;
    private final ChessGame chessGame;

    public ChessGameService() {
        this.piecePositionRepository = new PiecePositionRepository();
        this.turnRepository = new TurnRepository();
        this.chessGame = new ChessGame(
                turnRepository.find(),
                piecePositionRepository.findAllPiecePositions()
        );
    }

    public ChessBoard startGame() {
        try {
            ChessBoard chessBoard = chessGame.start();

            piecePositionRepository.clear();
            piecePositionRepository.saveAll(chessBoard.getPiecePosition());
            return chessBoard;
        } catch (final IllegalStateException exception) {
            OutputView.printErrorMessage(exception.getMessage());
            return startGame();
        }
    }

    public void endGame() {
        chessGame.end();
        piecePositionRepository.clear();
        turnRepository.clear();
    }

    public MoveResult move(final Position source, final Position target) {
        MoveResult moveResult = chessGame.move(source, target);

        piecePositionRepository.deleteByPosition(target);
        piecePositionRepository.updatePosition(source, target);

        turnRepository.save(moveResult.chessGameStatus().turn());
        return moveResult;
    }

    public GameResult status() {
        return chessGame.status();
    }

    public ChessGameStatus continueGame() {
        if (chessGame.hasNotGameInProgress()) {
            throw new IllegalStateException("[ERROR] 진행 중인 게임이 없습니다. 게임을 새로 시작합니다.");
        }
        return chessGame.continueGame();
    }

    public boolean isContinuing() {
        return chessGame.isContinuing();
    }
}
