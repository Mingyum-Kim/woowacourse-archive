package controller.command;

import service.ChessGameService;
import view.OutputView;
import view.command.CommandDto;

public class EndCommandExecutor implements CommandExecutor {
    public EndCommandExecutor(final CommandDto commandDto) {
        if (commandDto.isInvalidSupplementSize()) {
            throw new IllegalArgumentException("[ERROR] 게임 종료 명령어를 올바르게 입력해주세요.");
        }
    }

    @Override
    public void execute(
            final ChessGameService chessGameService,
            final OutputView outputView
    ) {
        chessGameService.endGame();
        outputView.printEndGameMessage();
    }
}
