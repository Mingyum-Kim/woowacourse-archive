package controller.command;

import controller.dto.ChessGameStatus;
import domain.game.ChessBoard;
import service.ChessGameService;
import view.OutputView;
import view.command.CommandDto;

public class ContinueCommandExecutor implements CommandExecutor {
    public ContinueCommandExecutor(final CommandDto commandDto) {
        if (commandDto.isInvalidSupplementSize()) {
            throw new IllegalArgumentException("[ERROR] 게임을 이어서 진행하는 명령어를 올바르게 입력해주세요.");
        }
    }

    @Override
    public void execute(final ChessGameService chessGameService, final OutputView outputView) {
        try {
            ChessGameStatus gameStatus = chessGameService.continueGame();
            outputView.printContinuingMessage(gameStatus.chessBoard(), gameStatus.turn());
        } catch (IllegalStateException exception) {
            outputView.printErrorMessage(exception.getMessage());
            ChessBoard chessBoard = chessGameService.startGame();
            outputView.printChessBoard(chessBoard);
        }
    }
}
